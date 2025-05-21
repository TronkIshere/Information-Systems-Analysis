package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course extends AbstractEntity<UUID> {
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
}
