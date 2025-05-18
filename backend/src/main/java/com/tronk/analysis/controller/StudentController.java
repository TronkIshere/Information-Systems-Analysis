package com.tronk.analysis.controller;

import com.tronk.analysis.dto.request.student.UploadStudentWithUserRequest;
import com.tronk.analysis.dto.response.common.ResponseAPI;
import com.tronk.analysis.dto.request.student.UploadStudentRequest;
import com.tronk.analysis.dto.request.student.UpdateStudentRequest;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import com.tronk.analysis.dto.response.student.StudentResponse;
import com.tronk.analysis.dto.response.student.StudentWithUserResponse;
import com.tronk.analysis.service.StudentService;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
	private final StudentService studentService;

	@GetMapping("/list")
	ResponseAPI<List<StudentResponse>> getAllUsers() {
		var result = studentService.getAllStudents();
		return ResponseAPI.<List<StudentResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@GetMapping("/list/StudentsWithUserInfo")
	ResponseAPI<List<StudentWithUserResponse>> getAllStudentsWithUserInfo() {
		var result = studentService.getAllStudentsWithUserInfo();
		return ResponseAPI.<List<StudentWithUserResponse>>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@PostMapping
	public ResponseAPI<StudentResponse> createStudent(
			@RequestBody UploadStudentRequest request) {
		var result = studentService.createStudent(request);
		return ResponseAPI.<StudentResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PostMapping("/StudentWithUserInfo")
	public ResponseAPI<StudentWithUserResponse> createStudentWithUserInfo(
			@RequestBody UploadStudentWithUserRequest request) {
		var result = studentService.createStudentWithUser(request);
		return ResponseAPI.<StudentWithUserResponse>builder()
				.code(HttpStatus.OK.value())
				.message("success")
				.data(result)
				.build();
	}

	@GetMapping("/{id}")
	public ResponseAPI<StudentResponse> getStudentById(@PathVariable UUID id) {
		var result = studentService.getStudentById(id);
		return ResponseAPI.<StudentResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@PutMapping
	public ResponseAPI<StudentResponse> updateStudent(
			@RequestBody UpdateStudentRequest request) {
		var result = studentService.updateStudent(request);
		return ResponseAPI.<StudentResponse>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data(result)
			.build();
	}

	@DeleteMapping("/{id}")
	public ResponseAPI<String> deleteStudent(@PathVariable UUID id) {
		studentService.deleteStudentById(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("Student deleted successfully")
			.data("success")
			.build();
	}

	@PutMapping("/soft-delete/{id}")
	public ResponseAPI<String> softDeleteStudent(@PathVariable UUID id) {
		studentService.softDeleteStudent(id);
		return ResponseAPI.<String>builder()
			.code(HttpStatus.OK.value())
			.message("success")
			.data("success")
			.build();
	}
}
