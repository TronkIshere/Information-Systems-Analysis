package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.department.UploadDepartmentRequest;
import com.tronk.analysis.dto.request.department.UpdateDepartmentRequest;
import com.tronk.analysis.dto.response.department.DepartmentResponse;
import java.util.UUID;
import java.util.List;

public interface DepartmentService {

	DepartmentResponse createDepartment(UploadDepartmentRequest request);

	DepartmentResponse getDepartmentById(UUID id);

	List<DepartmentResponse> getAllDepartments();

	DepartmentResponse updateDepartment(UpdateDepartmentRequest request);

	void deleteDepartmentById(UUID id);

	String softDeleteDepartment(UUID id);

}