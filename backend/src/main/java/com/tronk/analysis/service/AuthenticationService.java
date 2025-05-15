package com.tronk.analysis.service;

import com.nimbusds.jose.JOSEException;
import com.tronk.analysis.dto.request.authentication.LogoutRequest;
import com.tronk.analysis.dto.request.authentication.SignInRequest;
import com.tronk.analysis.dto.response.authentication.RefreshTokenResponse;
import com.tronk.analysis.dto.response.authentication.SignInResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;

import java.text.ParseException;

public interface AuthenticationService {
    SignInResponse signIn(SignInRequest request, HttpServletResponse response);

    RefreshTokenResponse refreshToken(@CookieValue(name = "refreshToken") String refreshToken) throws ParseException, JOSEException;

    void signOut(LogoutRequest request, HttpServletResponse response);
}

