package com.tronk.analysis.dto.response.cashier;

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
public class CashierResponse implements Serializable {
	UUID id;
	String name;
	String email;
	String phoneNumber;
	String status;
	String loginName;
	String password;
	LocalDate birthDay;
	boolean gender;
	BigDecimal salary;
	LocalDate hireDate;
}
