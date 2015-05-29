package com.avisit.vijayam.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.avisit.vijayam.common.NoResultException;
import com.avisit.vijayam.model.Topic;

@Repository
public class TopicDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Topic> getAllTopics() {
		String query = "SELECT * FROM topic ORDER BY topicId";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Topic>(Topic.class));
	}
	
	public List<Topic> getTopicsByCourseId(int courseId){
		String query = "SELECT * FROM topic WHERE courseId = " + courseId + " ORDER BY sortOrder";
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
	
	public int getTopicCount(int courseId){
		String query = "SELECT COUNT(*) FROM topic where courseId= ?";
		int count = jdbcTemplate.queryForObject(query, new Object[] {courseId}, Integer.class);
		return count;
	}

	public int toggleEnableFlag(Topic topic) {
		int updateCount = 0; 
		if (topic.getId() > 0) {
			updateCount = jdbcTemplate.update("update topic SET enabledFlag=?, lastModifiedTs=? WHERE id=?", new Object[] { !topic.isEnabledFlag(), new Date(), topic.getId() });
		}
		return updateCount;
	}

	public int editTopic(Topic topic) {
		int updateCount = 0; 
		if (topic.getId() > 0) {
			updateCount = jdbcTemplate.update("update topic SET name=?, description=?, lastModifiedTs=? WHERE id=?", new Object[] { topic.getName(), topic.getDescription(), new Date(), topic.getId() });
		}
		return updateCount;
	}

	public int deleteTopic(Topic topic) {
		int updateCount = 0; 
		if (topic.getId() > 0) {
			updateCount = jdbcTemplate.update("delete from topic WHERE id=?", new Object[] { topic.getId() });
		}
		return updateCount;
	}

	public int insertTopic(Topic topic) {
		int id = 0;
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("topic");
		jdbcInsert.setGeneratedKeyName("id");
		Number key = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(topic));
		if (key != null) {
			id = key.intValue();
		}
		return id;
	}
}
