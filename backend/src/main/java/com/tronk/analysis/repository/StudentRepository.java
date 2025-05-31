package com.tronk.analysis.repository;

import com.tronk.analysis.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByLoginName(String loginName);

    Optional<Student> findByEmail(String email);
}