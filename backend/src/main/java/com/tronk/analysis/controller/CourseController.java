package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.course.UpdateCourseRequest;
import com.tronk.analysis.dto.request.course.UploadCourseRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.course.CourseResponse;
import com.tronk.analysis.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
	private final CourseService courseService;

	@GetMapping("/list")
	ResponseAPI<List<CourseResponse>> getAllUsers() {
		var result = courseService.getAllCourses();
		return ResponseAPI.<List<CourseResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@PostMapping
	public ResponseAPI<CourseResponse> createCourse(
			@RequestBody UploadCourseRequest request) {
		var result = courseService.createCourse(request);
		return ResponseAPI.<CourseResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<CourseResponse> getCourseById(@PathVariable UUID id) {
		var result = courseService.getCourseById(id);
		return ResponseAPI.<CourseResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<CourseResponse> updateCourse(
			@RequestBody UpdateCourseRequest request) {
		var result = courseService.updateCourse(request);
		return ResponseAPI.<CourseResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteCourse(@PathVariable UUID id) {
		courseService.deleteCourseById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("Course deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteCourse(@PathVariable UUID id) {
		courseService.softDeleteCourse(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
