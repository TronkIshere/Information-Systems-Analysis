package com.tronk.analysis.dto.request.receipt;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadReceiptWithFullInfoRequest implements Serializable {
    // receipt
    BigDecimal totalAmount;
    boolean status;
    String description;
    // student
    UUID studentId;
    // semester
    UUID semesterId;
    // course
    List<UUID> courseId;
}
