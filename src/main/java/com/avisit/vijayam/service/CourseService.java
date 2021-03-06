package com.avisit.vijayam.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.CourseDao;
import com.avisit.vijayam.dao.TopicDao;
import com.avisit.vijayam.model.Course;

@Service
public class CourseService {
	
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private TopicDao topicDao; 
	
	public List<Course> fetchAllCourses() {
		return courseDao.getAllCourses();
	}
	
	public List<Course> fetchCourseTreeByProvider(String contentProviderId){
		return courseDao.getCourseTreeByProvider(contentProviderId);
	}

	public List<Course> fetchCourseByProvider(String contentProviderId) {
		List<Course> courseList = null;
		courseList = courseDao.getCourseByProvider(contentProviderId);
		for(Course course : courseList){
			course.setTopicCount(topicDao.getTopicCount(course.getId()));
		}
		return courseList;
	}

	public boolean addNew(Course newCourse) {
		boolean flag = false;
		if(newCourse!=null){
			int sortOrder = courseDao.fetchMaxSortOrder(newCourse.getContentProviderId());
			newCourse.setSortOrder(sortOrder + 1);
			newCourse.setCreatedTs(new Date());
			newCourse.setLastModifiedTs(new Date());
			flag = courseDao.insertCourse(newCourse) > 0;
		}
		return flag;
	}

	public boolean toggleEnableFlag(Course course) {
		return courseDao.toggleEnableFlag(course) == 1;
	}

	public boolean editCourse(Course course) {
		return courseDao.editCourse(course) == 1;
	}

	public int deleteCourse(Course course) {
		return courseDao.deleteCourse(course);
	}
}
