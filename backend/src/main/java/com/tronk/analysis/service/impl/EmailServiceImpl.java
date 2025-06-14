package com.tronk.analysis.service.impl;

import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.CourseOffering;
import com.tronk.analysis.entity.Receipt;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.repository.ReceiptRepository;
import com.tronk.analysis.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {
    JavaMailSender mailSender;
    ReceiptRepository receiptRepository;

    @Override
    public void sendReceiptForStudent(UUID receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));

        Student student = receipt.getStudent();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(student.getEmail());
        message.setSubject("Xác nhận đăng ký môn học - " + receipt.getStudentName());
        message.setText(buildReceiptText(receipt, student, false));
        mailSender.send(message);
    }

    @Override
    public void sendRPaymentReceiptInfoForStudent(UUID receiptId) {
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.RECEIPT_NOT_FOUND));

        Student student = receipt.getStudent();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(student.getEmail());
        message.setSubject("Xác nhận đã thanh toán - " + receipt.getStudentName());
        message.setText(buildReceiptText(receipt, student, true));
        mailSender.send(message);
    }

    private String buildReceiptText(Receipt receipt, Student student, boolean isPayment) {
        StringBuilder sb = new StringBuilder();

        // Header with invoice-like formatting
        sb.append("\n=========================================================\n");
        sb.append("                HÓA ĐƠN THANH TOÁN HỌC PHÍ\n");
        sb.append("=========================================================\n\n");

        // Student information section
        sb.append("THÔNG TIN HỌC SINH:\n");
        sb.append("+--------------------------------+--------------------------------+\n");
        sb.append(String.format("| %-30s | %-30s |\n", "Họ và tên", receipt.getStudentName()));
        sb.append(String.format("| %-30s | %-30s |\n", "Mã học sinh", receipt.getStudentCode()));
        sb.append(String.format("| %-30s | %-30s |\n", "Lớp", receipt.getStudentClass()));
        sb.append(String.format("| %-30s | %-30s |\n", "Ngành học", student.getMajor()));
        sb.append(String.format("| %-30s | %-30s |\n", "Email", student.getEmail()));
        sb.append(String.format("| %-30s | %-30s |\n", "Số điện thoại", student.getPhoneNumber()));
        sb.append("+--------------------------------+--------------------------------+\n\n");

        // Payment information section
        if (isPayment && receipt.getPaymentDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            sb.append("THÔNG TIN THANH TOÁN:\n");
            sb.append("+--------------------------------+--------------------------------+\n");
            sb.append(String.format("| %-30s | %-30s |\n", "Ngày thanh toán", receipt.getPaymentDate().format(formatter)));
            sb.append(String.format("| %-30s | %-30s |\n", "Nhân viên thu ngân", receipt.getCashier().getName()));
            sb.append(String.format("| %-30s | %-30s |\n", "Học kỳ", receipt.getSemester().getName()));
            sb.append("+--------------------------------+--------------------------------+\n\n");
        }

        // Course list section
        sb.append("DANH SÁCH MÔN HỌC:\n");
        sb.append("+--------------------------------+------------+-----------------+\n");
        sb.append("| Môn học                       | Số tín chỉ | Thành tiền (VND)|\n");
        sb.append("+--------------------------------+------------+-----------------+\n");

        // Course items with detailed information
        for (CourseOffering courseOffering : receipt.getCourseOfferings()) {
            String courseName = courseOffering.getCourse().getName();
            if (courseName.length() > 30) {
                courseName = courseName.substring(0, 27) + "...";
            }
            BigDecimal courseTotal = courseOffering.getCourse().getBaseFeeCredit()
                    .multiply(BigDecimal.valueOf(courseOffering.getCourse().getCredit()));

            sb.append(String.format("| %-30s | %10d | %15s |\n",
                    courseName,
                    courseOffering.getCourse().getCredit(),
                    formatCurrency(courseTotal)));
        }
        sb.append("+--------------------------------+------------+-----------------+\n");

        // Total amount section
        sb.append(String.format("| %-30s | %10s | %15s |\n",
                "TỔNG CỘNG:",
                "",
                formatCurrency(receipt.getTotalAmount())));
        sb.append("+--------------------------------+------------+-----------------+\n\n");

        // Footer with additional information
        sb.append("GHI CHÚ:\n");
        sb.append("- ").append(receipt.getDescription()).append("\n\n");
        sb.append("=========================================================\n");
        sb.append("Cảm ơn quý khách đã sử dụng dịch vụ!\n");
        sb.append("Mọi thắc mắc vui lòng liên hệ: 03529256696\n");
        sb.append("=========================================================\n");

        return sb.toString();
    }

    private String formatCurrency(BigDecimal amount) {
        if (amount == null) return "0";
        BigDecimal rounded = amount.setScale(0, RoundingMode.HALF_UP);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        formatter.setGroupingSize(3);
        return formatter.format(rounded);
    }
}