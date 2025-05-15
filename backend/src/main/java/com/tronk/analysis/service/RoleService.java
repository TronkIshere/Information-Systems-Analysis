package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.role.AssignUserToRoleRequest;
import com.tronk.analysis.dto.request.role.RemoveUserRoleRequest;
import com.tronk.analysis.dto.request.role.RoleRequest;
import com.tronk.analysis.dto.response.role.RoleResponse;
import com.tronk.analysis.dto.response.user.UserResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleResponse> getRoles();

    RoleResponse createRole(RoleRequest theRole);

    void deleteRole(UUID id);

    UserResponse removeUserFromRole(RemoveUserRoleRequest request);

    UserResponse assignRoleToUser(AssignUserToRoleRequest request);

    RoleResponse removeAllUsersFromRole(UUID roleId);

}
