package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.department.UploadDepartmentRequest;
import com.tronk.analysis.dto.request.department.UpdateDepartmentRequest;
import com.tronk.analysis.dto.response.department.DepartmentResponse;
import com.tronk.analysis.entity.Department;
import com.tronk.analysis.repository.DepartmentRepository;
import com.tronk.analysis.service.DepartmentService;
import com.tronk.analysis.mapper.Department.DepartmentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
	DepartmentRepository departmentRepository;
	@Override
	public DepartmentResponse createDepartment(UploadDepartmentRequest request) {
		Department department = new Department();
		department.setName(request.getName());
		Department savedEntity = departmentRepository.save(department);
		return DepartmentMapper.toResponse(savedEntity);
	}

	@Override
	public List<DepartmentResponse> getAllDepartments() {
		return DepartmentMapper.toResponseList(departmentRepository.findAll());
	}

	@Override
	public DepartmentResponse getDepartmentById(UUID id) {
		return DepartmentMapper.toResponse(
			departmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Department not found")));
	}

	@Override
	public DepartmentResponse updateDepartment(UpdateDepartmentRequest request) {
		Department entity = departmentRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Department not found"));
		Department department = new Department();
		department.setName(request.getName());
		return DepartmentMapper.toResponse(departmentRepository.save(entity));
	}

	@Override
	public void deleteDepartmentById(UUID id) {
		Department entity = departmentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Department not found"));
		departmentRepository.delete(entity);
	}

	@Override
	public String softDeleteDepartment(UUID id) {
		Department entity = departmentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Department not found"));
		entity.setDeletedAt(LocalDateTime.now());
		departmentRepository.save(entity);
		return "Department with ID " + id + " has been soft deleted";
	}
}
