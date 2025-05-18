package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.course.UploadCourseRequest;
import com.tronk.analysis.dto.request.course.UpdateCourseRequest;
import com.tronk.analysis.dto.response.course.CourseResponse;
import com.tronk.analysis.entity.Course;
import com.tronk.analysis.repository.CourseRepository;
import com.tronk.analysis.service.CourseService;
import com.tronk.analysis.mapper.Course.CourseMapper;
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
public class CourseServiceImpl implements CourseService {
	CourseRepository courseRepository;

	@Override
	public CourseResponse createCourse(UploadCourseRequest request) {
		Course course = new Course();
		course.setName(request.getName());
		course.setCredit(request.getCredit());
		course.setBaseFeeCredit(request.getBaseFeeCredit());
		Course savedEntity = courseRepository.save(course);
		return CourseMapper.toResponse(savedEntity);
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
		Course entity = courseRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Course not found"));
		Course course = new Course();
		course.setName(request.getName());
		course.setCredit(request.getCredit());
		course.setBaseFeeCredit(request.getBaseFeeCredit());
		return CourseMapper.toResponse(courseRepository.save(entity));
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
}
