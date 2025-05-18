package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.semester.UploadSemesterRequest;
import com.tronk.analysis.dto.request.semester.UpdateSemesterRequest;
import com.tronk.analysis.dto.response.semester.SemesterResponse;
import java.util.UUID;
import java.util.List;

public interface SemesterService {

	SemesterResponse createSemester(UploadSemesterRequest request);

	SemesterResponse getSemesterById(UUID id);

	List<SemesterResponse> getAllSemesters();

	SemesterResponse updateSemester(UpdateSemesterRequest request);

	void deleteSemesterById(UUID id);

	String softDeleteSemester(UUID id);

}