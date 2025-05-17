package com.tronk.analysis.dto.response.receipt;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptResponse implements Serializable {
	BigDecimal totalAmount;
	boolean status;
	String description;
}
