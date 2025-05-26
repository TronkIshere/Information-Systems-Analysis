package com.tronk.analysis.mapper.Lecturer;

import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import java.util.List;
import java.util.stream.Collectors;

public class LecturerMapper {
	public LecturerMapper() {
	}

	public static LecturerResponse toResponse(Lecturer lecturer) {
		return LecturerResponse.builder()
				// lecturer
				.id(lecturer.getId())
				.lecturerCode(lecturer.getLecturerCode())
				.salary(lecturer.getSalary())
				.hireDate(lecturer.getHireDate())
				.researchField(lecturer.getResearchField())
				.name(lecturer.getName())
				.email(lecturer.getEmail())
				.academicRank(lecturer.getAcademicRank())
				.phoneNumber(lecturer.getPhoneNumber())
				.status(lecturer.getStatus())
				.birthDay(lecturer.getBirthDay())
				.gender(lecturer.isGender())
				// department
				.departmentId(lecturer.getDepartment().getId())
				// courses
				.courseIds(
						lecturer.getCourses()
								.stream()
								.map(Course::getId)
								.collect(Collectors.toList())
				)
				.build();
	}

	public static List<LecturerResponse> toResponseList(List<Lecturer> lecturers) {
		return lecturers.stream()
			.map(LecturerMapper::toResponse)
			.collect(Collectors.toList());
	}
}
