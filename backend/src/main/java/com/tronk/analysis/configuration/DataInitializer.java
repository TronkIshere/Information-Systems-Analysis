package com.tronk.analysis.configuration;

import com.tronk.analysis.entity.*;
import com.tronk.analysis.repository.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Configuration
@Slf4j(topic = "INIT-APPLICATION")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true)
public class DataInitializer {
    PasswordEncoder passwordEncoder;
    CourseRepository courseRepository;
    DepartmentRepository departmentRepository;
    LecturerRepository lecturerRepository;
    SemesterRepository semesterRepository;
    StudentRepository studentRepository;
    ReceiptRepository receiptRepository;

    @Bean
    public ApplicationRunner initData() {
        log.info("INIT DATA");
        return args -> {
            if (ifDatabaseEmpty()) {
                createDepartments();
                createCourses();
                createLecturers();
                createStudents();
                createSemesters();
                createReceipts();
            }
            log.info("Initial users inserted!");
        };
    }

    private boolean ifDatabaseEmpty() {
        return courseRepository.count() == 0
                && departmentRepository.count() == 0
                && lecturerRepository.count() == 0
                && semesterRepository.count() == 0
                && studentRepository.count() == 0;
    }

    private void createDepartments() {
        List<Department> departments = List.of(
                createDepartment("Khoa Công nghệ Thông tin"),
                createDepartment("Khoa Điện - Điện tử"),
                createDepartment("Khoa Cơ khí"),
                createDepartment("Khoa Xây dựng"),
                createDepartment("Khoa Kinh tế")
        );
        departmentRepository.saveAll(departments);
        log.info("Initial departments inserted!");
    }

    private void createCourses() {
        Department itDepartment = departmentRepository.findAll().get(0);

        List<Course> courses = List.of(
                createCourse("Lập trình Java", 3, BigDecimal.valueOf(500000), true),
                createCourse("Cơ sở dữ liệu", 3, BigDecimal.valueOf(450000), true),
                createCourse("Mạng máy tính", 2, BigDecimal.valueOf(400000), true),
                createCourse("Trí tuệ nhân tạo", 4, BigDecimal.valueOf(600000), true),
                createCourse("Hệ điều hành", 3, BigDecimal.valueOf(450000), true),
                createCourse("Phân tích thiết kế hệ thống", 3, BigDecimal.valueOf(500000), false),
                createCourse("Lập trình Web", 3, BigDecimal.valueOf(500000), false),
                createCourse("An toàn thông tin", 2, BigDecimal.valueOf(400000), false),
                createCourse("Đồ án chuyên ngành", 4, BigDecimal.valueOf(700000), false),
                createCourse("Toán rời rạc", 3, BigDecimal.valueOf(450000), false)
        );

        courses.forEach(course -> {
            course.getDepartments().add(itDepartment);
            itDepartment.getCourses().add(course);
        });

        courseRepository.saveAll(courses);
        departmentRepository.save(itDepartment);
        log.info("Initial courses inserted!");
    }

    private void createLecturers() {
        Department itDepartment = departmentRepository.findAll().get(0);

        Lecturer lecturer = createLecturer(
                "Nguyễn Văn Giảng",
                "giang.nguyen@university.edu",
                "0987654321",
                "ACTIVE",
                "lecturer01",
                passwordEncoder.encode("lecturer@123"),
                LocalDate.of(1980, 5, 15),
                true,
                "ROLE_LECTURER",
                "GV001",
                "Tiến sĩ",
                BigDecimal.valueOf(20000000),
                LocalDate.of(2015, 8, 22),
                "Trí tuệ nhân tạo"
        );

        lecturer.setDepartment(itDepartment);
        List<Course> allCourses = courseRepository.findAll();
        lecturer.setCourses(new HashSet<>(allCourses));

        lecturerRepository.save(lecturer);
        log.info("Initial lecturer inserted!");
    }

