package com.avisit.vijayam.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.common.InternalServerException;
import com.avisit.vijayam.common.NoResultException;
import com.avisit.vijayam.model.Question;
import com.avisit.vijayam.service.QuestionService;

@Component
@Path("/questions")
public class QuestionRestService {
	
	@Autowired
	private QuestionService questionService;
	
	@GET
	@Path("/topicId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Question> fetchQuestionById(@PathParam("id") int topicId) {
		List<Question> questionList = null;
		try {
			questionList = questionService.fetchQuestionsByTopic(topicId);
		} catch(NoResultException nre){
			throw nre;
		} catch (DataAccessException e) {
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return questionList;
	}
	
	@POST
	@Path("/byTopic")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Question> fetchQuestionList(MultivaluedMap<String, String> params) {
		List<Question> questionList = null;
		if(params !=null && !params.isEmpty()){
			try {
				int topicId = Integer.valueOf(params.getFirst("topicId"));
				questionList = questionService.fetchQuestionsByTopic(topicId);
			} catch(NoResultException nre){
				throw nre;
			} catch (DataAccessException e) {
				throw new InternalServerException("Unknown Exception Occurred");
			}
		}
		
		return questionList;
	}
	
}
