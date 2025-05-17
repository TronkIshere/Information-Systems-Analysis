package com.tronk.analysis.dto.response.course;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.lang.Integer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse implements Serializable {
	String name;
	int credit;
	BigDecimal baseFeeCredit;
}
