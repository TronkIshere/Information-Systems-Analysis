package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.semester.UpdateSemesterRequest;
import com.tronk.analysis.dto.request.semester.UploadSemesterRequest;
import com.tronk.analysis.dto.response.semester.SemesterResponse;
import com.tronk.analysis.entity.Semester;
import com.tronk.analysis.mapper.SemesterMapper;
import com.tronk.analysis.repository.SemesterRepository;
import com.tronk.analysis.service.SemesterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SemesterServiceImpl implements SemesterService {
	SemesterRepository semesterRepository;
	@Override
	public SemesterResponse createSemester(UploadSemesterRequest request) {
		Semester semester = new Semester();
		semester.setName(request.getName());
		semester.setStartDate(request.getStartDate());
		semester.setEndDate(request.getEndDate());
		Semester savedEntity = semesterRepository.save(semester);
		return SemesterMapper.toResponse(savedEntity);
	}

	@Override
	public List<SemesterResponse> getAllSemesters() {
		return SemesterMapper.toResponseList(semesterRepository.findAll());
	}

	@Override
	public SemesterResponse getSemesterById(UUID id) {
		return SemesterMapper.toResponse(
			semesterRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Semester not found")));
	}

	@Override
	public SemesterResponse updateSemester(UpdateSemesterRequest request) {
		Semester entity = semesterRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Semester not found"));
		Semester semester = new Semester();
		semester.setName(request.getName());
		semester.setStartDate(request.getStartDate());
		semester.setEndDate(request.getEndDate());
		return SemesterMapper.toResponse(semesterRepository.save(entity));
	}

	@Override
	public void deleteSemesterById(UUID id) {
		Semester entity = semesterRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Semester not found"));
		semesterRepository.delete(entity);
	}

	@Override
	public String softDeleteSemester(UUID id) {
		Semester entity = semesterRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Semester not found"));
		entity.setDeletedAt(LocalDateTime.now());
		semesterRepository.save(entity);
		return "Semester with ID " + id + " has been soft deleted";
	}
}
