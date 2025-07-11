package com.tronk.analysis.mapper.Department;

import com.tronk.analysis.entity.Department;
import com.tronk.analysis.dto.response.department.DepartmentResponse;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {
	public DepartmentMapper() {
	}

	public static DepartmentResponse toResponse(Department department) {
		return DepartmentResponse.builder()
				.id(department.getId())
				.name(department.getName())
				.build();
	}

	public static List<DepartmentResponse> toResponseList(List<Department> departments) {
		return departments.stream()
			.map(DepartmentMapper::toResponse)
			.collect(Collectors.toList());
	}
}
