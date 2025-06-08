package com.tronk.analysis.dto.response.receipt;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
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
public class ReceiptResponse implements Serializable {
	UUID id;
	BigDecimal totalAmount;
	boolean status;
	String description;
	LocalDate paymentDate;
	String studentCode;
	String studentName;
	String studentClass;
	String semesterName;
	UUID studentId;
	UUID semesterId;
	UUID cashierId;
	List<UUID> courseOfferingIds;
}
