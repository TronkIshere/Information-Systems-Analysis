package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student extends AbstractEntity<UUID> {
	UUID student_code;
	String major;
	BigDecimal gpa;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_user_id")
	User app_user;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Receipt> receipts = new HashSet<>();
}
