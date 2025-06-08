package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.courseOffering.UpdateCourseOfferingRequest;
import com.tronk.analysis.dto.request.courseOffering.UploadCourseOfferingRequest;
import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.CourseOffering;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.courseOffering.CourseOfferingMapper;
import com.tronk.analysis.repository.CourseRepository;
import com.tronk.analysis.repository.CourseOfferingRepository;
import com.tronk.analysis.service.CourseOfferingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseOfferingServiceImpl implements CourseOfferingService {
	CourseOfferingRepository receiptItemRepository;
	CourseRepository courseRepository;

	@Override
	public CourseOfferingResponse createReceiptItem(UploadCourseOfferingRequest request) {
		CourseOffering receiptItem = new CourseOffering();
		receiptItem.setStartDate(request.getStartDate());
		receiptItem.setEndDate(request.getEndDate());
		receiptItem.setStudentCourses(request.getStudentCourses());

		Course course = courseRepository.findById(request.getCourseId())
						.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));
		receiptItem.setCourse(course);

		receiptItemRepository.save(receiptItem);
		return CourseOfferingMapper.toResponse(receiptItem);
	}

	@Override
	public List<CourseOfferingResponse> getAllReceiptItems() {
		return CourseOfferingMapper.toResponseList(receiptItemRepository.findAll());
	}

	@Override
	public CourseOfferingResponse getReceiptItemById(UUID id) {
		return CourseOfferingMapper.toResponse(
			receiptItemRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found")));
	}

	@Override
	public CourseOfferingResponse updateReceiptItem(UpdateCourseOfferingRequest request) {
		CourseOffering receiptItem = receiptItemRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found"));
		receiptItem.setStartDate(request.getStartDate());
		receiptItem.setEndDate(request.getEndDate());
		receiptItem.setStudentCourses(request.getStudentCourses());

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));
		receiptItem.setCourse(course);

		receiptItemRepository.save(receiptItem);
		return CourseOfferingMapper.toResponse(receiptItem);
	}

	@Override
	public void deleteReceiptItemById(UUID id) {
		CourseOffering entity = receiptItemRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found"));
		receiptItemRepository.delete(entity);
	}

	@Override
	public String softDeleteReceiptItem(UUID id) {
		CourseOffering entity = receiptItemRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found"));
		entity.setDeletedAt(LocalDateTime.now());
		receiptItemRepository.save(entity);
		return "ReceiptItem with ID " + id + " has been soft deleted";
	}
}
