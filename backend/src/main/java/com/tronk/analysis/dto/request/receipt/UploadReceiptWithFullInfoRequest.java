package com.tronk.analysis.dto.request.receipt;

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
public class UploadReceiptWithFullInfoRequest implements Serializable {
    BigDecimal totalAmount;
    boolean status;
    String description;
    LocalDate paymentDate;
    String studentCode;
    String studentName;
    String studentClass;
    UUID studentId;
    UUID semesterId;
    List<UUID> courseId;
    UUID cashierId;
}
