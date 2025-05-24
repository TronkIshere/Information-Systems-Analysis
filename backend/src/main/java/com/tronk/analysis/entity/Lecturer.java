package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lecturer extends User {
	String lecturerCode;
	@Column(columnDefinition = "NVARCHAR(255)")
	String academicRank;
	BigDecimal salary;
	LocalDate hireDate;
	String researchField;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	Department department;

	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {CascadeType.MERGE, CascadeType.DETACH})
	@JoinTable(name = "lecturer_course",
			joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
	Collection<Course> courses = new HashSet<>();
}
