package com.avisit.vijayam.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.CoursesMBean;
import com.avisit.vijayam.managed.RepeatPaginator;
import com.avisit.vijayam.managed.UserMBean;
import com.avisit.vijayam.model.Course;
import com.avisit.vijayam.service.CourseService;

@Component
@ManagedBean
@RequestScoped
public class CourseController {
	private Course newCourse;
	private String message;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserMBean userMBean;
	@Autowired
	private CoursesMBean coursesMBean;

	@PostConstruct
	public void init() {
		newCourse = new Course();
		userMBean.getBreadCrumbs().add("All Courses");
	}

	public Course getNewCourse() {
		return newCourse;
	}

	public void setNewCourse(Course newCourse) {
		this.newCourse = newCourse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public CoursesMBean getCoursesMBean() {
		return coursesMBean;
	}

	public void setCoursesMBean(CoursesMBean coursesMBean) {
		this.coursesMBean = coursesMBean;
	}

	public String loadCourses() {
		message = null;
		userMBean.setSelectedCourse(new Course());
		int size = userMBean.getBreadCrumbs().size();
		for(int i = size-1; i > 0; i--){
			userMBean.getBreadCrumbs().remove(i);	
		}
		coursesMBean.setPaginator(new RepeatPaginator<Course>(courseService.fetchCourseByProvider(userMBean.getContentProvider().getContentProviderId())));
		return "courses";
	}

	public void addCourse() {
		boolean success = false;
		if (newCourse != null) {
			newCourse.setContentProviderId(userMBean.getContentProvider().getContentProviderId());
			newCourse.setSortOrder(coursesMBean.getPaginator().getRecordsTotal() + 1);
			newCourse.setCreatedTs(new Date());
			newCourse.setLastModifiedTs(new Date());
			success = courseService.addNew(newCourse);
		}
		if (success) {
			loadCourses();
			setMessage("Info : New course added Successfully");
		} else {
			setMessage("Error : Failed to add new course");
		}
	}
	
	public void toggleEnableFlag() {
		if(userMBean.getSelectedCourse()!=null){
			if(courseService.toggleEnableFlag(userMBean.getSelectedCourse())){
				message = userMBean.getSelectedCourse().isEnabledFlag() ? "Info : Course disabled" : "Info : Course enabled" ;
				loadCourses();
			} else {
				message = "Error : Failed to toggle the course enable / disable";
			}
		}
	}
	
	public void editCourse() {
		if(userMBean.getSelectedCourse()!=null){
			if(courseService.editCourse(userMBean.getSelectedCourse())){
				loadCourses();
				message = "Info : Course updated successfully";
			} else {
				message = "Error : Failed to update the course";
			}
		}
	}
	
	public void deleteCourse() {
		if(userMBean.getSelectedCourse()!=null){
			courseService.deleteCourse(userMBean.getSelectedCourse());
			loadCourses();
		}
	}
}
