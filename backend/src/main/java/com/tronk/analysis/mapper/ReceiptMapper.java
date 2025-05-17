package com.tronk.analysis.mapper;

import com.tronk.analysis.entity.Receipt;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptMapper {
	public ReceiptMapper() {
	}

	public static ReceiptResponse toResponse(Receipt receipt) {
		return ReceiptResponse.builder()
			.build();
	}

	public static List<ReceiptResponse> toResponseList(List<Receipt> receipts) {
		return receipts.stream()
			.map(ReceiptMapper::toResponse)
			.collect(Collectors.toList());
	}
}
