package com.tronk.analysis.dto.request.lecturer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLecturerWithUserRequest {
    // user
    UUID userId;
    String name;
    String email;
    String phoneNumber;
    String password; // optional
    LocalDate birthDay;
    boolean gender;
    // lecturer
    UUID lecturerId;
    String lecturerCode;
    String academicRank;
    BigDecimal salary;
    LocalDate hireDate;
    String researchField;
}
