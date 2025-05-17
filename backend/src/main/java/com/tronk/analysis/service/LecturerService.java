package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.lecturer.UploadLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UpdateLecturerRequest;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import java.util.UUID;
import java.util.List;

public interface LecturerService {

	LecturerResponse createLecturer(UploadLecturerRequest request);

	LecturerResponse getLecturerById(UUID id);

	List<LecturerResponse> getAllLecturers();

	LecturerResponse updateLecturer(UpdateLecturerRequest request);

	void deleteLecturerById(UUID id);

	String softDeleteLecturer(UUID id);

}