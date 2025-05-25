package com.tronk.analysis.mapper.semester;

import com.tronk.analysis.entity.Semester;
import com.tronk.analysis.dto.response.semester.SemesterResponse;
import java.util.List;
import java.util.stream.Collectors;

public class SemesterMapper {
	public SemesterMapper() {
	}

	public static SemesterResponse toResponse(Semester semester) {
		return SemesterResponse.builder()
				.id(semester.getId())
				.name(semester.getName())
				.startDate(semester.getStartDate())
				.endDate(semester.getEndDate())
				.build();
	}

	public static List<SemesterResponse> toResponseList(List<Semester> semesters) {
		return semesters.stream()
			.map(SemesterMapper::toResponse)
			.collect(Collectors.toList());
	}
}
