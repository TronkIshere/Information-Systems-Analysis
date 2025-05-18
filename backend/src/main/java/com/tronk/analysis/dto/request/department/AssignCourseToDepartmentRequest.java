package com.tronk.analysis.dto.request.department;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignCourseToDepartmentRequest implements Serializable {
    UUID courseId;
    UUID departmentId;
}
