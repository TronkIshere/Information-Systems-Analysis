package com.tronk.analysis.dto.request.cashier;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCashierRequest implements Serializable {
	UUID id;
	BigDecimal salary;
	LocalDate hireDate;
	String name;
	String email;
	String phoneNumber;
	String status;
	String password;
	LocalDate birthDay;
	boolean gender;
}
