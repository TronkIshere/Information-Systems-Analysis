package com.tronk.analysis.mapper.courseOffering;

import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;
import com.tronk.analysis.entity.CourseOffering;

import java.util.List;
import java.util.stream.Collectors;

public class CourseOfferingMapper {
	public CourseOfferingMapper() {
	}

	public static CourseOfferingResponse toResponse(CourseOffering courseOffering) {
		return CourseOfferingResponse.builder()
				.id(courseOffering.getId())
				.startDate(courseOffering.getStartDate())
				.endDate(courseOffering.getEndDate())
				.courseId(courseOffering.getCourse().getId())
				.courseName(courseOffering.getCourse().getName())
				.baseFeeCredit(courseOffering.getCourse().getBaseFeeCredit())
				.credit(courseOffering.getCourse().getCredit())
				.subjectType(courseOffering.getCourse().isSubjectType())
				.build();
	}

	public static List<CourseOfferingResponse> toResponseList(List<CourseOffering> courseOfferings) {
		return courseOfferings.stream()
			.map(CourseOfferingMapper::toResponse)
			.collect(Collectors.toList());
	}
}
