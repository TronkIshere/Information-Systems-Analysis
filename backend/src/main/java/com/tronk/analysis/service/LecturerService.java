package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.lecturer.UpdateLecturerWithUserRequest;
import com.tronk.analysis.dto.request.lecturer.UploadLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UpdateLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UploadLecturerWithUserRequest;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import com.tronk.analysis.dto.response.lecturer.LecturerWithUserResponse;

import java.util.UUID;
import java.util.List;

public interface LecturerService {

	LecturerResponse createLecturer(UploadLecturerRequest request);

	LecturerWithUserResponse createLecturer(UploadLecturerWithUserRequest request);

	List<LecturerWithUserResponse> getAllLecturersByDepartmentId(UUID id);

	List<LecturerWithUserResponse> getAllLecturersWithUserInfo();

	LecturerResponse getLecturerById(UUID id);

	List<LecturerResponse> getAllLecturers();

	LecturerResponse updateLecturer(UpdateLecturerRequest request);

	LecturerWithUserResponse updateLecturerWithUserInfo(UpdateLecturerWithUserRequest request);

	void deleteLecturerById(UUID id);

	String softDeleteLecturer(UUID id);

}