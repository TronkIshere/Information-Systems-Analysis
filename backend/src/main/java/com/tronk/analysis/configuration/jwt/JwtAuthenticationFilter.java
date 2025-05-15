package com.tronk.analysis.configuration.jwt;

import com.tronk.analysis.service.JwtService;
import com.tronk.analysis.service.impl.UserDetailsServiceCustomizer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsServiceCustomizer customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);

        if (!isValidToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = getUserDetailsByJwt(jwt);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        logAuthenticationStatus();

        filterChain.doFilter(request, response);
    }

    private UserDetails getUserDetailsByJwt(String jwt) {
        String email = jwtService.getEmailFromToken(jwt);
        return customUserDetailsService.loadUserByUsername(email);
    }

    private boolean isValidToken(String jwt) {
        return StringUtils.hasText(jwt) && jwtService.validateToken(jwt);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void logAuthenticationStatus() {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth != null) {
            log.debug("[CONTEXT] Current auth: {} | Roles: {}",
                    currentAuth.getName(),
                    currentAuth.getAuthorities());
        } else {
            log.warn("[CONTEXT] SecurityContext is empty after authentication attempt");
        }
    }
}