    private void createStudents() {
        Student student = createStudent(
                "Nguyễn Hữu Trọng",
                "student01@university.edu",
                "0123456789",
                "ACTIVE",
                "student01",
                passwordEncoder.encode("student@123"),
                LocalDate.of(2000, 3, 20),
                false,
                "ROLE_STUDENT",
                UUID.randomUUID(),
                "Công nghệ Thông tin",
                BigDecimal.valueOf(3.5)
        );

        studentRepository.save(student);
        log.info("Initial student inserted!");
    }

    private void createSemesters() {
        Semester semester = createSemester(
                "Học kỳ 1 - 2023",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2024, 1, 15)
        );
        semesterRepository.save(semester);
        log.info("Initial semester inserted!");
    }

    private void createReceipts() {
        Student student = studentRepository.findAll().get(0);

        Semester semester = semesterRepository.findAll().get(0);

        List<Course> courses = courseRepository.findAll();

        BigDecimal totalAmount = courses.stream()
                .map(c -> c.getBaseFeeCredit().multiply(BigDecimal.valueOf(c.getCredit())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Receipt receipt = new Receipt();
        receipt.setTotalAmount(totalAmount);
        receipt.setStatus(true);
        receipt.setDescription("Học phí học kỳ 1 - 2023");
        receipt.setStudent(student);
        receipt.setSemester(semester);
        receipt.setCourses(new HashSet<>(courses));

        receiptRepository.save(receipt);
        log.info("Initial receipt inserted!");
    }

    private Course createCourse(String name, int credit, BigDecimal baseFeeCredit, boolean subjectType) {
        Course course = new Course();
        course.setName(name);
        course.setCredit(credit);
        course.setBaseFeeCredit(baseFeeCredit);
        course.setSubjectType(subjectType);
        return course;
    }

    private Department createDepartment(String name){
        Department department = new Department();
        department.setName(name);
        return department;
    }

    private Lecturer createLecturer(String name, String email, String phoneNumber, String status,
                                    String loginName, String password, LocalDate birthDay, boolean gender,
                                    String roles, String lecturerCode, String academicRank,
                                    BigDecimal salary, LocalDate hireDate, String researchField){
        Lecturer lecturer = new Lecturer();
        lecturer.setName(name);
        lecturer.setEmail(email);
        lecturer.setPhoneNumber(phoneNumber);
        lecturer.setStatus(status);
        lecturer.setLoginName(loginName);
        lecturer.setPassword(password);
        lecturer.setBirthDay(birthDay);
        lecturer.setGender(gender);
        lecturer.setRoles(roles);
        lecturer.setLecturerCode(lecturerCode);
        lecturer.setAcademicRank(academicRank);
        lecturer.setSalary(salary);
        lecturer.setHireDate(hireDate);
        lecturer.setResearchField(researchField);
        return lecturer;
    }

    private Student createStudent(String name, String email, String phoneNumber, String status,
                                  String loginName, String password, LocalDate birthDay, boolean gender,
                                  String roles, UUID studentCode, String major, BigDecimal gpa) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setStatus(status);
        student.setLoginName(loginName);
        student.setPassword(password);
        student.setBirthDay(birthDay);
        student.setGender(gender);
        student.setRoles(roles);
        student.setStudentCode(studentCode);
        student.setMajor(major);
        student.setGpa(gpa);
        return student;
    }

    private Semester createSemester(String name, LocalDate startDate, LocalDate endDate) {
        Semester semester = new Semester();
        semester.setName(name);
        semester.setStartDate(startDate);
        semester.setEndDate(endDate);
        return semester;
    }

    private Receipt createReceipt(BigDecimal totalAmount, boolean status, String description) {
        Receipt receipt = new Receipt();
        receipt.setTotalAmount(totalAmount);
        receipt.setStatus(status);
        receipt.setDescription(description);
        return receipt;
    }
}


