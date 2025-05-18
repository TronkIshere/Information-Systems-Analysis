package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.lecturer.*;
import com.tronk.analysis.dto.response.lecturer.LecturerResponse;
import com.tronk.analysis.dto.response.lecturer.LecturerWithUserResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.entity.Role;
import com.tronk.analysis.entity.User;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.Lecturer.LecturerMapper;
import com.tronk.analysis.mapper.Lecturer.LecturerWithUserMapper;
import com.tronk.analysis.repository.CourseRepository;
import com.tronk.analysis.repository.LecturerRepository;
import com.tronk.analysis.repository.RoleRepository;
import com.tronk.analysis.repository.UserRepository;
import com.tronk.analysis.service.LecturerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LecturerServiceImpl implements LecturerService {
	LecturerRepository lecturerRepository;
	CourseRepository courseRepository;
	PasswordEncoder passwordEncoder;
	UserRepository userRepository;
	RoleRepository roleRepository;
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
	public List<LecturerWithUserResponse> getAllLecturersByDepartmentId(UUID id){
		List<Lecturer> lecturers = lecturerRepository.findByDepartmentId(id);
		List<User> users = lecturers.stream()
				.map(Lecturer::getApp_user)
				.collect(Collectors.toList());

		return LecturerWithUserMapper.toResponseList(lecturers, users);
	}

	@Override
	public LecturerWithUserResponse createLecturer(UploadLecturerWithUserRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null");
		}

		// Create and save User
		User user = createLecturerUser(request);
		User savedUser = userRepository.save(user);

		// Create and save Lecturer
		Lecturer lecturer = createLecturer(request, savedUser);
		Lecturer savedLecturer = lecturerRepository.save(lecturer);

		return LecturerWithUserMapper.toResponse(savedLecturer, savedUser);
	}

	private User createLecturerUser(UploadLecturerWithUserRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setLoginName(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setBirthDay(request.getBirthDay());
		user.setGender(request.isGender());
		user.setStatus("ACTIVE");

		Role lecturerRole = roleRepository.findByName("ROLE_LECTURER")
				.orElseThrow(() -> new RuntimeException("ROLE_LECTURER not found"));
		user.setRoles(Collections.singletonList(lecturerRole));

		return user;
	}

	private Lecturer createLecturer(UploadLecturerWithUserRequest request, User savedUser) {
		Lecturer lecturer = new Lecturer();
		lecturer.setLecturerCode(request.getLecturerCode());
		lecturer.setAcademicRank(request.getAcademicRank());
		lecturer.setSalary(request.getSalary());
		lecturer.setHireDate(request.getHireDate());
		lecturer.setResearchField(request.getResearchField());
		lecturer.setApp_user(savedUser);

		return lecturer;
	}

	@Override
	public List<LecturerWithUserResponse> getAllLecturersWithUserInfo() {
		List<Lecturer> lecturers = lecturerRepository.findAll();
		List<User> users = lecturers.stream()
				.map(Lecturer::getApp_user)
				.collect(Collectors.toList());

		return LecturerWithUserMapper.toResponseList(lecturers, users);
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
	public LecturerWithUserResponse updateLecturerWithUserInfo(UpdateLecturerWithUserRequest request) {
		Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.LECTURER_NOT_FOUND));

		User user = lecturer.getApp_user();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setBirthDay(request.getBirthDay());
		user.setGender(request.isGender());

		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		lecturer.setLecturerCode(request.getLecturerCode());
		lecturer.setAcademicRank(request.getAcademicRank());
		lecturer.setSalary(request.getSalary());
		lecturer.setHireDate(request.getHireDate());
		lecturer.setResearchField(request.getResearchField());

		User updatedUser = userRepository.save(user);
		Lecturer updatedLecturer = lecturerRepository.save(lecturer);

		return LecturerWithUserMapper.toResponse(updatedLecturer, updatedUser);
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
