package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course extends AbstractEntity<UUID> {
	@Column(columnDefinition = "NVARCHAR(255)")
	String name;
	int credit;
	BigDecimal baseFeeCredit;
	boolean subjectType;

	@ManyToMany(mappedBy = "courses")
	Collection<Department> departments = new HashSet<>();

	@ManyToMany(mappedBy = "courses")
	Collection<Lecturer> lecturers = new HashSet<>();

	@OneToMany(mappedBy = "course")
	Set<CourseOffering> courseOfferings = new HashSet<>();
}
