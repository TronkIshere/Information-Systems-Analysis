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
public class UploadLecturerRequest implements Serializable {
	String lecturerCode;
	String academicRank;
	BigDecimal salary;
	LocalDate hireDate;
	String researchField;
	String name;
	String email;
	String phoneNumber;
	String status;
	String password;
	LocalDate birthDay;
	boolean gender;
	List<UUID> courseIds;
	UUID departmentId;
}
