package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.course.UpdateCourseRequest;
import com.tronk.analysis.dto.request.course.UploadCourseRequest;
import com.tronk.analysis.dto.request.department.AssignCourseToDepartmentRequest;
import com.tronk.analysis.dto.request.department.RemoveCourseFromDepartmentRequest;
import com.tronk.analysis.dto.response.course.CourseResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {

	CourseResponse createCourse(UploadCourseRequest request);

	CourseResponse getCourseById(UUID id);

	List<CourseResponse> getAllCourses();

	CourseResponse updateCourse(UpdateCourseRequest request);

	void deleteCourseById(UUID id);

	String softDeleteCourse(UUID id);

    void removeCourseFromDepartment(RemoveCourseFromDepartmentRequest request);

	void assignCourseToDepartment(AssignCourseToDepartmentRequest request);
}