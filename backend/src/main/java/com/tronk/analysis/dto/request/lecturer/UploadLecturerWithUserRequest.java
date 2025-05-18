package com.tronk.analysis.dto.request.lecturer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadLecturerWithUserRequest implements Serializable {
    // user
    String name;
    String email;
    String phoneNumber;
    String status;
    String password;
    Date birthDay;
    boolean gender;
    // lecture
    String lecturerCode;
    String academicRank;
    BigDecimal salary;
    Date hireDate;
    String researchField;
}
