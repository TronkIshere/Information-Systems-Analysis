package com.tronk.analysis.dto.response.semester;

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
public class SemesterResponse implements Serializable {
	UUID receiptId;
	UUID semesterId;
}
