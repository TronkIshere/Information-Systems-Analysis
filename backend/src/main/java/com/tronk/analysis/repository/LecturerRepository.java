package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {
}