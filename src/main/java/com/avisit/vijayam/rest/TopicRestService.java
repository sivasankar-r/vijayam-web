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
import com.avisit.vijayam.common.NoResultException;
import com.avisit.vijayam.model.Topic;
import com.avisit.vijayam.service.TopicService;

@Component
@Path("/topics")
public class TopicRestService {
	
	@Autowired
	private TopicService topicService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Topic> fetchTopics() {
		List<Topic> topicList = null;
		try{
			topicList = topicService.fetchAllTopics();
		}catch(DataAccessException exception){
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return topicList;
	}
	
	@GET
	@Path("/courseId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Topic> fetchTopicById(@PathParam("id") int courseId) {
		List<Topic> topicList = null;
		try {
			topicList = topicService.fetchTopicsByCourseId(courseId);
		} catch(NoResultException nre){
			throw nre;
		} catch (DataAccessException e) {
			throw new InternalServerException("Unknown Exception Occurred");
		}
		return topicList;
	}
	
}
