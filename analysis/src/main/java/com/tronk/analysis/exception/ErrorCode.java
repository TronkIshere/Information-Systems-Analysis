package com.tronk.analysis.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    // 4xx Client Errors (400-499)
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Access denied", HttpStatus.FORBIDDEN),

    // Token related errors (grouped together)
    TOKEN_INVALID(4001, "Invalid token", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(4002, "Token expired", HttpStatus.UNAUTHORIZED),
    TOKEN_BLACKLISTED(4003, "Token is blacklisted", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID(4004, "Invalid refresh token", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(4005, "Refresh token expired", HttpStatus.UNAUTHORIZED),
    JWT_SECRET_NOT_CONFIGURED(4006, "JWT secret not configured", HttpStatus.INTERNAL_SERVER_ERROR),

    // Authentication/Authorization
    SIGN_OUT_FAILED(4010, "Sign out failed", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(4011, "Invalid credentials", HttpStatus.UNAUTHORIZED),

    // User related errors
    USER_NOT_EXISTED(4041, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXIST_EXCEPTION(4091, "User already exists", HttpStatus.CONFLICT),
    USER_ALREADY_HAS_THIS_ROLE(4092, "User already has this role", HttpStatus.CONFLICT),

    // Role related errors
    ROLE_NOT_EXISTED(4091, "Role not found", HttpStatus.CONFLICT),
    ROLE_ALREADY_EXIST_EXCEPTION(4093, "Role already exists", HttpStatus.CONFLICT),

    // System/Generic errors
    INVALID_KEY(5001, "Invalid key", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_OTP(5002, "Invalid or expired OTP", HttpStatus.UNAUTHORIZED);

    public String formatMessage(Object... args) {
        return String.format(message, args);
    }

    int code;
    String message;
    HttpStatus statusCode;
}
