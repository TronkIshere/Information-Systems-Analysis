package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.courseOffering.UpdateCourseOfferingRequest;
import com.tronk.analysis.dto.request.courseOffering.UploadCourseOfferingRequest;
import com.tronk.analysis.dto.response.courseOffering.CourseOfferingResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.CourseOffering;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.courseOffering.CourseOfferingMapper;
import com.tronk.analysis.repository.CourseOfferingRepository;
import com.tronk.analysis.repository.CourseRepository;
import com.tronk.analysis.service.CourseOfferingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseOfferingServiceImpl implements CourseOfferingService {
	CourseOfferingRepository courseOfferingRepository;
	CourseRepository courseRepository;

	@Override
	public List<CourseOfferingResponse> getOpenCourseOfferings() {
		LocalDate currentDate = LocalDate.now();
		List<CourseOffering> openCourses = courseOfferingRepository.findOpenCourseOfferings(currentDate);
		return CourseOfferingMapper.toResponseList(openCourses);
	}

	@Override
	public CourseOfferingResponse createCourseOffering(UploadCourseOfferingRequest request) {
		CourseOffering receiptItem = new CourseOffering();
		receiptItem.setStartDate(request.getStartDate());
		receiptItem.setEndDate(request.getEndDate());
		receiptItem.setStudentCourses(request.getStudentCourses());

		Course course = courseRepository.findById(request.getCourseId())
						.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));
		receiptItem.setCourse(course);

		courseOfferingRepository.save(receiptItem);
		return CourseOfferingMapper.toResponse(receiptItem);
	}

	@Override
	public List<CourseOfferingResponse> getAllCourseOfferings() {
		return CourseOfferingMapper.toResponseList(courseOfferingRepository.findAll());
	}

	@Override
	public CourseOfferingResponse getCourseOfferingById(UUID id) {
		return CourseOfferingMapper.toResponse(
			courseOfferingRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found")));
	}

	@Override
	public CourseOfferingResponse updateCourseOffering(UpdateCourseOfferingRequest request) {
		CourseOffering receiptItem = courseOfferingRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found"));
		receiptItem.setStartDate(request.getStartDate());
		receiptItem.setEndDate(request.getEndDate());
		receiptItem.setStudentCourses(request.getStudentCourses());

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));
		receiptItem.setCourse(course);

		courseOfferingRepository.save(receiptItem);
		return CourseOfferingMapper.toResponse(receiptItem);
	}

	@Override
	public void deleteCourseOfferingById(UUID id) {
		CourseOffering entity = courseOfferingRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found"));
		courseOfferingRepository.delete(entity);
	}

	@Override
	public String softDeleteCourseOffering(UUID id) {
		CourseOffering entity = courseOfferingRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("ReceiptItem not found"));
		entity.setDeletedAt(LocalDateTime.now());
		courseOfferingRepository.save(entity);
		return "ReceiptItem with ID " + id + " has been soft deleted";
	}
}
