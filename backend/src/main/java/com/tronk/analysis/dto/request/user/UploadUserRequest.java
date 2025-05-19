package com.tronk.analysis.dto.request.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadUserRequest implements Serializable {
    String name;
    String email;
    String phoneNumber;
    String status;
    String password;
    LocalDate birthDay;
    boolean gender;
}

