package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.lecturer.UpdateLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UpdateLecturerWithUserRequest;
import com.tronk.analysis.dto.request.lecturer.UploadLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UploadLecturerWithUserRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import com.tronk.analysis.dto.response.lecturer.LecturerWithUserResponse;
import com.tronk.analysis.service.LecturerService;
import jakarta.validation.Valid;
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

	@GetMapping("/list/lecturersWithUserInfo")
	public ResponseAPI<List<LecturerWithUserResponse>> getAllLecturersWithUserInfo() {
		var result = lecturerService.getAllLecturersWithUserInfo();
		return ResponseAPI.<List<LecturerWithUserResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@GetMapping("/list/getAllLecturersByDepartmentId/{id}")
	public ResponseAPI<List<LecturerWithUserResponse>> getAllLecturersByDepartmentId(@PathVariable UUID id) {
		var result = lecturerService.getAllLecturersByDepartmentId(id);
		return ResponseAPI.<List<LecturerWithUserResponse>>builder()
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

	@PostMapping("/lecturerWithUserInfo")
	public ResponseAPI<LecturerWithUserResponse> createLecturerWithUserInfo(
			@Valid @RequestBody UploadLecturerWithUserRequest request) {
		var result = lecturerService.createLecturer(request);
		return ResponseAPI.<LecturerWithUserResponse>builder()
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

	@PutMapping("/lecturerWithUserInfo/{lecturerId}")
	public ResponseAPI<LecturerWithUserResponse> updateLecturerWithUserInfo(
			@Valid @RequestBody UpdateLecturerWithUserRequest request) {
		var result = lecturerService.updateLecturerWithUserInfo(request);
		return ResponseAPI.<LecturerWithUserResponse>builder()
				.code(HttpStatus.OK.value())
				.message("Lecturer updated successfully")
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
}
