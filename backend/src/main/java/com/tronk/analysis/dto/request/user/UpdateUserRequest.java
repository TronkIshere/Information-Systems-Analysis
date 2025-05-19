package com.tronk.analysis.dto.request.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String id;
    String name;
    String email;
    String phoneNumber;
    LocalDate birthDay;
    boolean gender;
}

