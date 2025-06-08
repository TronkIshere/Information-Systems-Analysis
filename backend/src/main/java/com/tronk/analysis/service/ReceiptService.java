package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.lecturer.AssignReceiptToSemesterRequest;
import com.tronk.analysis.dto.request.receipt.AssignReceiptToCourseRequest;
import com.tronk.analysis.dto.request.receipt.RemoveReceiptFromCourseRequest;
import com.tronk.analysis.dto.request.receipt.UpdateReceiptRequest;
import com.tronk.analysis.dto.request.receipt.UploadReceiptRequest;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;

import java.util.List;
import java.util.UUID;

public interface ReceiptService {

	ReceiptResponse createReceipt(UploadReceiptRequest request);

	ReceiptResponse getReceiptById(UUID id);

	ReceiptResponse updateReceipt(UpdateReceiptRequest request);
	void deleteReceiptById(UUID id);

	String softDeleteReceipt(UUID id);

    void removeReceiptFromCourse(RemoveReceiptFromCourseRequest request);

	void assignReceiptToCourse(AssignReceiptToCourseRequest request);

	void assignReceiptToSemester(AssignReceiptToSemesterRequest request);

	ReceiptResponse markAsPaid(UUID id);

	List<ReceiptResponse> getAllReceipts();
}