package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.courseOffering.UpdateCourseOfferingRequest;
import com.tronk.analysis.dto.request.courseOffering.UploadCourseOfferingRequest;
import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;

import java.util.List;
import java.util.UUID;

public interface CourseOfferingService {

	CourseOfferingResponse createReceiptItem(UploadCourseOfferingRequest request);

	CourseOfferingResponse getReceiptItemById(UUID id);

	List<CourseOfferingResponse> getAllReceiptItems();

	CourseOfferingResponse updateReceiptItem(UpdateCourseOfferingRequest request);

	void deleteReceiptItemById(UUID id);

	String softDeleteReceiptItem(UUID id);

}