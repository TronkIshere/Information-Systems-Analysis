package com.tronk.analysis.repository;

import java.util.List;
import java.util.UUID;
import com.tronk.analysis.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    @EntityGraph(attributePaths = {"lecturers"})
    @Query("SELECT c FROM Course c")
    List<Course> findAllWithLecturers();
}