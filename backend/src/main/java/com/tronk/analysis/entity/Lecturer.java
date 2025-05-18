package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.Date;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lecturer extends AbstractEntity<UUID> {
	String lecturerCode;
	String academicRank;
	BigDecimal salary;
	Date hireDate;
	String researchField;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	User app_user;

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
