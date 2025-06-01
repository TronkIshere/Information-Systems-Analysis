package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.lecturer.AssignReceiptToSemesterRequest;
import com.tronk.analysis.dto.request.receipt.*;
import com.tronk.analysis.dto.response.receipt.ReceiptFullInfoResponse;
import com.tronk.analysis.dto.response.receipt.ReceiptResponse;
import com.tronk.analysis.entity.*;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.Receipt.ReceiptMapper;
import com.tronk.analysis.repository.*;
import com.tronk.analysis.service.EmailService;
import com.tronk.analysis.service.ReceiptService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptServiceImpl implements ReceiptService {
	EmailService emailService;
	CourseRepository courseRepository;
	StudentRepository studentRepository;
	ReceiptRepository receiptRepository;
	CashierRepository cashierRepository;
	SemesterRepository semesterRepository;

	@Override
	public ReceiptResponse createReceipt(UploadReceiptRequest request) {
		Student student = studentRepository.findById(request.getStudentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));

		Semester semester = semesterRepository.findById(request.getSemesterId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));

		Cashier cashier = null;
		if (request.getCashierId() != null) {
			cashier = cashierRepository.findById(request.getCashierId())
					.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));
		}

		Set<Course> courses = new HashSet<>(courseRepository.findAllById(request.getCourseIds()));

		BigDecimal totalAmount = request.getTotalAmount();
		if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) == 0) {
			totalAmount = courses.stream()
					.map(course -> course.getBaseFeeCredit().multiply(BigDecimal.valueOf(course.getCredit())))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}

		Receipt receipt = new Receipt();
		receipt.setTotalAmount(totalAmount);
		receipt.setStatus(request.isStatus());
		receipt.setDescription(request.getDescription());
		receipt.setPaymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now());
		receipt.setStudentName(request.getStudentName());
		receipt.setStudentCode(request.getStudentCode());
		receipt.setStudentClass(request.getStudentClass());

		receipt.setStudent(student);
		receipt.setSemester(semester);
		receipt.setCourses(courses);
		receipt.setCashier(cashier);
		receiptRepository.save(receipt);

		emailService.sendReceiptForStudent(receipt.getId());

		return ReceiptMapper.toResponse(receipt);
	}


	@Override
	public List<ReceiptFullInfoResponse> getAllReceipts() {
		return ReceiptMapper.toFullInfoList(receiptRepository.findAll());
	}

	@Override
	public ReceiptResponse getReceiptById(UUID id) {
		return ReceiptMapper.toResponse(
			receiptRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Receipt not found")));
	}

	@Override
	public ReceiptResponse updateReceipt(UpdateReceiptRequest request) {
		Receipt receipt = receiptRepository.findById(request.getId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));

		Student student = studentRepository.findById(request.getStudentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));

		Semester semester = semesterRepository.findById(request.getSemesterId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));

		Cashier cashier = null;
		if (request.getCashierId() != null) {
			cashier = cashierRepository.findById(request.getCashierId())
					.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));
		}

		Set<Course> courses = new HashSet<>(courseRepository.findAllById(request.getCourseIds()));

		receipt.setTotalAmount(request.getTotalAmount());

		if(!receipt.isStatus() && request.isStatus())
			emailService.sendRPaymentReceiptInfoForStudent(receipt.getId());
		receipt.setStatus(request.isStatus());

		receipt.setDescription(request.getDescription());
		receipt.setPaymentDate(request.getPaymentDate());
		receipt.setStudentName(request.getStudentName());
		receipt.setStudentCode(request.getStudentCode());
		receipt.setStudentClass(request.getStudentClass());

		receipt.setStudent(student);
		receipt.setSemester(semester);
		receipt.setCourses(courses);
		receipt.setCashier(cashier);

		return ReceiptMapper.toResponse(receiptRepository.save(receipt));
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

	@Override
	public ReceiptResponse createReceiptWithFullInfo(UploadReceiptWithFullInfoRequest request) {
		Student student = studentRepository.findById(request.getStudentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_EXISTED));
		Semester semester = semesterRepository.findById(request.getSemesterId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));
		List<Course> courses = courseRepository.findAllById(request.getCourseId());

		if (courses.isEmpty()) {
			throw new ApplicationException(ErrorCode.COURSE_NOT_FOUND);
		}

		BigDecimal totalAmount = courses.stream()
				.map(course -> course.getBaseFeeCredit().multiply(BigDecimal.valueOf(course.getCredit())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Receipt receipt = new Receipt();
		receipt.setStatus(request.isStatus());
		receipt.setDescription(request.getDescription());
		receipt.setPaymentDate(receipt.getPaymentDate());
		receipt.setStudentName(request.getStudentName());
		receipt.setStudentCode(request.getStudentCode());
		receipt.setStudentClass(request.getStudentClass());
		receipt.setStudent(student);
		receipt.setSemester(semester);
		receipt.setCourses(new HashSet<>(courses));
		receipt.setTotalAmount(totalAmount);

		receiptRepository.save(receipt);
		return ReceiptMapper.toResponse(receipt);
	}

	@Override
	public ReceiptResponse markAsPaid(UUID id) {
		Receipt receipt = receiptRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
		receipt.setStatus(true);
		receiptRepository.save(receipt);
		return ReceiptMapper.toResponse(receipt);
	}
}
