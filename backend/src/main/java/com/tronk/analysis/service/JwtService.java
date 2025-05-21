package com.tronk.analysis.service;

import com.nimbusds.jose.JOSEException;
import com.tronk.analysis.configuration.UserPrincipal;

import java.text.ParseException;
import java.util.UUID;

public interface JwtService {
    String generateAccessToken(UserPrincipal user);

    boolean verificationToken(String token, UserPrincipal user) throws ParseException, JOSEException;

    String extractUserName(String token);

    String generateRefreshToken(UserPrincipal user);

    long extractTokenExpired(String accessToken);

    String getUserEmailFromToken(String token);

    boolean validateToken(String authToken);

    String getEmailFromToken(String jwt);

    UUID extractUserId(String token);

    String extractUserType(String token);
}

