package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.courseOffering.UpdateCourseOfferingRequest;
import com.tronk.analysis.dto.request.courseOffering.UploadCourseOfferingRequest;
import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;

import java.util.List;
import java.util.UUID;

public interface CourseOfferingService {

	CourseOfferingResponse createCourseOffering(UploadCourseOfferingRequest request);

	CourseOfferingResponse getCourseOfferingById(UUID id);

	List<CourseOfferingResponse> getAllCourseOfferings();

	CourseOfferingResponse updateCourseOffering(UpdateCourseOfferingRequest request);

	void deleteCourseOfferingById(UUID id);

	String softDeleteCourseOffering(UUID id);

	List<CourseOfferingResponse> getOpenCourseOfferings();
}