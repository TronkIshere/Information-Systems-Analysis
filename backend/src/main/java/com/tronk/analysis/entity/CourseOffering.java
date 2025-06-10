package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseOffering extends AbstractEntity<UUID> {
    LocalDate startDate;
    LocalDate endDate;

    @ManyToMany(mappedBy = "courseOfferings")
    Set<Receipt> receipts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    Semester semester;

    @OneToMany(mappedBy = "courseOffering", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<StudentCourse> studentCourses = new HashSet<>();
}
