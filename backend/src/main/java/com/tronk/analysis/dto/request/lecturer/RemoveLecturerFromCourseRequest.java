package com.tronk.analysis.dto.request.lecturer;

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
public class RemoveLecturerFromCourseRequest implements Serializable {
    UUID lecturerId;
    UUID courseId;
}
