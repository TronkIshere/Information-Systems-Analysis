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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Configuration
@Slf4j(topic = "INIT-APPLICATION")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true)
public class DataInitializer {
    RoleRepository roleRepository;
    UserRepository userRepository;
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
                createRoles();
                createUsers();
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
        return roleRepository.count() == 0
                && userRepository.count() == 0
                && courseRepository.count() == 0
                && departmentRepository.count() == 0
                && lecturerRepository.count() == 0
                && semesterRepository.count() == 0
                && studentRepository.count() == 0;
    }

    private void createRoles() {
        List<Role> roles = List.of(
                createRole("ROLE_STUDENT"),
                createRole("ROLE_ADMIN"),
                createRole("ROLE_LECTURER")
        );
        roleRepository.saveAll(roles);
        log.info("Initial roles inserted!");
    }

    private void createUsers() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found!"));

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("ROLE_STUDENT not found!"));

        Role lecturerRole = roleRepository.findByName("ROLE_LECTURER")
                .orElseThrow(() -> new RuntimeException("ROLE_LECTURER not found!"));

        //create user admin
        LocalDate date1 = LocalDate.of(2004, 8, 22); // 22/8/2004
        User adminUser = createUser("Admin User", "adminUser@gmail.com", "0359256696", "ACTIVE", "admin", passwordEncoder.encode("1243"), date1, true);
        adminUser.setRoles(Collections.singleton(adminRole));

        //create user student
        LocalDate date2 = LocalDate.of(2004, 8, 22);
        User studentUser = createUser("Nguyễn Hữu Trọng", "nguyenhuutrong11133@gmail.com", "0359256696", "ACTIVE", "trong123", passwordEncoder.encode("1243"), date2, true);
        studentUser.setRoles(Collections.singleton(studentRole));

        //create user lecturerRole
        LocalDate date3 = LocalDate.of(2004, 8, 22);
        User lecturerUser = createUser("Giảng viên", "lecturer@gmail.com", "0359256696", "ACTIVE", "lecturer", passwordEncoder.encode("1243"), date3, true);
        lecturerUser.setRoles(Collections.singleton(lecturerRole));

        userRepository.saveAll(List.of(adminUser, studentUser, lecturerUser));
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
                createCourse("Lập trình Java", 3, BigDecimal.valueOf(500000)),
                createCourse("Cơ sở dữ liệu", 3, BigDecimal.valueOf(450000)),
                createCourse("Mạng máy tính", 2, BigDecimal.valueOf(400000)),
                createCourse("Trí tuệ nhân tạo", 4, BigDecimal.valueOf(600000)),
                createCourse("Hệ điều hành", 3, BigDecimal.valueOf(450000)),
                createCourse("Phân tích thiết kế hệ thống", 3, BigDecimal.valueOf(500000)),
                createCourse("Lập trình Web", 3, BigDecimal.valueOf(500000)),
                createCourse("An toàn thông tin", 2, BigDecimal.valueOf(400000)),
                createCourse("Đồ án chuyên ngành", 4, BigDecimal.valueOf(700000)),
                createCourse("Toán rời rạc", 3, BigDecimal.valueOf(450000))
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
        User lecturerUser = userRepository.findByLoginName("lecturer")
                .orElseThrow(() -> new RuntimeException("Lecturer user not found!"));
        Department itDepartment = departmentRepository.findAll().get(0);

        LocalDate hireDate = LocalDate.of(2004, 8, 22);
        Lecturer lecturer = createLecturer("GV001", "Tiến sĩ", BigDecimal.valueOf(20000000), hireDate, "Trí tuệ nhân tạo");
        lecturer.setApp_user(lecturerUser);
        lecturer.setDepartment(itDepartment);

        List<Course> allCourses = courseRepository.findAllWithLecturers();
        lecturer.setCourses(allCourses);

        lecturerRepository.save(lecturer);
        courseRepository.saveAll(allCourses);
        log.info("Initial lecturer inserted!");
    }

    private void createStudents() {
        User studentUser = userRepository.findByLoginName("trong123")
                .orElseThrow(() -> new RuntimeException("Student user not found!"));

        Student student = createStudent(UUID.randomUUID(), "Công nghệ Thông tin", BigDecimal.valueOf(3.5));
        student.setApp_user(studentUser);

        studentRepository.save(student);
        log.info("Initial student inserted!");
    }

    private void createSemesters() {
        LocalDate startDate = LocalDate.of(2013, 1, 1);
        LocalDate endDate = LocalDate.of(2013, 1, 1);

        Semester semester = createSemester("Học kỳ 1 - 2023", startDate, endDate);
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

    private Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    private User createUser(String name, String email, String phoneNumber, String status, String loginName, String password, LocalDate birthDay, boolean gender) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setStatus(status);
        user.setLoginName(loginName);
        user.setPassword(password);
        user.setBirthDay(birthDay);
        user.setGender(gender);
        return user;
    }

    private Course createCourse(String name, int credit, BigDecimal baseFeeCredit) {
        Course course = new Course();
        course.setName(name);
        course.setCredit(credit);
        course.setBaseFeeCredit(baseFeeCredit);
        return course;
    }

    private Department createDepartment(String name){
        Department department = new Department();
        department.setName(name);
        return department;
    }

    private Lecturer createLecturer(String lecturerCode, String academicRank, BigDecimal salary, LocalDate hireDate, String researchField){
        Lecturer lecturer = new Lecturer();
        lecturer.setLecturerCode(lecturerCode);
        lecturer.setAcademicRank(academicRank);
        lecturer.setSalary(salary);
        lecturer.setHireDate(hireDate);
        lecturer.setResearchField(researchField);
        return lecturer;
    }

    private Semester createSemester(String name, LocalDate startDate, LocalDate endDate) {
        Semester semester = new Semester();
        semester.setName(name);
        semester.setStartDate(startDate);
        semester.setEndDate(endDate);
        return semester;
    }

    private Student createStudent(UUID studentCode, String major, BigDecimal gpa) {
        Student student = new Student();
        student.setStudentCode(studentCode);
        student.setMajor(major);
        student.setGpa(gpa);
        return student;
    }

    private Receipt createReceipt(BigDecimal totalAmount, boolean status, String description) {
        Receipt receipt = new Receipt();
        receipt.setTotalAmount(totalAmount);
        receipt.setStatus(status);
        receipt.setDescription(description);
        return receipt;
    }
}


