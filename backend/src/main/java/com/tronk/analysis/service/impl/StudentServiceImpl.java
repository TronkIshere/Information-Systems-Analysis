package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.student.UploadStudentRequest;
import com.tronk.analysis.dto.request.student.UpdateStudentRequest;
import com.tronk.analysis.dto.response.student.StudentResponse;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.repository.StudentRepository;
import com.tronk.analysis.service.StudentService;
import com.tronk.analysis.mapper.StudentMapper;
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
public class StudentServiceImpl implements StudentService {
	StudentRepository studentRepository;
	@Override
	public StudentResponse createStudent(UploadStudentRequest request) {
		Student student = new Student();
		student.setStudent_code(request.getStudent_code());
		student.setMajor(request.getMajor());
		student.setGpa(request.getGpa());
		Student savedEntity = studentRepository.save(student);
		return StudentMapper.toResponse(savedEntity);
	}

	@Override
	public List<StudentResponse> getAllStudents() {
		return StudentMapper.toResponseList(studentRepository.findAll());
	}

	@Override
	public StudentResponse getStudentById(UUID id) {
		return StudentMapper.toResponse(
			studentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Student not found")));
	}

	@Override
	public StudentResponse updateStudent(UpdateStudentRequest request) {
		Student entity = studentRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Student not found"));
		Student student = new Student();
		student.setStudent_code(request.getStudent_code());
		student.setMajor(request.getMajor());
		student.setGpa(request.getGpa());
		return StudentMapper.toResponse(studentRepository.save(entity));
	}

	@Override
	public void deleteStudentById(UUID id) {
		Student entity = studentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Student not found"));
		studentRepository.delete(entity);
	}

	@Override
	public String softDeleteStudent(UUID id) {
		Student entity = studentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Student not found"));
		entity.setDeletedAt(LocalDateTime.now());
		studentRepository.save(entity);
		return "Student with ID " + id + " has been soft deleted";
	}
}
