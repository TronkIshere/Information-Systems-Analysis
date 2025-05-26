package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.lecturer.AssignLecturerToCourseRequest;
import com.tronk.analysis.dto.request.lecturer.RemoveLecturerFromCourseRequest;
import com.tronk.analysis.dto.request.lecturer.UpdateLecturerRequest;
import com.tronk.analysis.dto.request.lecturer.UploadLecturerRequest;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.Department;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.Lecturer.LecturerMapper;
import com.tronk.analysis.repository.CourseRepository;
import com.tronk.analysis.repository.DepartmentRepository;
import com.tronk.analysis.repository.LecturerRepository;
import com.tronk.analysis.service.LecturerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LecturerServiceImpl implements LecturerService {
	LecturerRepository lecturerRepository;
	CourseRepository courseRepository;
	PasswordEncoder passwordEncoder;
	DepartmentRepository departmentRepository;
	@Override
	public LecturerResponse createLecturer(UploadLecturerRequest request) {
		Lecturer lecturer = new Lecturer();
		lecturer.setName(request.getName());
		lecturer.setEmail(request.getEmail());
		lecturer.setPhoneNumber(request.getPhoneNumber());
		lecturer.setLoginName(request.getEmail());
		lecturer.setPassword(passwordEncoder.encode(request.getPassword()));
		lecturer.setBirthDay(request.getBirthDay());
		lecturer.setGender(request.isGender());
		lecturer.setStatus(request.getStatus());
		lecturer.setRoles("ROLE_LECTURER");

		lecturer.setLecturerCode(request.getLecturerCode());
		lecturer.setAcademicRank(request.getAcademicRank());
		lecturer.setSalary(request.getSalary());
		lecturer.setHireDate(request.getHireDate());
		lecturer.setResearchField(request.getResearchField());
		Lecturer savedEntity = lecturerRepository.save(lecturer);

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.DEPARTMENT_NOT_FOUND));
		lecturer.setDepartment(department);

		Set<Course> courses = new HashSet<>(courseRepository.findAllById(request.getCourseIds()));
		lecturer.setCourses(courses);
		return LecturerMapper.toResponse(savedEntity);
	}

	@Override
	public List<LecturerResponse> getAllLecturersByDepartmentId(UUID id){
		List<Lecturer> lecturers = lecturerRepository.findByDepartmentId(id);
		return LecturerMapper.toResponseList(lecturers);
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
		Lecturer lecturer = lecturerRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));

		lecturer.setName(request.getName());
		lecturer.setEmail(request.getEmail());
		lecturer.setPhoneNumber(request.getPhoneNumber());
		lecturer.setBirthDay(request.getBirthDay());
		lecturer.setGender(request.isGender());

		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			lecturer.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		lecturer.setLecturerCode(request.getLecturerCode());
		System.out.println("Demo: " + request.getAcademicRank());
		lecturer.setAcademicRank(request.getAcademicRank());
		lecturer.setSalary(request.getSalary());
		lecturer.setHireDate(request.getHireDate());
		lecturer.setResearchField(request.getResearchField());
		lecturer.setStatus(request.getStatus());

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.DEPARTMENT_NOT_FOUND));
		lecturer.setDepartment(department);

		Set<Course> courses = new HashSet<>(courseRepository.findAllById(request.getCourseIds()));
		lecturer.setCourses(courses);

		lecturerRepository.save(lecturer);
		return LecturerMapper.toResponse(lecturer);
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

	@Override
	public void removeLecturerFromCourse(RemoveLecturerFromCourseRequest request) {
		Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.LECTURER_NOT_FOUND));

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));

		if (!lecturer.getCourses().contains(course)) {
			throw new ApplicationException(ErrorCode.LECTURER_DOES_NOT_HAVE_COURSE);
		}

		removeCourseFromLecturer(lecturer, course);
		lecturerRepository.save(lecturer);
	}

	private void removeCourseFromLecturer(Lecturer lecturer, Course course) {
		lecturer.getCourses().remove(course);
		course.getLecturers().remove(lecturer);
	}

	@Override
	public void assignLecturerToCourse(AssignLecturerToCourseRequest request) {
		Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.LECTURER_NOT_FOUND));

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));

		if (lecturer.getCourses().contains(course)) {
			throw new ApplicationException(ErrorCode.LECTURER_ALREADY_HAS_COURSE);
		}

		assignCourseToLecturer(lecturer, course);
		lecturerRepository.save(lecturer);
	}

	private void assignCourseToLecturer(Lecturer lecturer, Course course) {
		lecturer.getCourses().add(course);
		course.getLecturers().add(lecturer);
	}
}
