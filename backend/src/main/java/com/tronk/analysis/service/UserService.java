package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.user.SearchUsersRequest;
import com.tronk.analysis.dto.request.user.UpdateUserRequest;
import com.tronk.analysis.dto.request.user.UploadUserRequest;
import com.tronk.analysis.dto.response.common.PageResponse;
import com.tronk.analysis.dto.response.user.UserResponse;
import com.tronk.analysis.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse registerUser(UploadUserRequest request);

    List<UserResponse> getUsers();

    void deleteUser(UUID id);

    UserResponse getUser(String email);

    User getUserById(UUID userId);

    PageResponse<UserResponse> getAllUserExceptAdminRole(Integer pageNo, Integer pageSize);

    PageResponse<UserResponse> searchUserByKeyWord(SearchUsersRequest request);

    UserResponse getUserByUserId(UUID userId);

    UserResponse updateUser(UpdateUserRequest request);

    String softDelete(UUID userId);
}

