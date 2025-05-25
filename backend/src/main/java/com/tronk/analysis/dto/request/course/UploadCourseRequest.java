package com.tronk.analysis.dto.request.course;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.lang.Integer;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadCourseRequest implements Serializable {
	String name;
	int credit;
	BigDecimal baseFeeCredit;
	boolean subjectType;
}
