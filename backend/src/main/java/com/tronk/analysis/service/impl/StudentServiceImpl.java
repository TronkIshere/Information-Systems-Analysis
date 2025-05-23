package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.student.UpdateStudentRequest;
import com.tronk.analysis.dto.request.student.UploadStudentRequest;
import com.tronk.analysis.dto.response.student.StudentResponse;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.student.StudentMapper;
import com.tronk.analysis.repository.StudentRepository;
import com.tronk.analysis.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public StudentResponse createStudent(UploadStudentRequest request) {
        Student student = new Student();
        student.setStudentCode(request.getStudentCode());
        student.setMajor(request.getMajor());
        student.setGpa(request.getGpa());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setLoginName(request.getLoginName());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setBirthDay(request.getBirthDay());
        student.setGender(request.isGender());
        student.setStatus("ACTIVE");
        studentRepository.save(student);
        return StudentMapper.toResponse(student);
    }

    @Override
    public StudentResponse getStudentById(UUID id) {
        return StudentMapper.toResponse(
                studentRepository.findById(id)
                        .orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND)));
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return StudentMapper.toResponseList(studentRepository.findAll());
    }

    @Override
    public StudentResponse updateStudent(UpdateStudentRequest request) {
        Student student = studentRepository.findById(request.getId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));
        student.setStudentCode(request.getStudentCode());
        student.setMajor(request.getMajor());
        student.setGpa(request.getGpa());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setBirthDay(request.getBirthDay());
        student.setGender(request.isGender());
        studentRepository.save(student);
        return StudentMapper.toResponse(student);
    }

    @Override
    public void deleteStudentById(UUID id) {
        Student entity = studentRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));
        studentRepository.delete(entity);
    }

    @Override
    public String softDeleteStudent(UUID id) {
        Student entity = studentRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));
        entity.setDeletedAt(LocalDateTime.now());
        studentRepository.save(entity);
        return "Student with ID " + id + " has been soft deleted";
    }
}
