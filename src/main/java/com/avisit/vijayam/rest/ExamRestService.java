package com.avisit.vijayam.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.common.InternalServerException;
import com.avisit.vijayam.model.Exam;
import com.avisit.vijayam.service.ExamService;

@Component
@Path("/exams")
public class ExamRestService {
	
	@Autowired
	private ExamService examService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Exam> fetchExams() {
		List<Exam> examList = null;
		try{
			examList = examService.fetchAllExams();
		}catch(DataAccessException exception){
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return examList;
	}
	
}
