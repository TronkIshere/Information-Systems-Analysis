package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.lecturer.AssignReceiptToSemesterRequest;
import com.tronk.analysis.dto.request.receipt.*;
import com.tronk.analysis.dto.response.receipt.ReceiptFullInfoResponse;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import java.util.UUID;
import java.util.List;

public interface ReceiptService {

	ReceiptResponse createReceipt(UploadReceiptRequest request);

	ReceiptResponse getReceiptById(UUID id);

	List<ReceiptFullInfoResponse> getAllReceipts();

	ReceiptResponse updateReceipt(UpdateReceiptRequest request);

	void deleteReceiptById(UUID id);

	String softDeleteReceipt(UUID id);

    void removeReceiptFromCourse(RemoveReceiptFromCourseRequest request);

	void assignReceiptToCourse(AssignReceiptToCourseRequest request);

	void assignReceiptToSemester(AssignReceiptToSemesterRequest request);

	ReceiptResponse createReceiptWithFullInfo(UploadReceiptWithFullInfoRequest request);

	ReceiptResponse markAsPaid(UUID id);
}