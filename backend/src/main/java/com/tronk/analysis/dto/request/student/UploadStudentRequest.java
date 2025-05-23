package com.tronk.analysis.dto.request.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadStudentRequest implements Serializable {
    UUID id;
    String studentCode;
    String major;
    BigDecimal gpa;
    String name;
    String email;
    String phoneNumber;
    String status;
    String loginName;
    String password;
    LocalDate birthDay;
    boolean gender;
}
