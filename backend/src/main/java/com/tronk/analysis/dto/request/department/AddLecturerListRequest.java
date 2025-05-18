package com.tronk.analysis.dto.request.department;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddLecturerListRequest implements Serializable {
    UUID departmentId;
    List<UUID> lecturerIds;
}
