package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.lecturer.AssignLecturerToCourseRequest;
import com.tronk.analysis.dto.request.lecturer.RemoveLecturerFromCourseRequest;
import com.tronk.analysis.dto.request.lecturer.UpdateLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UploadLecturerRequest;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;

import java.util.List;
import java.util.UUID;

public interface LecturerService {

	LecturerResponse createLecturer(UploadLecturerRequest request);

	List<LecturerResponse> getAllLecturersByDepartmentId(UUID id);

	LecturerResponse getLecturerById(UUID id);

	List<LecturerResponse> getAllLecturers();

	LecturerResponse updateLecturer(UpdateLecturerRequest request);

	void deleteLecturerById(UUID id);

	String softDeleteLecturer(UUID id);

	void removeLecturerFromCourse(RemoveLecturerFromCourseRequest request);

	void assignLecturerToCourse(AssignLecturerToCourseRequest request);
}