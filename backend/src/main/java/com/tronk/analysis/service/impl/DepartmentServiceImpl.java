package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.department.AddLecturerListRequest;
import com.tronk.analysis.dto.request.department.UploadDepartmentRequest;
import com.tronk.analysis.dto.request.department.UpdateDepartmentRequest;
import com.tronk.analysis.dto.response.department.DepartmentResponse;
import com.tronk.analysis.entity.Department;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.repository.DepartmentRepository;
import com.tronk.analysis.repository.LecturerRepository;
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
	LecturerRepository lecturerRepository;
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
		Department department = departmentRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Department not found"));
		department.setName(request.getName());
		departmentRepository.save(department);
		return DepartmentMapper.toResponse(department);
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

	@Override
	public void addLecturerList(AddLecturerListRequest request) {
		checkAddLecturerListRequest(request);

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.DEPARTMENT_NOT_FOUND));

		List<Lecturer> lecturers = lecturerRepository.findAllById(request.getLecturerIds());
		checkLecturers(lecturers, request);

		lecturers.forEach(lecturer -> {
			checkIfLecturerAlreadyInDepartment(lecturer, department);
			lecturer.setDepartment(department);
		});

		lecturerRepository.saveAll(lecturers);
	}

	private void checkAddLecturerListRequest(AddLecturerListRequest request){
		if (request == null || request.getLecturerIds() == null || request.getLecturerIds().isEmpty()) {
			throw new IllegalArgumentException("Request or lecturer IDs cannot be null or empty");
		}
	}

	private void checkLecturers(List<Lecturer> lecturers, AddLecturerListRequest request) {
		if (lecturers.size() != request.getLecturerIds().size()) {
			List<UUID> foundIds = lecturers.stream()
					.map(Lecturer::getId)
					.toList();

			List<UUID> notFoundIds = request.getLecturerIds().stream()
					.filter(id -> !foundIds.contains(id))
					.toList();

			throw new ApplicationException(ErrorCode.LECTURER_NOT_FOUND,
					"Lecturers not found with IDs: " + notFoundIds);
		}
	}

	private void checkIfLecturerAlreadyInDepartment(Lecturer lecturer, Department department) {
		if (lecturer.getDepartment() != null && lecturer.getDepartment().equals(department)) {
			throw new ApplicationException(ErrorCode.LECTURER_ALREADY_IN_DEPARTMENT,
					"Lecturer with ID " + lecturer.getId() + " already in this department");
		}
	}
}
