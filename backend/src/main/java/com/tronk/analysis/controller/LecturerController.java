package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.lecturer.AssignLecturerToCourseRequest;
import com.tronk.analysis.dto.request.lecturer.RemoveLecturerFromCourseRequest;
import com.tronk.analysis.dto.request.lecturer.UpdateLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UploadLecturerRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import com.tronk.analysis.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecturers")
public class LecturerController {
	private final LecturerService lecturerService;

	@GetMapping("/list")
	ResponseAPI<List<LecturerResponse>> getAllUsers() {
		var result = lecturerService.getAllLecturers();
		return ResponseAPI.<List<LecturerResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@GetMapping("/list/getAllLecturersByDepartmentId/{id}")
	public ResponseAPI<List<LecturerResponse>> getAllLecturersByDepartmentId(@PathVariable UUID id) {
		var result = lecturerService.getAllLecturersByDepartmentId(id);
		return ResponseAPI.<List<LecturerResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@PostMapping
	public ResponseAPI<LecturerResponse> createLecturer(
			@RequestBody UploadLecturerRequest request) {
		var result = lecturerService.createLecturer(request);
		return ResponseAPI.<LecturerResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<LecturerResponse> getLecturerById(@PathVariable UUID id) {
		var result = lecturerService.getLecturerById(id);
		return ResponseAPI.<LecturerResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<LecturerResponse> updateLecturer(
			@RequestBody UpdateLecturerRequest request) {
		var result = lecturerService.updateLecturer(request);
		return ResponseAPI.<LecturerResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteLecturer(@PathVariable UUID id) {
		lecturerService.deleteLecturerById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("Lecturer deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteLecturer(@PathVariable UUID id) {
		lecturerService.softDeleteLecturer(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}

	@PostMapping("/remove-lecturer-from-course")
	public ResponseAPI<String> removeLecturerFromCourse(
			@RequestBody RemoveLecturerFromCourseRequest request) {
		lecturerService.removeLecturerFromCourse(request);
		return ResponseAPI.<String>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data("success")
				.build();
	}

	@PostMapping("/assign-lecturer-to-course")
	ResponseAPI<String> assignCourseToCourse(
			@RequestBody AssignLecturerToCourseRequest request) {
		lecturerService.assignLecturerToCourse(request);
		return ResponseAPI.<String>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data("success")
				.build();
	}
}
