package com.tronk.analysis.mapper.cashier;

import com.tronk.analysis.entity.Cashier;
import com.tronk.analysis.dto.response.cashier.CashierResponse;
import java.util.List;
import java.util.stream.Collectors;

public class CashierMapper {
	public CashierMapper() {
	}

	public static CashierResponse toResponse(Cashier cashier) {
		return CashierResponse.builder()
				.id(cashier.getId())
				.name(cashier.getName())
				.email(cashier.getEmail())
				.phoneNumber(cashier.getPhoneNumber())
				.status(cashier.getStatus())
				.loginName(cashier.getLoginName())
				.password(cashier.getPassword())
				.birthDay(cashier.getBirthDay())
				.gender(cashier.isGender())
				.salary(cashier.getSalary())
				.hireDate(cashier.getHireDate())
				.build();
	}

	public static List<CashierResponse> toResponseList(List<Cashier> cashiers) {
		return cashiers.stream()
			.map(CashierMapper::toResponse)
			.collect(Collectors.toList());
	}
}
