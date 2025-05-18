package com.tronk.analysis.dto.response.department;

import lombok.*;
import java.io.Serializable;
import java.util.UUID;

import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse implements Serializable {
	UUID id;
	String name;
}
