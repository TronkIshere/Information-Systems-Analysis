package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.lecturer.AssignReceiptToSemesterRequest;
import com.tronk.analysis.dto.request.receipt.AssignReceiptToCourseRequest;
import com.tronk.analysis.dto.request.receipt.RemoveReceiptFromCourseRequest;
import com.tronk.analysis.dto.request.receipt.UploadReceiptRequest;
import com.tronk.analysis.dto.request.receipt.UpdateReceiptRequest;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import java.util.UUID;
import java.util.List;

public interface ReceiptService {

	ReceiptResponse createReceipt(UploadReceiptRequest request);

	ReceiptResponse getReceiptById(UUID id);

	List<ReceiptResponse> getAllReceipts();

	ReceiptResponse updateReceipt(UpdateReceiptRequest request);

	void deleteReceiptById(UUID id);

	String softDeleteReceipt(UUID id);

    void removeReceiptFromCourse(RemoveReceiptFromCourseRequest request);

	void assignReceiptToCourse(AssignReceiptToCourseRequest request);

	void assignReceiptToSemester(AssignReceiptToSemesterRequest request);
}