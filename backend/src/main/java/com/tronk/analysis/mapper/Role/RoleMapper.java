package com.tronk.analysis.mapper.Role;

import com.tronk.analysis.dto.response.role.RoleResponse;
import com.tronk.analysis.entity.Role;

import java.util.List;

public class RoleMapper {
    public RoleMapper() {
    }

    public static RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static List<RoleResponse> roleResponses(List<Role> roles) {
        return roles.stream()
                .map(RoleMapper::toRoleResponse)
                .toList();
    }
}

