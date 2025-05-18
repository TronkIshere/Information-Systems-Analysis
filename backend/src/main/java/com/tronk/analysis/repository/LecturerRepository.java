package com.tronk.analysis.repository;

import java.util.List;
import java.util.UUID;
import com.tronk.analysis.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {
    List<Lecturer> findByDepartmentId(UUID departmentId);
}