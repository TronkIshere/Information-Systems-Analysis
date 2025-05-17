package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}