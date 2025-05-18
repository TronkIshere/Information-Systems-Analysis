package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, UUID> {
}