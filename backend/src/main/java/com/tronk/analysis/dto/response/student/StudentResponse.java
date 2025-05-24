package com.tronk.analysis.dto.response.student;

import lombok.*;
import java.io.Serializable;
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
public class StudentResponse implements Serializable {
	UUID id;
	String studentCode;
	String major;
	BigDecimal gpa;
	String name;
	String email;
	String phoneNumber;
	String status;
	String loginName;
	LocalDate birthDay;
	boolean gender;
}
