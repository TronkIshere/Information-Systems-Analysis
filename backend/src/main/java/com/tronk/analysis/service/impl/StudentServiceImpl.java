package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.student.UploadStudentRequest;
import com.tronk.analysis.dto.request.student.UpdateStudentRequest;
import com.tronk.analysis.dto.request.student.UploadStudentWithUserRequest;
import com.tronk.analysis.dto.response.student.StudentResponse;
import com.tronk.analysis.dto.response.student.StudentWithUserResponse;
import com.tronk.analysis.entity.Role;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.entity.User;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.student.StudentWithUserMapper;
import com.tronk.analysis.repository.RoleRepository;
import com.tronk.analysis.repository.StudentRepository;
import com.tronk.analysis.repository.UserRepository;
import com.tronk.analysis.service.StudentService;
import com.tronk.analysis.mapper.student.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {
	StudentRepository studentRepository;
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepository;
	UserRepository userRepository;
	@Override
	public StudentResponse createStudent(UploadStudentRequest request) {
		Student student = new Student();
		student.setStudentCode(request.getStudent_code());
		student.setMajor(request.getMajor());
		student.setGpa(request.getGpa());

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXISTED));
		student.setApp_user(user);

		Student savedEntity = studentRepository.save(student);
		return StudentMapper.toResponse(savedEntity);
	}

	@Override
	public StudentWithUserResponse createStudentWithUser(UploadStudentWithUserRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null");
		}

		User user = createUser(request);
		User savedUser = userRepository.save(user);

		Student student = createStudent(request, savedUser);
		Student savedStudent = studentRepository.save(student);

		return StudentWithUserMapper.toResponse(savedStudent, savedUser);
	}

	private User createUser(UploadStudentWithUserRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setLoginName(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setBirthDay(request.getBirthDay());
		user.setGender(request.isGender());
		user.setStatus("ACTIVE");

		Role studentRole = roleRepository.findByName("ROLE_STUDENT")
				.orElseThrow(() -> new RuntimeException("ROLE_STUDENT not found"));
		user.setRoles(Collections.singletonList(studentRole));

		return user;
	}

	private Student createStudent(UploadStudentWithUserRequest request, User savedUser) {
		Student student = new Student();
		student.setStudentCode(request.getStudent_code());
		student.setMajor(request.getMajor());
		student.setGpa(request.getGpa());
		student.setApp_user(savedUser);

		return student;
	}

	@Override
	public List<StudentWithUserResponse> getAllStudentsWithUserInfo() {
		List<Student> students = studentRepository.findAll();
		List<User> users = students.stream()
				.map(Student::getApp_user)
				.collect(Collectors.toList());

		return StudentWithUserMapper.toResponseList(students, users);
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
		student.setStudentCode(request.getStudent_code());
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
