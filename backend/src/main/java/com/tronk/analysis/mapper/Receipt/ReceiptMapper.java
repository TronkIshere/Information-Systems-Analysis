package com.tronk.analysis.mapper.Receipt;

import com.tronk.analysis.dto.response.receipt.ReceiptFullInfoResponse;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.Receipt;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptMapper {

	public static ReceiptResponse toResponse(Receipt receipt) {
		return ReceiptResponse.builder()
				.id(receipt.getId())
				.description(receipt.getDescription())
				.status(receipt.isStatus())
				.totalAmount(receipt.getTotalAmount())
				.paymentDate(receipt.getPaymentDate())
				.build();
	}

	public static List<ReceiptResponse> toResponseList(List<Receipt> receipts) {
		return receipts.stream()
				.map(ReceiptMapper::toResponse)
				.collect(Collectors.toList());
	}

	public static ReceiptFullInfoResponse toFullInfoResponse(Receipt receipt) {
		return ReceiptFullInfoResponse.builder()
				.id(receipt.getId())
				.totalAmount(receipt.getTotalAmount())
				.status(receipt.isStatus())
				.description(receipt.getDescription())
				.paymentDate(receipt.getPaymentDate())
				// student
				.studentId(receipt.getStudent().getId())
				.studentName(receipt.getStudent().getName())
				// semester
				.semesterId(receipt.getSemester().getId())
				.semesterName(receipt.getSemester().getName())
				// courses
				.courseIds(
						receipt.getCourses()
								.stream()
								.map(Course::getId)
								.collect(Collectors.toList())
				)
				// cashier
				.cashierId(receipt.getCashier().getId())
				.build();
	}

	public static List<ReceiptFullInfoResponse> toFullInfoList(List<Receipt> receipts) {
		return receipts.stream()
				.map(ReceiptMapper::toFullInfoResponse)
				.collect(Collectors.toList());
	}
}
