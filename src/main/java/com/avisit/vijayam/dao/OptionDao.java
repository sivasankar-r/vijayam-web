package com.avisit.vijayam.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.avisit.vijayam.model.Option;

@Repository
public class OptionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int persistOptions(int questionId, Option option) throws SQLException{
		int rowsUpdated = 0;
		if(questionId > 0 && option!=null && !option.getContent().trim().isEmpty()){
			rowsUpdated = getJdbcTemplate().update("INSERT INTO `option` (questionId, optionId, content, correct ) VALUES(?,?,?,?)", new Object[] { questionId, option.getOptionId(), option.getContent(), option.isCorrect() });	
		}
		return rowsUpdated;
	}
}
