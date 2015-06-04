package com.avisit.vijayam.managed;

import javax.faces.bean.ManagedBean;

import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Course;

@Component
@ManagedBean(name = "coursesMBean")
public class CoursesMBean {
	private RepeatPaginator<Course> paginator;
	
	public RepeatPaginator<Course> getPaginator() {
		return paginator;
	}

	public void setPaginator(RepeatPaginator<Course> paginator) {
		this.paginator = paginator;
	}

	
}
