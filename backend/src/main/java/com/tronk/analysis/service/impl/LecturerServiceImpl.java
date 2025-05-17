package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.lecturer.UploadLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UpdateLecturerRequest;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.repository.LecturerRepository;
import com.tronk.analysis.service.LecturerService;
import com.tronk.analysis.mapper.LecturerMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LecturerServiceImpl implements LecturerService {
	LecturerRepository lecturerRepository;
	@Override
	public LecturerResponse createLecturer(UploadLecturerRequest request) {
		Lecturer lecturer = new Lecturer();
		lecturer.setLecturerCode(request.getLecturerCode());
		lecturer.setAcademicRank(request.getAcademicRank());
		lecturer.setSalary(request.getSalary());
		lecturer.setHireDate(request.getHireDate());
		lecturer.setResearchField(request.getResearchField());
		Lecturer savedEntity = lecturerRepository.save(lecturer);
		return LecturerMapper.toResponse(savedEntity);
	}

	@Override
	public List<LecturerResponse> getAllLecturers() {
		return LecturerMapper.toResponseList(lecturerRepository.findAll());
	}

	@Override
	public LecturerResponse getLecturerById(UUID id) {
		return LecturerMapper.toResponse(
			lecturerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Lecturer not found")));
	}

	@Override
	public LecturerResponse updateLecturer(UpdateLecturerRequest request) {
		Lecturer entity = lecturerRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
		Lecturer lecturer = new Lecturer();
		lecturer.setLecturerCode(request.getLecturerCode());
		lecturer.setAcademicRank(request.getAcademicRank());
		lecturer.setSalary(request.getSalary());
		lecturer.setHireDate(request.getHireDate());
		lecturer.setResearchField(request.getResearchField());
		return LecturerMapper.toResponse(lecturerRepository.save(entity));
	}

	@Override
	public void deleteLecturerById(UUID id) {
		Lecturer entity = lecturerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
		lecturerRepository.delete(entity);
	}

	@Override
	public String softDeleteLecturer(UUID id) {
		Lecturer entity = lecturerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
		entity.setDeletedAt(LocalDateTime.now());
		lecturerRepository.save(entity);
		return "Lecturer with ID " + id + " has been soft deleted";
	}
}
