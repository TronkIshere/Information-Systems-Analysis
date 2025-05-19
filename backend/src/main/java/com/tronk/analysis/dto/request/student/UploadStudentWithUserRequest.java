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
public class UploadStudentWithUserRequest implements Serializable {
    // user
    String name;
    String email;
    String phoneNumber;
    String status;
    String password;
    LocalDate birthDay;
    boolean gender;
    //student
    UUID student_code;
    String major;
    BigDecimal gpa;
    UUID id;
    UUID userId;
}
