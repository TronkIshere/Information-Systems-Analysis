package com.tronk.analysis.dto.request.receipt;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadReceiptRequest implements Serializable {
	BigDecimal totalAmount;
	boolean status;
	String description;
	LocalDate paymentDate;
	UUID id;
}
