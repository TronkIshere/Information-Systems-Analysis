package com.tronk.analysis.dto.response.receipt;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptFullInfoResponse implements Serializable {
    UUID receiptId;
    BigDecimal totalAmount;
    boolean status;
    String description;
    LocalDate paymentDate;
    // student
    UUID studentId;
    String studentName;
    // semester;
    UUID semesterId;
    String semesterName;
    // course;
    List<UUID> courseIds;
}
