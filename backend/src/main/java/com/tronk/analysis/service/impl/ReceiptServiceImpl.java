package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.receipt.UploadReceiptRequest;
import com.tronk.analysis.dto.request.receipt.UpdateReceiptRequest;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import com.tronk.analysis.entity.Receipt;
import com.tronk.analysis.repository.ReceiptRepository;
import com.tronk.analysis.service.ReceiptService;
import com.tronk.analysis.mapper.Receipt.ReceiptMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptServiceImpl implements ReceiptService {
	ReceiptRepository receiptRepository;
	@Override
	public ReceiptResponse createReceipt(UploadReceiptRequest request) {
		Receipt receipt = new Receipt();
		receipt.setTotalAmount(request.getTotalAmount());
		receipt.setStatus(request.isStatus());
		receipt.setDescription(request.getDescription());
		Receipt savedEntity = receiptRepository.save(receipt);
		return ReceiptMapper.toResponse(savedEntity);
	}

	@Override
	public List<ReceiptResponse> getAllReceipts() {
		return ReceiptMapper.toResponseList(receiptRepository.findAll());
	}

	@Override
	public ReceiptResponse getReceiptById(UUID id) {
		return ReceiptMapper.toResponse(
			receiptRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Receipt not found")));
	}

	@Override
	public ReceiptResponse updateReceipt(UpdateReceiptRequest request) {
		Receipt entity = receiptRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
		Receipt receipt = new Receipt();
		receipt.setTotalAmount(request.getTotalAmount());
		receipt.setStatus(request.isStatus());
		receipt.setDescription(request.getDescription());
		return ReceiptMapper.toResponse(receiptRepository.save(entity));
	}

	@Override
	public void deleteReceiptById(UUID id) {
		Receipt entity = receiptRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
		receiptRepository.delete(entity);
	}

	@Override
	public String softDeleteReceipt(UUID id) {
		Receipt entity = receiptRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
		entity.setDeletedAt(LocalDateTime.now());
		receiptRepository.save(entity);
		return "Receipt with ID " + id + " has been soft deleted";
	}
}
