package com.tronk.analysis.dto.request.semester;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSemesterRequest implements Serializable {
	UUID id;
	String name;
	LocalDate startDate;
	LocalDate endDate;
}
