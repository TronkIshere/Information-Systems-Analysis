package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.course.UpdateCourseRequest;
import com.tronk.analysis.dto.request.course.UploadCourseRequest;
import com.tronk.analysis.dto.request.department.AssignCourseToDepartmentRequest;
import com.tronk.analysis.dto.request.department.RemoveCourseFromDepartmentRequest;
import com.tronk.analysis.dto.response.course.CourseResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.entity.Department;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.mapper.Course.CourseMapper;
import com.tronk.analysis.repository.CourseRepository;
import com.tronk.analysis.repository.DepartmentRepository;
import com.tronk.analysis.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseServiceImpl implements CourseService {
	CourseRepository courseRepository;
	DepartmentRepository departmentRepository;

	@Override
	public CourseResponse createCourse(UploadCourseRequest request) {
		Course course = new Course();
		course.setName(request.getName());
		course.setCredit(request.getCredit());
		course.setBaseFeeCredit(request.getBaseFeeCredit());
		course.setSubjectType(request.isSubjectType());

		if (request.getPrerequisiteIds() != null && !request.getPrerequisiteIds().isEmpty()) {
			Set<Course> prerequisites = new HashSet<>(courseRepository.findAllById(request.getPrerequisiteIds()));
			course.setPrerequisites(prerequisites);
		}

		courseRepository.save(course);
		return CourseMapper.toResponse(course);
	}

	@Override
	public List<CourseResponse> getAllCourses() {
		return CourseMapper.toResponseList(courseRepository.findAll());
	}

	@Override
	public CourseResponse getCourseById(UUID id) {
		return CourseMapper.toResponse(
			courseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Course not found")));
	}

	@Override
	public CourseResponse updateCourse(UpdateCourseRequest request) {
		Course course = courseRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Course not found"));
		course.setName(request.getName());
		course.setCredit(request.getCredit());
		course.setBaseFeeCredit(request.getBaseFeeCredit());
		course.setSubjectType(request.isSubjectType());

		if (request.getPrerequisiteIds() != null) {
			Set<Course> prerequisites = new HashSet<>(courseRepository.findAllById(request.getPrerequisiteIds()));
			course.setPrerequisites(prerequisites);
		}

		courseRepository.save(course);
		return CourseMapper.toResponse(course);
	}

	@Override
	public void deleteCourseById(UUID id) {
		Course entity = courseRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Course not found"));
		courseRepository.delete(entity);
	}

	@Override
	public String softDeleteCourse(UUID id) {
		Course entity = courseRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Course not found"));
		entity.setDeletedAt(LocalDateTime.now());
		courseRepository.save(entity);
		return "Course with ID " + id + " has been soft deleted";
	}

	@Override
	public void removeCourseFromDepartment(RemoveCourseFromDepartmentRequest request) {
		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.DEPARTMENT_NOT_FOUND));

		if(!department.getCourses().contains(course)) {
			throw new ApplicationException(ErrorCode.DEPARTMENT_DOES_NOT_HAVE_COURSES);
		}

		removeDepartmentFromCourse(department, course);
		departmentRepository.save(department);
	}

	private void removeDepartmentFromCourse(Department department, Course course) {
		department.getCourses().remove(course);
		course.getDepartments().remove(department);
	}

	@Override
	public void assignCourseToDepartment(AssignCourseToDepartmentRequest request) {
		Course course = courseRepository.findById(request.getCourseId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.COURSE_NOT_FOUND));

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new ApplicationException(ErrorCode.DEPARTMENT_NOT_FOUND));

		if(department.getCourses().contains(course)) {
			throw new ApplicationException(ErrorCode.DEPARTMENT_ALREADY_EXIST_COURSES);
		}
		
		assignDepartmentFromCourse(department, course);
		departmentRepository.save(department);
	}

	private void assignDepartmentFromCourse(Department department, Course course) {
		department.getCourses().add(course);
		course.getDepartments().add(department);
	}
}
