package com.avisit.vijayam.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.common.InternalServerException;
import com.avisit.vijayam.model.Course;
import com.avisit.vijayam.service.CourseService;

@Component
@Path("/courses")
public class CourseRestService {
	
	@Autowired
	private CourseService courseService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> fetchCourses() {
		List<Course> courseList = null;
		try{
			courseList = courseService.fetchAllCourses();
		}catch(DataAccessException exception){
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return courseList;
	}
	
	@GET
	@Path("/contentProvider/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> fetchCourseByProvider(@PathParam("param") String contentProviderId) {
		List<Course> courseList = null;
		if(contentProviderId !=null && !contentProviderId.isEmpty()){
			try{
				courseList = courseService.fetchCourseByProvider(contentProviderId);
			} catch(DataAccessException exception){
				throw new InternalServerException("Unknown Exception Occurred");
			} catch (NumberFormatException nfe) {
				throw new InternalServerException("Content provider id should be a valid integer");
			}
		}
		
		return courseList;
	}
	
}
