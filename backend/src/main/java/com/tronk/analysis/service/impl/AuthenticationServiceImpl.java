package com.tronk.analysis.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tronk.analysis.configuration.UserPrincipal;
import com.tronk.analysis.dto.request.authentication.LogoutRequest;
import com.tronk.analysis.dto.request.authentication.SignInRequest;
import com.tronk.analysis.dto.response.authentication.RefreshTokenResponse;
import com.tronk.analysis.dto.response.authentication.SignInResponse;
import com.tronk.analysis.dto.response.authentication.SignInStatus;
import com.tronk.analysis.entity.Cashier;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.entity.common.User;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.repository.CashierRepository;
import com.tronk.analysis.repository.LecturerRepository;
import com.tronk.analysis.repository.StudentRepository;
import com.tronk.analysis.service.AuthenticationService;
import com.tronk.analysis.service.JwtService;
import com.tronk.analysis.service.RedisService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    UserDetailsServiceCustomizer userDetailsServiceCustomizer;
    LecturerRepository lecturerRepository;
    StudentRepository studentRepository;
    AuthenticationManager authenticationManager;
    CashierRepository cashierRepository;
    RedisService redisService;
    JwtService jwtService;

    @Override
    public SignInResponse signIn(SignInRequest request, HttpServletResponse response) {
        UserPrincipal userPrincipal = authenticateAndGetUserPrincipal(request.getLoginName(), request.getPassword());
        User user = getUserByIdAndType(userPrincipal.getId(), userPrincipal.getUserType());

        return generateTokenResponse(userPrincipal, user, response);
    }

    private UserPrincipal authenticateAndGetUserPrincipal(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return (UserPrincipal) authentication.getPrincipal();
    }

    private User getUserByIdAndType(UUID id, String userType) {
        return switch (userType) {
            case "STUDENT" -> studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            case "LECTURER" -> lecturerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Lecturer not found"));
            case "CASHIER" -> cashierRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Lecturer not found"));
            default -> throw new IllegalArgumentException("Invalid user type");
        };
    }

    private SignInResponse generateTokenResponse(UserPrincipal userPrincipal, User user, HttpServletResponse response) {
        final String accessToken = jwtService.generateAccessToken(userPrincipal);
        final String refreshToken = jwtService.generateRefreshToken(userPrincipal);

        if (user instanceof Student) {
            ((Student) user).setRefreshToken(refreshToken);
            studentRepository.save((Student) user);
        } else if (user instanceof Lecturer) {
            ((Lecturer) user).setRefreshToken(refreshToken);
            lecturerRepository.save((Lecturer) user);
        } else if (user instanceof Lecturer) {
            ((Cashier) user).setRefreshToken(refreshToken);
            cashierRepository.save((Cashier) user);
        }

        Cookie cookie = createCookie(refreshToken);
        response.addCookie(cookie);

        return SignInResponse.builder()
                .status(SignInStatus.SUCCESS)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .roles(user.getRoles())
                .build();
    }

    private Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(14 * 24 * 60 * 60);
        return cookie;
    }

    @Override
    public RefreshTokenResponse refreshToken(String refreshToken) throws ParseException, JOSEException {
        log.info("refresh token");

        validateRefreshToken(refreshToken);
        String email = jwtService.extractUserName(refreshToken);
        UserPrincipal userDetails = userDetailsServiceCustomizer.loadUserByUsername(email);

        User user = getUserByIdAndType(userDetails.getId(), userDetails.getUserType());

        isValidToken(user, refreshToken);
        String accessToken = jwtService.generateAccessToken(UserPrincipal.create(user));

        log.info("refresh token success");
        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .build();
    }

    private void validateRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            log.warn("Empty refresh token provided");
            throw new ApplicationException(ErrorCode.REFRESH_TOKEN_INVALID);
        }
    }

    private void isValidToken(User user, String refreshToken) throws ParseException, JOSEException {
        log.debug("Comparing tokens - DB: {} vs Input: {}", user.getRefreshToken(), refreshToken);

        if(StringUtils.isBlank(user.getRefreshToken())) {
            log.error("Refresh token in DB is empty for user: {}", user.getId());
            throw new ApplicationException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        if(!Objects.equals(refreshToken, user.getRefreshToken())) {
            log.error("Refresh token mismatch for user: {}", user.getId());
            throw new ApplicationException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        if(!jwtService.verificationToken(refreshToken, UserPrincipal.create(user))) {
            log.error("JWT verification failed for token: {}", refreshToken);
            throw new ApplicationException(ErrorCode.REFRESH_TOKEN_INVALID);
        }
    }

    @Override
    public void signOut(final LogoutRequest request, final HttpServletResponse response) {
        final String accessToken = request.getAccessToken();

        final String userType = jwtService.extractUserType(accessToken);
        final UUID userId = jwtService.extractUserId(accessToken);

        User user = switch (userType) {
            case "STUDENT" -> studentRepository.findById(userId)
                    .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXISTED));
            case "LECTURER" -> lecturerRepository.findById(userId)
                    .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXISTED));
            case "CASHIER" -> cashierRepository.findById(userId)
                    .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXISTED));
            default -> throw new ApplicationException(ErrorCode.USER_TYPE_INVALID);
        };

        user.setRefreshToken(null);
        if (user instanceof Student) {
            studentRepository.save((Student) user);
        } else if (user instanceof  Lecturer){
            lecturerRepository.save((Lecturer) user);
        } else {
            cashierRepository.save((Cashier) user);
        }

        addTokenToBlacklist(accessToken);
        deleteRefreshTokenCookie(response);
    }

    private void addTokenToBlacklist(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expiration = claims.getExpirationTime();
            long ttl = expiration.getTime() - System.currentTimeMillis();

            if (ttl > 0) {
                String jti = claims.getJWTID();
                redisService.save(jti, "blacklisted", ttl, TimeUnit.MILLISECONDS);
            }
        } catch (ParseException e) {
            throw new ApplicationException(ErrorCode.TOKEN_INVALID);
        }
    }

    private void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
