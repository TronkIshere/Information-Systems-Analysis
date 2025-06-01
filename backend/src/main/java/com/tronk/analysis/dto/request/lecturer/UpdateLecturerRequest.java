package com.tronk.analysis.dto.request.lecturer;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLecturerRequest implements Serializable {
	// lecturer
	UUID id;
	String lecturerCode;
	String academicRank;
	BigDecimal salary;
	LocalDate hireDate;
	String researchField;
	String name;
	String email;
	String phoneNumber;
	String password;
	LocalDate birthDay;
	boolean gender;
	String status;
	List<UUID> courseIds;
	UUID departmentId;
}
