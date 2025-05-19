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
public class UpdateStudentWithUserRequest implements Serializable {
    // user
    String userId;
    String name;
    String email;
    String phoneNumber;
    String password; // optional
    LocalDate birthDay;
    boolean gender;
    // student
    UUID studentId;
    UUID studentCode;
    String major;
    BigDecimal gpa;
}
