package com.tronk.analysis.mapper.Receipt;

import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import com.tronk.analysis.entity.CourseOffering;
import com.tronk.analysis.entity.Receipt;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptMapper {

	public static ReceiptResponse toResponse(Receipt receipt) {
		return ReceiptResponse.builder()
				.id(receipt.getId())
				.totalAmount(receipt.getTotalAmount())
				.status(receipt.isStatus())
				.description(receipt.getDescription())
				.paymentDate(receipt.getPaymentDate())
				// student
				.studentId(receipt.getStudent().getId())
				.studentName(receipt.getStudentName())
				.studentCode(receipt.getStudentCode())
				.studentClass(receipt.getStudentClass())
				// semester
				.semesterId(receipt.getSemester().getId())
				.semesterName(receipt.getSemester().getName())
				// courseOfferings
				.courseOfferingIds(
						receipt.getCourseOfferings()
								.stream()
								.map(CourseOffering::getId)
								.collect(Collectors.toList())
				)
				// cashier
				.cashierId(receipt.getCashier() != null ? receipt.getCashier().getId() : null)
				.build();
	}

	public static List<ReceiptResponse> toResponseList(List<Receipt> receipts) {
		return receipts.stream()
				.map(ReceiptMapper::toResponse)
				.collect(Collectors.toList());
	}
}
