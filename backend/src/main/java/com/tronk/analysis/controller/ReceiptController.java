package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.lecturer.AssignReceiptToSemesterRequest;
import com.tronk.analysis.dto.request.receipt.AssignReceiptToCourseRequest;
import com.tronk.analysis.dto.request.receipt.RemoveReceiptFromCourseRequest;
import com.tronk.analysis.dto.request.receipt.UpdateReceiptRequest;
import com.tronk.analysis.dto.request.receipt.UploadReceiptRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import com.tronk.analysis.service.ReceiptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/receipts")
public class ReceiptController {
	private final ReceiptService receiptService;

	@GetMapping("/list")
	ResponseAPI<List<ReceiptResponse>> getAllReceipt() {
		var result = receiptService.getAllReceipts();
		return ResponseAPI.<List<ReceiptResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@PostMapping
	public ResponseAPI<ReceiptResponse> createReceipt(
			@RequestBody UploadReceiptRequest request) {
		System.out.println("Testing");
		var result = receiptService.createReceipt(request);
		return ResponseAPI.<ReceiptResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PostMapping("/{id}/paid-status")
	public ResponseAPI<ReceiptResponse> markReceiptAsPaid(@PathVariable UUID id) {
		var result = receiptService.markAsPaid(id);
		return ResponseAPI.<ReceiptResponse>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<ReceiptResponse> getReceiptById(@PathVariable UUID id) {
		var result = receiptService.getReceiptById(id);
		return ResponseAPI.<ReceiptResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<ReceiptResponse> updateReceipt(
			@RequestBody UpdateReceiptRequest request) {
		var result = receiptService.updateReceipt(request);
		return ResponseAPI.<ReceiptResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteReceipt(@PathVariable UUID id) {
		receiptService.deleteReceiptById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("Receipt deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteReceipt(@PathVariable UUID id) {
		receiptService.softDeleteReceipt(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}

	@PostMapping("/remove-receipt-from-course")
	public ResponseAPI<String> removeReceiptFromCourse(
			@RequestBody RemoveReceiptFromCourseRequest request) {
		receiptService.removeReceiptFromCourse(request);
		return ResponseAPI.<String>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data("success")
				.build();
	}

	@PostMapping("/assign-receipt-to-course")
	ResponseAPI<String> assignReceiptToCourse(
			@RequestBody AssignReceiptToCourseRequest request) {
		receiptService.assignReceiptToCourse(request);
		return ResponseAPI.<String>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data("success")
				.build();
	}

	@PostMapping("/assign-to-semester")
	public ResponseAPI<String> assignReceiptToSemester(
			@Valid @RequestBody AssignReceiptToSemesterRequest request) {
		receiptService.assignReceiptToSemester(request);
		return ResponseAPI.<String>builder()
				.code(HttpStatus.OK.value())
				.message("Receipt assigned to semester successfully")
				.data("success")
				.build();
	}
}
