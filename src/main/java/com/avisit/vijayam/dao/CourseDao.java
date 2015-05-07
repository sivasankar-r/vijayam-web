package com.avisit.vijayam.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Course;

@Component
public class CourseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Course> getAllCourses() {
		String query = "SELECT * FROM course ORDER BY courseId";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Course>(Course.class));
	}
}
