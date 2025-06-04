package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
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
	Collection<Receipt> receipts = new HashSet<>();

	@ManyToMany(mappedBy = "courses")
	Collection<Lecturer> lecturers = new HashSet<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<StudentCourse> studentCourses = new HashSet<>();

	@ManyToMany
	@JoinTable(
			name = "course_prerequisites",
			joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
	)
	private Set<Course> prerequisites = new HashSet<>();
}
