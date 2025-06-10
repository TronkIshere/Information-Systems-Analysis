package com.tronk.analysis.dto.response.courseOffering;

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
public class CourseOfferingResponse implements Serializable {
	UUID id;
	LocalDate startDate;
	LocalDate endDate;
	UUID courseId;
	String courseName;
	int credit;
	BigDecimal baseFeeCredit;
	boolean subjectType;
}
