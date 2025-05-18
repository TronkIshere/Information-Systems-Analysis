package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.student.UpdateStudentWithUserRequest;
import com.tronk.analysis.dto.request.student.UploadStudentRequest;
import com.tronk.analysis.dto.request.student.UpdateStudentRequest;
import com.tronk.analysis.dto.request.student.UploadStudentWithUserRequest;
import com.tronk.analysis.dto.response.student.StudentResponse;
import com.tronk.analysis.dto.response.student.StudentWithUserResponse;

import java.util.UUID;
import java.util.List;

public interface StudentService {

	StudentResponse createStudent(UploadStudentRequest request);

	StudentWithUserResponse createStudentWithUser(UploadStudentWithUserRequest request);

	List<StudentWithUserResponse> getAllStudentsWithUserInfo();

	StudentResponse getStudentById(UUID id);

	List<StudentResponse> getAllStudents();

	StudentResponse updateStudent(UpdateStudentRequest request);

	StudentWithUserResponse updateStudentWithUserInfo(UpdateStudentWithUserRequest request);

	void deleteStudentById(UUID id);

	String softDeleteStudent(UUID id);

}