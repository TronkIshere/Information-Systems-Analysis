package com.tronk.analysis.dto.request.courseOffering;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCourseOfferingRequest implements Serializable {
	LocalDate startDate;
	LocalDate endDate;
	Set studentCourses;
	UUID id;
	UUID courseId;
	UUID semesterId;
}
