package com.tronk.analysis.dto.response.lecturer;

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
public class LecturerResponse implements Serializable {
	UUID id;
	String lecturerCode;
	String academicRank;
	BigDecimal salary;
	LocalDate hireDate;
	String researchField;
	String name;
	String email;
	String phoneNumber;
	String status;
	LocalDate birthDay;
	boolean gender;
}
