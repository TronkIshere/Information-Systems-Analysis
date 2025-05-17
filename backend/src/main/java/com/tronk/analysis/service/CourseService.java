package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.course.UploadCourseRequest;
import com.tronk.analysis.dto.request.course.UpdateCourseRequest;
import com.tronk.analysis.dto.response.course.CourseResponse;
import java.util.UUID;
import java.util.List;

public interface CourseService {

	CourseResponse createCourse(UploadCourseRequest request);

	CourseResponse getCourseById(UUID id);

	List<CourseResponse> getAllCourses();

	CourseResponse updateCourse(UpdateCourseRequest request);

	void deleteCourseById(UUID id);

	String softDeleteCourse(UUID id);

}