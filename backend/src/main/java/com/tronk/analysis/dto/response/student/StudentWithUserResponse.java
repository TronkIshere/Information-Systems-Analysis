package com.tronk.analysis.dto.response.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentWithUserResponse implements Serializable {
    // user
    UUID userId;
    String name;
    String email;
    String phoneNumber;
    String status;
    Date birthDay;
    boolean gender;
    //student
    UUID studentId;
    UUID studentCode;
    String major;
    BigDecimal gpa;
}
