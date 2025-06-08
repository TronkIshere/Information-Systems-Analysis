package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
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
public class Receipt extends AbstractEntity<UUID> {
	BigDecimal totalAmount;
	boolean status;
	LocalDate paymentDate;
	String studentCode;
	@Column(columnDefinition = "NVARCHAR(1000)")
	String description;
	@Column(columnDefinition = "NVARCHAR(255)")
	String studentName;
	@Column(columnDefinition = "NVARCHAR(255)")
	String studentClass;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cashier_id")
	Cashier cashier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "semester_id")
	Semester semester;

	@OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<CourseOffering> courseOfferings = new HashSet<>();
}
