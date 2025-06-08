package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.courseOffering.UpdateCourseOfferingRequest;
import com.tronk.analysis.dto.request.courseOffering.UploadCourseOfferingRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;
import com.tronk.analysis.service.CourseOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/receiptItems")
public class CourseOfferingController {

	private final CourseOfferingService receiptItemService;

	@PostMapping
	public ResponseAPI<CourseOfferingResponse> createReceiptItem(
			@RequestBody UploadCourseOfferingRequest request) {
		var result = receiptItemService.createReceiptItem(request);
		return ResponseAPI.<CourseOfferingResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<CourseOfferingResponse> getReceiptItemById(@PathVariable UUID id) {
		var result = receiptItemService.getReceiptItemById(id);
		return ResponseAPI.<CourseOfferingResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<CourseOfferingResponse> updateReceiptItem(
			@RequestBody UpdateCourseOfferingRequest request) {
		var result = receiptItemService.updateReceiptItem(request);
		return ResponseAPI.<CourseOfferingResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteReceiptItem(@PathVariable UUID id) {
		receiptItemService.deleteReceiptItemById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("ReceiptItem deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteReceiptItem(@PathVariable UUID id) {
		receiptItemService.softDeleteReceiptItem(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
