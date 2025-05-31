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
    UUID id;
    BigDecimal totalAmount;
    boolean status;
    String description;
    LocalDate paymentDate;
    UUID studentId;
    String studentName;
    String studentCode;
    String studentClass;
    UUID semesterId;
    String semesterName;
    UUID cashierId;
    List<UUID> courseIds;
}
