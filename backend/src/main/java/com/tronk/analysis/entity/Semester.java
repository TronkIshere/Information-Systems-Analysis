package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Semester extends AbstractEntity<UUID> {
	String name;
	Date startDate;
	Date endDate;

	@OneToMany(mappedBy = "semester")
	Set<Receipt> receipts = new HashSet<>();
}
