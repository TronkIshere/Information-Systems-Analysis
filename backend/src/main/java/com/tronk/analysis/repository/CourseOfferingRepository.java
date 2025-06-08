package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, UUID> {
}