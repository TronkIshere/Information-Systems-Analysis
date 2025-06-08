package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, UUID> {
}