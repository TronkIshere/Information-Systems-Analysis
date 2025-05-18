package com.tronk.analysis.dto.request.student;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadStudentRequest implements Serializable {
	UUID student_code;
	String major;
	BigDecimal gpa;
	UUID id;
	UUID userId;
}
