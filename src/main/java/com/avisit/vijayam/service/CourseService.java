package com.avisit.vijayam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.dao.CourseDao;
import com.avisit.vijayam.model.Course;

@Component
public class CourseService {
	
	@Autowired
	private CourseDao courseDao; 
	
	public List<Course> fetchAllCourses() {
		List<Course> courseList = null;
		courseList = courseDao.getAllCourses();
		return courseList;
	}
	
	public List<Course> fetchCourseByProvider(String contentProviderId){
		List<Course> courseList = null;
		courseList = courseDao.getCourseByProvider(contentProviderId);
		return courseList;
	}
}
