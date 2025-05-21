package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.student.UpdateStudentRequest;
import com.tronk.analysis.dto.request.student.UploadStudentRequest;
import com.tronk.analysis.dto.response.student.StudentResponse;

import java.util.List;
import java.util.UUID;

public interface StudentService {

	StudentResponse createStudent(UploadStudentRequest request);

	StudentResponse getStudentById(UUID id);

	List<StudentResponse> getAllStudents();

	StudentResponse updateStudent(UpdateStudentRequest request);

	void deleteStudentById(UUID id);

	String softDeleteStudent(UUID id);

}