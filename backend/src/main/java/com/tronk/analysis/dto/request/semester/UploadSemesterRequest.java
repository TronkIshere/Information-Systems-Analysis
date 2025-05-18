package com.tronk.analysis.dto.request.semester;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadSemesterRequest implements Serializable {
	String name;
	Date startDate;
	Date endDate;
	Set receipts;
	UUID id;
}
