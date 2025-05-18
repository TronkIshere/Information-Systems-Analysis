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
    INVALID_OTP(5002, "Invalid or expired OTP", HttpStatus.UNAUTHORIZED),

    // Course related errors (41xx)
    COURSE_NOT_FOUND(4101, "Course not found", HttpStatus.NOT_FOUND),
    COURSE_ALREADY_EXISTS(4102, "Course already exists", HttpStatus.CONFLICT),
    COURSE_CREDIT_INVALID(4103, "Invalid credit value", HttpStatus.BAD_REQUEST),
    COURSE_FEE_INVALID(4104, "Invalid course fee", HttpStatus.BAD_REQUEST),
    COURSE_PREREQUISITE_CONFLICT(4105, "Course prerequisite conflict", HttpStatus.CONFLICT),

    // Department related errors (42xx)
    DEPARTMENT_NOT_FOUND(4201, "Department not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_ALREADY_EXISTS(4202, "Department already exists", HttpStatus.CONFLICT),
    DEPARTMENT_HAS_LECTURERS(4203, "Cannot delete department with active lecturers", HttpStatus.CONFLICT),
    DEPARTMENT_NAME_INVALID(4204, "Invalid department name", HttpStatus.BAD_REQUEST),

    // Lecturer related errors (43xx)
    LECTURER_NOT_FOUND(4301, "Lecturer not found", HttpStatus.NOT_FOUND),
    LECTURER_ALREADY_EXISTS(4302, "Lecturer already exists", HttpStatus.CONFLICT),
    LECTURER_CODE_INVALID(4303, "Invalid lecturer code", HttpStatus.BAD_REQUEST),
    LECTURER_SALARY_INVALID(4304, "Invalid salary value", HttpStatus.BAD_REQUEST),
    LECTURER_HAS_COURSES(4305, "Cannot delete lecturer with assigned courses", HttpStatus.CONFLICT),

    // Receipt related errors (44xx)
    RECEIPT_NOT_FOUND(4401, "Receipt not found", HttpStatus.NOT_FOUND),
    RECEIPT_ALREADY_PAID(4402, "Receipt already paid", HttpStatus.CONFLICT),
    RECEIPT_AMOUNT_INVALID(4403, "Invalid receipt amount", HttpStatus.BAD_REQUEST),
    RECEIPT_STATUS_INVALID(4404, "Invalid receipt status", HttpStatus.BAD_REQUEST),
    RECEIPT_STUDENT_MISMATCH(4405, "Receipt does not belong to this student", HttpStatus.FORBIDDEN),

    // Student related errors (45xx)
    STUDENT_NOT_FOUND(4501, "Student not found", HttpStatus.NOT_FOUND),
    STUDENT_ALREADY_EXISTS(4502, "Student already exists", HttpStatus.CONFLICT),
    STUDENT_CODE_INVALID(4503, "Invalid student code", HttpStatus.BAD_REQUEST),
    STUDENT_GPA_INVALID(4504, "Invalid GPA value", HttpStatus.BAD_REQUEST),
    STUDENT_HAS_RECEIPTS(4505, "Cannot delete student with existing receipts", HttpStatus.CONFLICT),
    STUDENT_MAJOR_INVALID(4506, "Invalid major specification", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatus statusCode;
}
