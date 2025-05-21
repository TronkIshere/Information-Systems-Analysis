package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Receipt extends AbstractEntity<UUID> {
	BigDecimal totalAmount;
	boolean status;
	String description;
	LocalDate paymentDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "semester_id")
	Semester semester;

	@ManyToMany(fetch = FetchType.EAGER,
			cascade = {CascadeType.MERGE, CascadeType.DETACH})
	@JoinTable(name = "receipt_courses",
			joinColumns = @JoinColumn(name = "receipt_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
	Collection<Course> courses = new HashSet<>();
}
