package com.tronk.analysis.controller;

import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.request.semester.UploadSemesterRequest;
import com.tronk.analysis.dto.request.semester.UpdateSemesterRequest;
import com.tronk.analysis.dto.response.semester.SemesterResponse;
import com.tronk.analysis.service.SemesterService;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/semesters")
public class SemesterController {
	private final SemesterService semesterService;

	@PostMapping
	public ResponseAPI<SemesterResponse> createSemester(
			@RequestBody UploadSemesterRequest request) {
		var result = semesterService.createSemester(request);
		return ResponseAPI.<SemesterResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<SemesterResponse> getSemesterById(@PathVariable UUID id) {
		var result = semesterService.getSemesterById(id);
		return ResponseAPI.<SemesterResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<SemesterResponse> updateSemester(
			@RequestBody UpdateSemesterRequest request) {
		var result = semesterService.updateSemester(request);
		return ResponseAPI.<SemesterResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteSemester(@PathVariable UUID id) {
		semesterService.deleteSemesterById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("Semester deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteSemester(@PathVariable UUID id) {
		semesterService.softDeleteSemester(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
