package com.tronk.analysis.controller;

import com.nimbusds.jose.JOSEException;
import com.tronk.analysis.dto.request.authentication.LogoutRequest;
import com.tronk.analysis.dto.request.authentication.SignInRequest;
import com.tronk.analysis.dto.response.authentication.RefreshTokenResponse;
import com.tronk.analysis.dto.response.authentication.SignInResponse;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PUBLIC)
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    ResponseAPI<SignInResponse> authenticateUser(@Valid @RequestBody SignInRequest request, HttpServletResponse response) {
        var result = authenticationService.signIn(request, response);
        return ResponseAPI.<SignInResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PostMapping("/refresh-token")
    ResponseAPI<RefreshTokenResponse> refreshToken(@CookieValue(name = "refreshToken") String refreshToken) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshToken);
        return ResponseAPI.<RefreshTokenResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Refreshed token success")
                .data(result)
                .build();
    }

    @PostMapping("/logout")
    ResponseAPI<Void> logout(@RequestBody @Valid LogoutRequest request, HttpServletResponse response) {
        authenticationService.signOut(request, response);
        return ResponseAPI.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Sign out success")
                .build();
    }
}
