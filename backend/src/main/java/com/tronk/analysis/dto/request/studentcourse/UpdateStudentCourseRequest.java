package com.tronk.analysis.dto.request.studentcourse;

import com.tronk.analysis.entity.EnrollmentStatus;
import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStudentCourseRequest implements Serializable {
	String grade;
	LocalDate enrollmentDate;
	EnrollmentStatus status;
	UUID id;
	UUID studentId;
	UUID courseOfferingId;
}
