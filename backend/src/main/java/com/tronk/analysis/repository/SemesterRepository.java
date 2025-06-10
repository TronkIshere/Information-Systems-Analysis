package com.tronk.analysis.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.tronk.analysis.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SemesterRepository extends JpaRepository<Semester, UUID> {
    Optional<Semester> findTopByOrderByStartDateDesc();

    Optional<Semester> findFirstByOrderByCreatedAtDesc();

    @Query("SELECT s FROM Semester s WHERE s.startDate <= :currentDate ORDER BY s.startDate DESC")
    List<Semester> findLatestStartedSemester(@Param("currentDate") LocalDate currentDate);

    Optional<Semester> findFirstByStartDateLessThanEqualOrderByStartDateDesc(LocalDate currentDate);
}