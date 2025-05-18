package com.tronk.analysis.mapper.student;

import com.tronk.analysis.dto.response.student.StudentWithUserResponse;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.entity.User;

import java.util.ArrayList;
import java.util.List;

public class StudentWithUserMapper {
    public StudentWithUserMapper (){}

    public static StudentWithUserResponse toResponse(Student student, User user) {
        if (student == null || user == null) {
            throw new IllegalArgumentException("Student and User must not be null");
        }

        return StudentWithUserResponse.builder()
                // user
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .birthDay(user.getBirthDay())
                .gender(user.isGender())
                // student
                .studentCode(student.getStudentCode())
                .major(student.getMajor())
                .gpa(student.getGpa())
                .build();
    }

    public static List<StudentWithUserResponse> toResponseList(List<Student> students, List<User> users) {
        if (students.size() != users.size()) {
            throw new IllegalArgumentException("Students and Users lists must have the same size");
        }

        List<StudentWithUserResponse> responses = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            responses.add(toResponse(students.get(i), users.get(i)));
        }
        return responses;
    }
}
