package com.tronk.analysis.mapper;

import com.tronk.analysis.entity.Semester;
import com.tronk.analysis.dto.response.semester.SemesterResponse;
import java.util.List;
import java.util.stream.Collectors;

public class SemesterMapper {
	public SemesterMapper() {
	}

	public static SemesterResponse toResponse(Semester semester) {
		return SemesterResponse.builder()
			.build();
	}

	public static List<SemesterResponse> toResponseList(List<Semester> semesters) {
		return semesters.stream()
			.map(SemesterMapper::toResponse)
			.collect(Collectors.toList());
	}
}
