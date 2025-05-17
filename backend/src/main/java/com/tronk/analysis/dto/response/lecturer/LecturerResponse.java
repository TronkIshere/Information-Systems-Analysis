package com.tronk.analysis.dto.response.lecturer;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LecturerResponse implements Serializable {
	String lecturerCode;
	String academicRank;
	BigDecimal salary;
	Date hireDate;
	String researchField;
}
