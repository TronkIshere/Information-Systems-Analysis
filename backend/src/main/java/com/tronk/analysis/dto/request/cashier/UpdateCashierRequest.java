package com.tronk.analysis.dto.request.cashier;

import lombok.*;
import java.io.Serializable;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Set;
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
