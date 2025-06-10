package com.tronk.analysis.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.tronk.analysis.entity.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, UUID> {
    @Query("SELECT co FROM CourseOffering co LEFT JOIN FETCH co.receipts r LEFT JOIN FETCH r.student")
    List<CourseOffering> findAllWithReceiptAndStudent();

    @Query("SELECT o FROM CourseOffering o JOIN FETCH o.course")
    List<CourseOffering> findAllWithCourse();

    @Query("SELECT co FROM CourseOffering co WHERE :currentDate BETWEEN co.startDate AND co.endDate")
    List<CourseOffering> findOpenCourseOfferings(LocalDate currentDate);
}