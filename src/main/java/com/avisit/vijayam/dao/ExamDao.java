package com.avisit.vijayam.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Exam;

@Component
public class ExamDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Exam> getAllExams() {
		String query = "SELECT * FROM exam ORDER BY examId";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Exam>(Exam.class));
	}
}
