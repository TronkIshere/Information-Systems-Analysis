package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.lecturer.AssignReceiptToSemesterRequest;
import com.tronk.analysis.dto.request.receipt.AssignReceiptToCourseRequest;
import com.tronk.analysis.dto.request.receipt.RemoveReceiptFromCourseRequest;
import com.tronk.analysis.dto.request.receipt.UpdateReceiptRequest;
import com.tronk.analysis.dto.request.receipt.UploadReceiptRequest;
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
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptServiceImpl implements ReceiptService {
	CourseOfferingRepository courseOfferingRepository;
	StudentCourseRepository studentCourseRepository;
	SemesterRepository semesterRepository;
	StudentRepository studentRepository;
	ReceiptRepository receiptRepository;
	CashierRepository cashierRepository;
	CourseRepository courseRepository;
	EmailService emailService;

	@Override
	public ReceiptResponse createReceipt(UploadReceiptRequest request) {
		Student student = findStudent(request.getStudentId());
		Set<CourseOffering> courseOfferings = findCourseOfferings(request.getCourseOfferingIds());

		Semester semester = request.getSemesterId() == null
				? findTopByOrderByStartDateDesc()
				: findSemester(request.getSemesterId());

		Cashier cashier = null;
		if (request.getCashierId() != null)
			cashier = findCashier(request.getCashierId());

		createStudentCourses(student, courseOfferings);

		Receipt receipt = createReceiptObject(request, courseOfferings, student, semester, cashier);
		receiptRepository.save(receipt);

		emailService.sendReceiptForStudent(receipt.getId());

		return ReceiptMapper.toResponse(receipt);
	}

	private Student findStudent(UUID id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));
	}

	private Semester findSemester(UUID id) {
		return semesterRepository.findById(id)
				.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));
	}

	private Semester findTopByOrderByStartDateDesc() {
		return semesterRepository.findTopByOrderByStartDateDesc()
				.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));
	}

	private Set<CourseOffering> findCourseOfferings(List<UUID> ids) {
		return new HashSet<>(courseOfferingRepository.findAllById(ids));
	}

	private Cashier findCashier(UUID id) {
		return cashierRepository.findById(id)
				.orElseThrow(() -> new ApplicationException(ErrorCode.SEMESTER_NOT_FOUND));
	}

	private void createStudentCourses(Student student, Set<CourseOffering> courseOfferings) {
		Set<StudentCourse> studentCourses = new HashSet<>();
		for (CourseOffering offering : courseOfferings) {
			StudentCourse studentCourse = new StudentCourse();
			studentCourse.setStudent(student);
			studentCourse.setCourseOffering(offering);
			studentCourse.setEnrollmentDate(LocalDate.now());
			studentCourse.setStatus(EnrollmentStatus.ENROLLED);
			studentCourse.setGrade(null);
			studentCourses.add(studentCourse);
		}

		studentCourseRepository.saveAll(studentCourses);
	}

	private BigDecimal calculateTotalAmount(BigDecimal totalAmount, Set<CourseOffering> courseOfferings) {
		BigDecimal result = totalAmount;
		if (result == null || result.compareTo(BigDecimal.ZERO) == 0) {
			result = courseOfferings.stream()
					.map(courseOffering -> {
						Course course = courseOffering.getCourse();
						return course.getBaseFeeCredit().multiply(BigDecimal.valueOf(course.getCredit()));
					})
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return result;
	}

	private Receipt createReceiptObject(UploadReceiptRequest request, Set<CourseOffering> courseOfferings,
										Student student, Semester semester, Cashier cashier) {
		Receipt receipt = new Receipt();
		receipt.setStatus(request.isStatus());
		receipt.setDescription(request.getDescription());
		receipt.setStudentName(request.getStudentName());
		receipt.setStudentCode(request.getStudentCode());
		receipt.setStudentClass(request.getStudentClass());
		receipt.setTotalAmount(calculateTotalAmount(receipt.getTotalAmount(), courseOfferings));
		receipt.setPaymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now());

		receipt.setStudent(student);
		receipt.setSemester(semester);
		receipt.setCourseOfferings(courseOfferings);
		receipt.setCashier(cashier);
		return receipt;
	}

	@Override
	public ReceiptResponse getReceiptById(UUID id) {
		return ReceiptMapper.toResponse(
			receiptRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Receipt not found")));
	}

	@Override
	public ReceiptResponse updateReceipt(UpdateReceiptRequest request) {
		Receipt receipt = findReceipt(request.getId());
		Student student = findStudent(request.getStudentId());
		Semester semester = findSemester(request.getSemesterId());
		Set<CourseOffering> courseOfferings = findCourseOfferings(request.getCourseOfferingIds());

		Cashier cashier = Optional.ofNullable(request.getCashierId())
				.map(this::findCashier)
				.orElse(null);

		updateReceiptObject(request, receipt, student, semester, cashier, courseOfferings);
		return ReceiptMapper.toResponse(receiptRepository.save(receipt));
	}

	private Receipt findReceipt(UUID id) {
		return receiptRepository.findById(id)
				.orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));
	}

	private void updateReceiptObject(UpdateReceiptRequest request, Receipt receipt,
									 Student student, Semester semester,
									 Cashier cashier, Set<CourseOffering> courseOfferings) {
		receipt.setTotalAmount(request.getTotalAmount());
		receipt.setStatus(request.isStatus());
		receipt.setDescription(request.getDescription());
		receipt.setPaymentDate(request.getPaymentDate());
		receipt.setStudentName(request.getStudentName());
		receipt.setStudentCode(request.getStudentCode());
		receipt.setStudentClass(request.getStudentClass());

		receipt.setStudent(student);
		receipt.setSemester(semester);
		receipt.setCourseOfferings(courseOfferings);
		receipt.setCashier(cashier);
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

//		if (receipt.getCourses().contains(course)) {
//			throw new ApplicationException(ErrorCode.RECEIPT_ALREADY_HAS_COURSE);
//		}
//
//		receipt.getCourses().add(course);
//		course.getReceipts().add(receipt);

		receiptRepository.save(receipt);
	}

	@Override
	public void removeReceiptFromCourse(RemoveReceiptFromCourseRequest request) {
		Receipt receipt = receiptRepository.findById(request.getReceiptId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));

		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));

//		if (!receipt.getCourses().contains(course)) {
//			throw new ApplicationException(ErrorCode.RECEIPT_DOES_NOT_HAVE_COURSE);
//		}
//
//		receipt.getCourses().remove(course);
//		course.getReceipts().remove(receipt);

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
	public ReceiptResponse markAsPaid(UUID id) {
		Receipt receipt = receiptRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
		receipt.setStatus(true);
		receiptRepository.save(receipt);
		return ReceiptMapper.toResponse(receipt);
	}

	@Override
	public List<ReceiptResponse> getAllReceipts() {
		return ReceiptMapper.toResponseList(receiptRepository.findAll());
	}
}
