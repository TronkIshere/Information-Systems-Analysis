package com.tronk.analysis.dto.request.course;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCourseRequest implements Serializable {
	String name;
	int credit;
	BigDecimal baseFeeCredit;
	boolean subjectType;
	UUID id;
}
