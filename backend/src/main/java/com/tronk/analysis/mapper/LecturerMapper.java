package com.tronk.analysis.mapper;

import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import java.util.List;
import java.util.stream.Collectors;

public class LecturerMapper {
	public LecturerMapper() {
	}

	public static LecturerResponse toResponse(Lecturer lecturer) {
		return LecturerResponse.builder()
			.build();
	}

	public static List<LecturerResponse> toResponseList(List<Lecturer> lecturers) {
		return lecturers.stream()
			.map(LecturerMapper::toResponse)
			.collect(Collectors.toList());
	}
}
