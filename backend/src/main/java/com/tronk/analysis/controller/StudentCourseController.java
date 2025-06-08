package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.studentcourse.UpdateStudentCourseRequest;
import com.tronk.analysis.dto.request.studentcourse.UploadStudentCourseRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.studentCourse.StudentCourseResponse;
import com.tronk.analysis.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studentCourses")
public class StudentCourseController {

	private final StudentCourseService studentCourseService;

	@PostMapping
	public ResponseAPI<StudentCourseResponse> createStudentCourse(
			@RequestBody UploadStudentCourseRequest request) {
		var result = studentCourseService.createStudentCourse(request);
		return ResponseAPI.<StudentCourseResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<StudentCourseResponse> getStudentCourseById(@PathVariable UUID id) {
		var result = studentCourseService.getStudentCourseById(id);
		return ResponseAPI.<StudentCourseResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<StudentCourseResponse> updateStudentCourse(
			@RequestBody UpdateStudentCourseRequest request) {
		var result = studentCourseService.updateStudentCourse(request);
		return ResponseAPI.<StudentCourseResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteStudentCourse(@PathVariable UUID id) {
		studentCourseService.deleteStudentCourseById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("StudentCourse deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteStudentCourse(@PathVariable UUID id) {
		studentCourseService.softDeleteStudentCourse(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
