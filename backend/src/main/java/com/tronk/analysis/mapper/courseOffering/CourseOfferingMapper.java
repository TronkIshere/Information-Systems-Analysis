package com.tronk.analysis.mapper.courseOffering;

import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;
import com.tronk.analysis.entity.CourseOffering;

import java.util.List;
import java.util.stream.Collectors;

public class CourseOfferingMapper {
	public CourseOfferingMapper() {
	}

	public static CourseOfferingResponse toResponse(CourseOffering receiptItem) {
		return CourseOfferingResponse.builder()
			.build();
	}

	public static List<CourseOfferingResponse> toResponseList(List<CourseOffering> receiptItems) {
		return receiptItems.stream()
			.map(CourseOfferingMapper::toResponse)
			.collect(Collectors.toList());
	}
}
