package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.cashier.UpdateCashierRequest;
import com.tronk.analysis.dto.request.cashier.UploadCashierRequest;
import com.tronk.analysis.dto.response.cashier.CashierResponse;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.service.CashierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cashiers")
public class CashierController {
	private final CashierService cashierService;

	@GetMapping("/list")
	ResponseAPI<List<CashierResponse>> getAllUsers() {
		var result = cashierService.getAllCashiers();
		return ResponseAPI.<List<CashierResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@PostMapping
	public ResponseAPI<CashierResponse> createCashier(
			@RequestBody UploadCashierRequest request) {
		var result = cashierService.createCashier(request);
		return ResponseAPI.<CashierResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<CashierResponse> getCashierById(@PathVariable UUID id) {
		var result = cashierService.getCashierById(id);
		return ResponseAPI.<CashierResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<CashierResponse> updateCashier(
			@RequestBody UpdateCashierRequest request) {
		var result = cashierService.updateCashier(request);
		return ResponseAPI.<CashierResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteCashier(@PathVariable UUID id) {
		cashierService.deleteCashierById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("Cashier deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteCashier(@PathVariable UUID id) {
		cashierService.softDeleteCashier(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
