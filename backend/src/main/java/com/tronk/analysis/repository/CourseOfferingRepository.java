package com.tronk.analysis.repository;

import java.util.List;
import java.util.UUID;
import com.tronk.analysis.entity.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, UUID> {
    @Query("SELECT co FROM CourseOffering co LEFT JOIN FETCH co.receipt r LEFT JOIN FETCH r.student")
    List<CourseOffering> findAllWithReceiptAndStudent();
}