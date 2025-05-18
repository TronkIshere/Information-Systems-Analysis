package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.user.SearchUsersRequest;
import com.tronk.analysis.dto.request.user.UpdateUserRequest;
import com.tronk.analysis.dto.request.user.UploadUserRequest;
import com.tronk.analysis.dto.response.common.PageResponse;
import com.tronk.analysis.dto.response.user.UserResponse;
import com.tronk.analysis.entity.Role;
import com.tronk.analysis.entity.User;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.user.UserMapper;
import com.tronk.analysis.repository.RoleRepository;
import com.tronk.analysis.repository.UserRepository;
import com.tronk.analysis.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Override
    public UserResponse registerUser(UploadUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setLoginName(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBirthDay(request.getBirthDay());
        user.setGender(request.isGender());

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }


    @Override
    public List<UserResponse> getUsers() {
        return UserMapper.userResponses(userRepository.findAll());
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXISTED));
        if (user != null) {
            userRepository.deleteById(id);
        }

    }

    @Override
    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserMapper.toUserResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getAllUserExceptAdminRole(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.getAllUserExceptAdminRole(pageable);

        List<User> userList = userPage.getContent();

        return PageResponse.<UserResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .data(UserMapper.userResponses(userList))
                .build();
    }

    @Override
    public PageResponse<UserResponse> searchUserByKeyWord(SearchUsersRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
        Page<User> userPage = userRepository.searchUserByKeyWord(pageable, request.getKeyword());

        List<User> userList = userPage.getContent();

        return PageResponse.<UserResponse>builder()
                .currentPage(request.getPageNo())
                .pageSize(pageable.getPageSize())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .data(UserMapper.userResponses(userList))
                .build();
    }

    @Override
    public UserResponse getUserByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) {
        User user = getUserById(UUID.fromString(request.getId()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setLoginName(request.getEmail());
        user.setBirthDay(request.getBirthDay());
        user.setGender(request.isGender());
        userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    @Override
    public User getUserById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + userId));
    }

    @Override
    public String softDelete(UUID userId) {
        LocalDateTime deleteAt = LocalDateTime.now();
        User user = getUserById(userId);
        user.setDeletedAt(deleteAt);
        userRepository.save(user);
        return "User with ID " + userId + " has been delete at " + deleteAt;
    }
}
