package com.tronk.analysis.dto.request.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String id;
    String name;
    String email;
    String phoneNumber;
    Date birthDay;
    boolean gender;
}

