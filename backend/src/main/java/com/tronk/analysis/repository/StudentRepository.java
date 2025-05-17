package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}