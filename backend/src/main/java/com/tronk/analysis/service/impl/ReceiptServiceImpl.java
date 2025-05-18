package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.lecturer.AssignReceiptToSemesterRequest;
import com.tronk.analysis.dto.request.receipt.AssignReceiptToCourseRequest;
import com.tronk.analysis.dto.request.receipt.RemoveReceiptFromCourseRequest;
import com.tronk.analysis.dto.request.receipt.UploadReceiptRequest;
import com.tronk.analysis.dto.request.receipt.UpdateReceiptRequest;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.Receipt;
import com.tronk.analysis.entity.Semester;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.repository.CourseRepository;
import com.tronk.analysis.repository.ReceiptRepository;
import com.tronk.analysis.repository.SemesterRepository;
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
	SemesterRepository semesterRepository;
	ReceiptRepository receiptRepository;
	CourseRepository courseRepository;
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

	@Override
	public void assignReceiptToCourse(AssignReceiptToCourseRequest request) {
		Receipt receipt = receiptRepository.findById(request.getReceiptId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));

		if (receipt.getCourses().contains(course)) {
			throw new ApplicationException(ErrorCode.RECEIPT_ALREADY_HAS_COURSE);
		}

		receipt.getCourses().add(course);
		course.getReceipts().add(receipt);

		receiptRepository.save(receipt);
	}

	@Override
	public void removeReceiptFromCourse(RemoveReceiptFromCourseRequest request) {
		Receipt receipt = receiptRepository.findById(request.getReceiptId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));

		if (!receipt.getCourses().contains(course)) {
			throw new ApplicationException(ErrorCode.RECEIPT_DOES_NOT_HAVE_COURSE);
		}

		receipt.getCourses().remove(course);
		course.getReceipts().remove(receipt);

		receiptRepository.save(receipt);
	}

	@Override
	public void assignReceiptToSemester(AssignReceiptToSemesterRequest request) {
		int updatedRows = receiptRepository.updateSemesterForReceipt(
				request.getReceiptId(),
				request.getSemesterId()
		);

		if (updatedRows == 0) {
			throw new ApplicationException(ErrorCode.RECEIPT_OR_SEMESTER_NOT_FOUND);
		}
	}
}
