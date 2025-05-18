package com.tronk.analysis.mapper.Lecturer;

import com.tronk.analysis.dto.response.lecturer.LecturerWithUserResponse;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LecturerWithUserMapper {
    public LecturerWithUserMapper() {}

    public static LecturerWithUserResponse toResponse(Lecturer lecturer, User user) {
        if (lecturer == null || user == null) {
            throw new IllegalArgumentException("Lecturer and User must not be null");
        }

        return LecturerWithUserResponse.builder()
                // user
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .birthDay(user.getBirthDay())
                .gender(user.isGender())
                // lecturer
                .lecturerId(lecturer.getId())
                .lecturerCode(lecturer.getLecturerCode())
                .academicRank(lecturer.getAcademicRank())
                .salary(lecturer.getSalary())
                .hireDate(lecturer.getHireDate())
                .researchField(lecturer.getResearchField())
                .build();
    }

    public static List<LecturerWithUserResponse> toResponseList(List<Lecturer> lecturers, List<User> users) {
        if (lecturers.size() != users.size()) {
            throw new IllegalArgumentException("Lecturers and Users lists must have the same size");
        }

        List<LecturerWithUserResponse> responses = new ArrayList<>();
        for (int i = 0; i < lecturers.size(); i++) {
            responses.add(toResponse(lecturers.get(i), users.get(i)));
        }
        return responses;
    }
}