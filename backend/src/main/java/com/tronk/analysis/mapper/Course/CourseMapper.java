package com.tronk.analysis.mapper.Course;

import com.tronk.analysis.entity.Course;
import com.tronk.analysis.dto.response.course.CourseResponse;
import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {
	public CourseMapper() {
	}

	public static CourseResponse toResponse(Course course) {
		return CourseResponse.builder()
				.id(course.getId())
				.name(course.getName())
				.credit(course.getCredit())
				.baseFeeCredit(course.getBaseFeeCredit())
				.subjectType(course.isSubjectType())
				.isPrerequisiteForId(course.getIsPrerequisiteFor()
						.stream()
						.map(Course::getId)
						.toList())
				.prerequisiteIds(course.getPrerequisites()
						.stream()
						.map(Course::getId)
						.toList())
				.build();
	}

	public static List<CourseResponse> toResponseList(List<Course> courses) {
		return courses.stream()
			.map(CourseMapper::toResponse)
			.collect(Collectors.toList());
	}
}
