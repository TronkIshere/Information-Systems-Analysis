package com.tronk.analysis.dto.response.studentCourse;

import com.tronk.analysis.entity.EnrollmentStatus;
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
public class StudentCourseResponse implements Serializable {
	String grade;
	LocalDate enrollmentDate;
	EnrollmentStatus status;
}
