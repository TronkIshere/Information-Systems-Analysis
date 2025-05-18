package com.tronk.analysis.dto.request.receipt;

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
public class AssignReceiptToCourseRequest implements Serializable {
    UUID receiptId;
    UUID courseId;
}
