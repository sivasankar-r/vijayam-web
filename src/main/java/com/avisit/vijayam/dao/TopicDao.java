package com.avisit.vijayam.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.common.NoResultException;
import com.avisit.vijayam.model.Topic;

@Component
public class TopicDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Topic> getAllTopics() {
		String query = "SELECT * FROM topic ORDER BY topicId";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Topic>(Topic.class));
	}
	
	public List<Topic> getTopicsByCourseId(int courseId){
		String query = "SELECT * FROM topic WHERE courseId = " + courseId + " ORDER BY topicId";
		List<Topic> topicList = null;
		try {
			topicList = jdbcTemplate.query(query, new BeanPropertyRowMapper<Topic>(Topic.class));
		} catch (DataAccessException e) {
			if(e instanceof EmptyResultDataAccessException){
				throw new NoResultException("No Results Found");
			}else{
				throw e;
			}
		}
		return topicList;
	}
}
