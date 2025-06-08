package com.tronk.analysis.dto.response.courseOffering;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseOfferingResponse implements Serializable {
	LocalDate startDate;
	LocalDate endDate;
}
