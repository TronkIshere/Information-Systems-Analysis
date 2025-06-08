package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.studentcourse.UpdateStudentCourseRequest;
import com.tronk.analysis.dto.request.studentcourse.UploadStudentCourseRequest;
import com.tronk.analysis.dto.response.studentCourse.StudentCourseResponse;
import com.tronk.analysis.entity.CourseOffering;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.entity.StudentCourse;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.studentCourse.StudentCourseMapper;
import com.tronk.analysis.repository.CourseOfferingRepository;
import com.tronk.analysis.repository.StudentCourseRepository;
import com.tronk.analysis.repository.StudentRepository;
import com.tronk.analysis.service.StudentCourseService;
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
public class StudentCourseServiceImpl implements StudentCourseService {
	StudentCourseRepository studentCourseRepository;
	CourseOfferingRepository courseOfferingRepository;
	StudentRepository studentRepository;

	@Override
	public StudentCourseResponse createStudentCourse(UploadStudentCourseRequest request) {
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setGrade(request.getGrade());
		studentCourse.setEnrollmentDate(request.getEnrollmentDate());
		studentCourse.setStatus(request.getStatus());

		Student student = studentRepository.findById(request.getStudentId())
						.orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));
		studentCourse.setStudent(student);

		CourseOffering courseOffering = courseOfferingRepository.findById(request.getReceiptItemId())
						.orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));
		studentCourse.setCourseOffering(courseOffering);

		studentCourseRepository.save(studentCourse);
		return StudentCourseMapper.toResponse(studentCourse);
	}

	@Override
	public List<StudentCourseResponse> getAllStudentCourses() {
		return StudentCourseMapper.toResponseList(studentCourseRepository.findAll());
	}

	@Override
	public StudentCourseResponse getStudentCourseById(UUID id) {
		return StudentCourseMapper.toResponse(
			studentCourseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("StudentCourse not found")));
	}

	@Override
	public StudentCourseResponse updateStudentCourse(UpdateStudentCourseRequest request) {
		StudentCourse studentCourse = studentCourseRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("StudentCourse not found"));
		studentCourse.setGrade(request.getGrade());
		studentCourse.setEnrollmentDate(request.getEnrollmentDate());
		studentCourse.setStatus(request.getStatus());

		Student student = studentRepository.findById(request.getStudentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));
		studentCourse.setStudent(student);

		CourseOffering courseOffering = courseOfferingRepository.findById(request.getCourseOfferingId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));
		studentCourse.setCourseOffering(courseOffering);

		studentCourseRepository.save(studentCourse);
		return StudentCourseMapper.toResponse(studentCourse);
	}

	@Override
	public void deleteStudentCourseById(UUID id) {
		StudentCourse entity = studentCourseRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("StudentCourse not found"));
		studentCourseRepository.delete(entity);
	}

	@Override
	public String softDeleteStudentCourse(UUID id) {
		StudentCourse entity = studentCourseRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("StudentCourse not found"));
		entity.setDeletedAt(LocalDateTime.now());
		studentCourseRepository.save(entity);
		return "StudentCourse with ID " + id + " has been soft deleted";
	}
}
