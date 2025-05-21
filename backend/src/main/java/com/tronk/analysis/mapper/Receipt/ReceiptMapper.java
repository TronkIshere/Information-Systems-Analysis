package com.tronk.analysis.mapper.Receipt;

import com.tronk.analysis.entity.Receipt;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptMapper {
	public ReceiptMapper() {
	}

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
}
