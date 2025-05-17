package com.tronk.analysis.dto.request.department;

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
public class UploadDepartmentRequest implements Serializable {
	String name;
	UUID id;
}
