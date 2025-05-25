package com.tronk.analysis.dto.response.semester;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SemesterResponse implements Serializable {
	UUID id;
	String name;
	LocalDate startDate;
	LocalDate endDate;
}
