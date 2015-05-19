package com.avisit.vijayam.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Option;
import com.avisit.vijayam.model.Question;

@Component
public class QuestionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
