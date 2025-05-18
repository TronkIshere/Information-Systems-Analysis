package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.department.AddLecturerListRequest;
import com.tronk.analysis.dto.request.department.UpdateDepartmentRequest;
import com.tronk.analysis.dto.request.department.UploadDepartmentRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.response.department.DepartmentResponse;
import com.tronk.analysis.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {
	private final DepartmentService departmentService;

	@GetMapping("/list")
	ResponseAPI<List<DepartmentResponse>> getAllUsers() {
		var result = departmentService.getAllDepartments();
		return ResponseAPI.<List<DepartmentResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@PostMapping
	public ResponseAPI<DepartmentResponse> createDepartment(
			@RequestBody UploadDepartmentRequest request) {
		var result = departmentService.createDepartment(request);
		return ResponseAPI.<DepartmentResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<DepartmentResponse> getDepartmentById(@PathVariable UUID id) {
		var result = departmentService.getDepartmentById(id);
		return ResponseAPI.<DepartmentResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<DepartmentResponse> updateDepartment(
			@RequestBody UpdateDepartmentRequest request) {
		var result = departmentService.updateDepartment(request);
		return ResponseAPI.<DepartmentResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping("/addLecturerListRequest")
	public ResponseAPI<String> addLecturerList(
			@RequestBody AddLecturerListRequest request) {
		departmentService.addLecturerList(request);
		return ResponseAPI.<String>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data("success")
				.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteDepartment(@PathVariable UUID id) {
		departmentService.deleteDepartmentById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("Department deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteDepartment(@PathVariable UUID id) {
		departmentService.softDeleteDepartment(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
