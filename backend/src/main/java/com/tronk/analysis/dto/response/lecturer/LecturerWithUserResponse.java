package com.tronk.analysis.dto.response.lecturer;

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
public class LecturerWithUserResponse implements Serializable {
    // user
    UUID userId;
    String name;
    String email;
    String phoneNumber;
    String status;
    String password;
    Date birthDay;
    boolean gender;
    //lecturer
    UUID lecturerId;
    String lecturerCode;
    String academicRank;
    BigDecimal salary;
    Date hireDate;
    String researchField;
}
