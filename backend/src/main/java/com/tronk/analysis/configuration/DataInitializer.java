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
import java.util.*;

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
        List<Course> allCourses = courseRepository.findAll();

        List<Lecturer> lecturers = List.of(
                createLecturer("Nguyễn Văn Giảng", "giang.nguyen@university.edu", "0987654321", "ACTIVE", "lecturer01", passwordEncoder.encode("lecturer@123"), LocalDate.of(1980, 5, 15), true, "ROLE_LECTURER", "GV001", "Tiến sĩ", BigDecimal.valueOf(20000000), LocalDate.of(2015, 8, 22), "Trí tuệ nhân tạo"),
                createLecturer("Trần Thị Minh", "minh.tran@university.edu", "0912345678", "ACTIVE", "lecturer02", passwordEncoder.encode("lecturer@123"), LocalDate.of(1985, 3, 10), false, "ROLE_LECTURER", "GV002", "Thạc sĩ", BigDecimal.valueOf(18000000), LocalDate.of(2017, 2, 10), "Hệ thống thông tin"),
                createLecturer("Lê Văn Hùng", "hung.le@university.edu", "0934567890", "ACTIVE", "lecturer03", passwordEncoder.encode("lecturer@123"), LocalDate.of(1979, 9, 25), true, "ROLE_LECTURER", "GV003", "Phó Giáo sư", BigDecimal.valueOf(25000000), LocalDate.of(2012, 1, 5), "An toàn thông tin"),
                createLecturer("Phạm Thị Hoa", "hoa.pham@university.edu", "0978888888", "INACTIVE", "lecturer04", passwordEncoder.encode("lecturer@123"), LocalDate.of(1983, 6, 30), false, "ROLE_LECTURER", "GV004", "Tiến sĩ", BigDecimal.valueOf(21000000), LocalDate.of(2016, 5, 1), "Cơ sở dữ liệu"),
                createLecturer("Đặng Văn Bình", "binh.dang@university.edu", "0909009090", "ACTIVE", "lecturer05", passwordEncoder.encode("lecturer@123"), LocalDate.of(1981, 12, 12), true, "ROLE_LECTURER", "GV005", "Thạc sĩ", BigDecimal.valueOf(17000000), LocalDate.of(2018, 3, 15), "Phần mềm nhúng"),
                createLecturer("Hoàng Thị Yến", "yen.hoang@university.edu", "0966666666", "ACTIVE", "lecturer06", passwordEncoder.encode("lecturer@123"), LocalDate.of(1990, 11, 18), false, "ROLE_LECTURER", "GV006", "Tiến sĩ", BigDecimal.valueOf(23000000), LocalDate.of(2020, 9, 1), "Khoa học dữ liệu"),
                createLecturer("Vũ Văn Nam", "nam.vu@university.edu", "0911999999", "ACTIVE", "lecturer07", passwordEncoder.encode("lecturer@123"), LocalDate.of(1988, 4, 4), true, "ROLE_LECTURER", "GV007", "Phó Giáo sư", BigDecimal.valueOf(26000000), LocalDate.of(2014, 10, 10), "Kỹ thuật phần mềm"),
                createLecturer("Ngô Thị Thanh", "thanh.ngo@university.edu", "0922233344", "ACTIVE", "lecturer08", passwordEncoder.encode("lecturer@123"), LocalDate.of(1986, 8, 8), false, "ROLE_LECTURER", "GV008", "Thạc sĩ", BigDecimal.valueOf(19000000), LocalDate.of(2019, 7, 1), "Truyền thông đa phương tiện"),
                createLecturer("Bùi Văn Quang", "quang.bui@university.edu", "0988123456", "INACTIVE", "lecturer09", passwordEncoder.encode("lecturer@123"), LocalDate.of(1975, 1, 1), true, "ROLE_LECTURER", "GV009", "Giáo sư", BigDecimal.valueOf(30000000), LocalDate.of(2005, 6, 6), "Trí tuệ nhân tạo"),
                createLecturer("Lý Thị Mai", "mai.ly@university.edu", "0933444555", "ACTIVE", "lecturer10", passwordEncoder.encode("lecturer@123"), LocalDate.of(1992, 2, 2), false, "ROLE_LECTURER", "GV010", "Tiến sĩ", BigDecimal.valueOf(22000000), LocalDate.of(2021, 1, 1), "Khoa học máy tính")
        );

        for (Lecturer lecturer : lecturers) {
            lecturer.setDepartment(itDepartment);
            lecturer.setCourses(new HashSet<>(allCourses));
            lecturerRepository.save(lecturer);
        }

        log.info("10 lecturers inserted successfully!");
    }


    private void createStudents() {
        List<Student> students = List.of(
                createStudent("Nguyễn Hữu Trọng", "student01@university.edu", "0123456789", "ACTIVE", "student01", passwordEncoder.encode("student@123"), LocalDate.of(2000, 3, 20), false, "ROLE_STUDENT", "253A10101", "Công nghệ Thông tin", BigDecimal.valueOf(3.5)),
                createStudent("Lê Thị Mai", "student02@university.edu", "0987654321", "ACTIVE", "student02", passwordEncoder.encode("student@123"), LocalDate.of(2001, 5, 15), false, "ROLE_STUDENT", "253A10102", "Hệ thống thông tin", BigDecimal.valueOf(3.2)),
                createStudent("Trần Văn Nam", "student03@university.edu", "0912345678", "ACTIVE", "student03", passwordEncoder.encode("student@123"), LocalDate.of(1999, 8, 12), true, "ROLE_STUDENT", "253A10103", "An toàn thông tin", BigDecimal.valueOf(3.8)),
                createStudent("Phạm Thị Linh", "student04@university.edu", "0909876543", "INACTIVE", "student04", passwordEncoder.encode("student@123"), LocalDate.of(2000, 12, 1), false, "ROLE_STUDENT", "253A10104", "Công nghệ phần mềm", BigDecimal.valueOf(2.9)),
                createStudent("Vũ Văn Hải", "student05@university.edu", "0933666777", "ACTIVE", "student05", passwordEncoder.encode("student@123"), LocalDate.of(2002, 1, 10), true, "ROLE_STUDENT", "253A10105", "Trí tuệ nhân tạo", BigDecimal.valueOf(3.4)),
                createStudent("Đặng Thị Hoa", "student06@university.edu", "0977123456", "ACTIVE", "student06", passwordEncoder.encode("student@123"), LocalDate.of(2001, 9, 25), false, "ROLE_STUDENT", "253A10106", "Khoa học máy tính", BigDecimal.valueOf(3.6)),
                createStudent("Hoàng Văn Dũng", "student07@university.edu", "0922111222", "ACTIVE", "student07", passwordEncoder.encode("student@123"), LocalDate.of(1998, 6, 18), true, "ROLE_STUDENT", "253A10107", "Mạng máy tính", BigDecimal.valueOf(2.7)),
                createStudent("Ngô Thị Hạnh", "student08@university.edu", "0944556677", "INACTIVE", "student08", passwordEncoder.encode("student@123"), LocalDate.of(2000, 11, 22), false, "ROLE_STUDENT", "253A10108", "Phát triển phần mềm", BigDecimal.valueOf(3.1)),
                createStudent("Bùi Văn Khoa", "student09@university.edu", "0966332211", "ACTIVE", "student09", passwordEncoder.encode("student@123"), LocalDate.of(1999, 3, 3), true, "ROLE_STUDENT", "253A10109", "Công nghệ Web", BigDecimal.valueOf(3.3)),
                createStudent("Lý Thị Thanh", "student10@university.edu", "0911888999", "ACTIVE", "student10", passwordEncoder.encode("student@123"), LocalDate.of(2001, 4, 4), false, "ROLE_STUDENT", "253A10110", "Kỹ thuật phần mềm", BigDecimal.valueOf(3.9))
        );
        studentRepository.saveAll(students);

        // for admin role demo
        Student admin = createStudent("Nguyễn Hữu Trọng", "Admin01@university.edu", "0123456789", "ACTIVE", "admin01", passwordEncoder.encode("admin@123"), LocalDate.of(2000, 3, 20), false, "ROLE_ADMIN", "253A10100", "Công nghệ Thông tin", BigDecimal.valueOf(3.5));
        studentRepository.save(admin);
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
                                  String roles, String studentCode, String major, BigDecimal gpa) {
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


