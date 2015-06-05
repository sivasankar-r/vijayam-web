package com.avisit.vijayam.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.CoursesMBean;
import com.avisit.vijayam.managed.RepeatPaginator;
import com.avisit.vijayam.managed.ContentProviderMBean;
import com.avisit.vijayam.model.Course;
import com.avisit.vijayam.service.CourseService;

@Component
@ManagedBean(name="courseController")
@Scope(value="request")
public class CourseController {
	private Course newCourse;
	private String message;
	@Autowired
	private CourseService courseService;
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	@Autowired
	private CoursesMBean coursesMBean;

	@PostConstruct
	public void init() {
		newCourse = new Course();
		contentProviderMBean.getBreadCrumbs().add("All Courses");
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

	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
	}

	public CoursesMBean getCoursesMBean() {
		return coursesMBean;
	}

	public void setCoursesMBean(CoursesMBean coursesMBean) {
		this.coursesMBean = coursesMBean;
	}

	public String loadCourses() {
		message = null;
		contentProviderMBean.setSelectedCourse(new Course());
		int size = contentProviderMBean.getBreadCrumbs().size();
		for(int i = size-1; i > 0; i--){
			contentProviderMBean.getBreadCrumbs().remove(i);	
		}
		coursesMBean.setPaginator(new RepeatPaginator<Course>(courseService.fetchCourseByProvider(contentProviderMBean.getContentProvider().getContentProviderId())));
		return "courses";
	}

	public void addCourse() {
		boolean success = false;
		if (newCourse != null) {
			newCourse.setContentProviderId(contentProviderMBean.getContentProvider().getContentProviderId());
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
		if(contentProviderMBean.getSelectedCourse()!=null){
			if(courseService.toggleEnableFlag(contentProviderMBean.getSelectedCourse())){
				message = contentProviderMBean.getSelectedCourse().isEnabledFlag() ? "Info : Course disabled" : "Info : Course enabled" ;
				loadCourses();
			} else {
				message = "Error : Failed to toggle the course enable / disable";
			}
		}
	}
	
	public void editCourse() {
		if(contentProviderMBean.getSelectedCourse()!=null){
			if(courseService.editCourse(contentProviderMBean.getSelectedCourse())){
				loadCourses();
				message = "Info : Course updated successfully";
			} else {
				message = "Error : Failed to update the course";
			}
		}
	}
	
	public void deleteCourse() {
		if(contentProviderMBean.getSelectedCourse()!=null){
			courseService.deleteCourse(contentProviderMBean.getSelectedCourse());
			loadCourses();
		}
	}
}
