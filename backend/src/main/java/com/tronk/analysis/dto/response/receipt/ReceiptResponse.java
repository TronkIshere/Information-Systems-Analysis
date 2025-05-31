package com.tronk.analysis.dto.response.receipt;

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
public class ReceiptResponse implements Serializable {
	UUID id;
	BigDecimal totalAmount;
	boolean status;
	String description;
	LocalDate paymentDate;
	String studentCode;
	String studentName;
	String studentClass;
}
