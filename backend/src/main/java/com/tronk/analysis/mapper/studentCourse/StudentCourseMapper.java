package com.tronk.analysis.mapper.studentCourse;

import com.tronk.analysis.entity.StudentCourse;
import com.tronk.analysis.dto.response.studentCourse.StudentCourseResponse;
import java.util.List;
import java.util.stream.Collectors;

public class StudentCourseMapper {
	public StudentCourseMapper() {
	}

	public static StudentCourseResponse toResponse(StudentCourse studentCourse) {
		return StudentCourseResponse.builder()
			.build();
	}

	public static List<StudentCourseResponse> toResponseList(List<StudentCourse> studentCourses) {
		return studentCourses.stream()
			.map(StudentCourseMapper::toResponse)
			.collect(Collectors.toList());
	}
}
