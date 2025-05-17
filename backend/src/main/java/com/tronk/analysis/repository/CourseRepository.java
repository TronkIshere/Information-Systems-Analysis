package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}