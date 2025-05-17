package com.tronk.analysis.dto.request.lecturer;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLecturerRequest implements Serializable {
	String lecturerCode;
	String academicRank;
	BigDecimal salary;
	Date hireDate;
	String researchField;
	UUID id;
}
