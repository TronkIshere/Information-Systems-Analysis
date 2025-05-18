package com.tronk.analysis.mapper.user;

import com.tronk.analysis.dto.response.user.UserResponse;
import com.tronk.analysis.entity.User;

import java.util.List;

public class UserMapper {
    public UserMapper() {
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getLoginName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static List<UserResponse> userResponses(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }
}

