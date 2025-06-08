package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.studentcourse.UpdateStudentCourseRequest;
import com.tronk.analysis.dto.request.studentcourse.UploadStudentCourseRequest;
import com.tronk.analysis.dto.response.studentCourse.StudentCourseResponse;

import java.util.List;
import java.util.UUID;

public interface StudentCourseService {

	StudentCourseResponse createStudentCourse(UploadStudentCourseRequest request);

	StudentCourseResponse getStudentCourseById(UUID id);

	List<StudentCourseResponse> getAllStudentCourses();

	StudentCourseResponse updateStudentCourse(UpdateStudentCourseRequest request);

	void deleteStudentCourseById(UUID id);

	String softDeleteStudentCourse(UUID id);

}