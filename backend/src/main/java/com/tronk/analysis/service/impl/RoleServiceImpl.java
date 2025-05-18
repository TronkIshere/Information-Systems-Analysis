package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.role.AssignUserToRoleRequest;
import com.tronk.analysis.dto.request.role.RemoveUserRoleRequest;
import com.tronk.analysis.dto.request.role.RoleRequest;
import com.tronk.analysis.dto.response.role.RoleResponse;
import com.tronk.analysis.dto.response.user.UserResponse;
import com.tronk.analysis.entity.Role;
import com.tronk.analysis.entity.User;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.Role.RoleMapper;
import com.tronk.analysis.mapper.user.UserMapper;
import com.tronk.analysis.repository.RoleRepository;
import com.tronk.analysis.repository.UserRepository;
import com.tronk.analysis.service.RoleService;
import com.tronk.analysis.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    UserService userService;
    UserRepository userRepository;

    @Override
    public List<RoleResponse> getRoles() {
        List<Role> roleList = roleRepository.findAll();
        return RoleMapper.roleResponses(roleList);
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        String roleName = request.getName();
        Role role = new Role();
        role.setName("ROLE_" + roleName.toUpperCase());
        if (roleRepository.existsByName(roleName)) {
            throw new ApplicationException(ErrorCode.USER_ALREADY_EXIST_EXCEPTION);
        }
        roleRepository.save(role);
        return RoleMapper.toRoleResponse(role);
    }

    @Override
    public void deleteRole(UUID roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public UserResponse removeUserFromRole(RemoveUserRoleRequest request) {
        User user = userService.getUserById(request.getUserId());
        Role role = roleRepository.getReferenceById(request.getRoleId());

        if (user.getRoles().contains(role)) {
            throw new ApplicationException(ErrorCode.USER_ALREADY_EXIST_EXCEPTION);
        }
        assignRoleToUser(user, role);
        roleRepository.save(role);

        return UserMapper.toUserResponse(user);
    }

    @Override
    public UserResponse assignRoleToUser(AssignUserToRoleRequest request) {
        User user = userService.getUserById(request.getUserId());
        Role role = roleRepository.getReferenceById(request.getRoleId());

        if (!user.getRoles().contains(role)) {
            throw new ApplicationException(ErrorCode.USER_DOES_NOT_HAVE_ROLE);
        }

        removeRoleFromUser(user, role);
        roleRepository.save(role);
        return UserMapper.toUserResponse(user);
    }

    @Override
    public RoleResponse removeAllUsersFromRole(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));

        for (User user : new ArrayList<>(role.getUsers())) {
            user.getRoles().remove(role);
            userRepository.save(user);
        }
        role.getUsers().clear();
        Role updatedRole = roleRepository.save(role);

        return RoleMapper.toRoleResponse(updatedRole);
    }

    private void assignRoleToUser(User user, Role role) {
        user.getRoles().add(role);
        role.getUsers().add(user);
    }

    private void removeRoleFromUser(User user, Role role) {
        user.getRoles().remove(role);
        role.getUsers().remove(user);
    }
}
