package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student extends User {
	String studentCode;
	BigDecimal gpa;

	@Column(columnDefinition = "NVARCHAR(255)")
	String major;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Receipt> receipts = new HashSet<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<StudentCourse> studentCourses = new HashSet<>();
}
