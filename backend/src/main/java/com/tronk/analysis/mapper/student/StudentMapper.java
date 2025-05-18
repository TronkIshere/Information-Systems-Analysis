package com.tronk.analysis.mapper.student;

import com.tronk.analysis.entity.Student;
import com.tronk.analysis.dto.response.student.StudentResponse;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {
	public StudentMapper() {
	}

	public static StudentResponse toResponse(Student student) {
		return StudentResponse.builder()
				.id(student.getId())
				.student_code(student.getStudentCode())
				.major(student.getMajor())
				.gpa(student.getGpa())
				.build();
	}

	public static List<StudentResponse> toResponseList(List<Student> students) {
		return students.stream()
			.map(StudentMapper::toResponse)
			.collect(Collectors.toList());
	}
}
