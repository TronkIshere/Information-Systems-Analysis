package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.courseOffering.UpdateCourseOfferingRequest;
import com.tronk.analysis.dto.request.courseOffering.UploadCourseOfferingRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;
import com.tronk.analysis.service.CourseOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courseOfferings")
public class CourseOfferingController {
	private final CourseOfferingService courseOfferingService;

	@GetMapping("/open-list")
	public ResponseAPI<List<CourseOfferingResponse>> getOpenCourseOfferings() {
		var result = courseOfferingService.getOpenCourseOfferings();
		return ResponseAPI.<List<CourseOfferingResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@GetMapping("/list")
	ResponseAPI<List<CourseOfferingResponse>> getAllCourseOfferings() {
		var result = courseOfferingService.getAllCourseOfferings();
		return ResponseAPI.<List<CourseOfferingResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@PostMapping
	public ResponseAPI<CourseOfferingResponse> createCourseOffering(
			@RequestBody UploadCourseOfferingRequest request) {
		var result = courseOfferingService.createCourseOffering(request);
		return ResponseAPI.<CourseOfferingResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<CourseOfferingResponse> getCourseOfferingById(@PathVariable UUID id) {
		var result = courseOfferingService.getCourseOfferingById(id);
		return ResponseAPI.<CourseOfferingResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<CourseOfferingResponse> updateCourseOffering(
			@RequestBody UpdateCourseOfferingRequest request) {
		var result = courseOfferingService.updateCourseOffering(request);
		return ResponseAPI.<CourseOfferingResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteCourseOffering(@PathVariable UUID id) {
		courseOfferingService.deleteCourseOfferingById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("CourseOffering deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteReceiptItem(@PathVariable UUID id) {
		courseOfferingService.softDeleteCourseOffering(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
