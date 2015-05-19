package com.avisit.vijayam.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Course;
import com.avisit.vijayam.model.Option;
import com.avisit.vijayam.model.Question;
import com.avisit.vijayam.model.Topic;

@Component
public class CourseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Course> getAllCourses() {
		String query = "SELECT id, name, description, imageName, enabledFlag FROM course ORDER BY id ";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Course>(Course.class));
	}

	public List<Course> getCourseByProvider(String contentProviderId){
		String query = "SELECT id, name, description, imageName, enabledFlag, sortOrder, contentProviderId FROM course WHERE contentProviderId = ? and enabledFlag = ? ORDER BY sortOrder";
		List<Course> courseList = null;
		courseList = jdbcTemplate.query(query, new Object[]{contentProviderId, 1}, new BeanPropertyRowMapper<Course>(Course.class));
		for(Course course : courseList) {
			List<Topic> topicList = jdbcTemplate.query("select id, name, description, enabledFlag, sortOrder, courseId FROM topic where courseId = ? and enabledFlag = ? ORDER BY sortOrder", new Object[]{course.getId(), 1}, new BeanPropertyRowMapper<Topic>(Topic.class));
			for(Topic topic : topicList){
				topic.setQuestions(getQuestionsByTopic(topic.getId()));
			}
			course.setTopicList(topicList);
		}
		return courseList;
	}
	
	public List<Question> getQuestionsByTopic(int topicId) {
		String questionQuery = "SELECT * FROM question WHERE topicId = " + topicId + " ORDER BY questionId";
		List<Question> questionList = jdbcTemplate.query(questionQuery, new BeanPropertyRowMapper<Question>(Question.class));
		
		String optionQry = "select optionId, questionId, content, correct from `option` where questionId=";
		for (Question question : questionList) {
			List<Option> options = jdbcTemplate.query( optionQry + question.getQuestionId(), new BeanPropertyRowMapper<Option>(Option.class));
			question.setOptionsList(options);
		}
		return questionList;
	}
}
