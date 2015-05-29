package com.avisit.vijayam.dao;

import java.sql.SQLException;
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
import com.avisit.vijayam.model.Exam;

@Repository
public class ExamDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Exam> getAllExams() {
		String query = "SELECT * FROM exam ORDER BY examId";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Exam>(Exam.class));
	}
	
	
	public List<Exam> getExamsByContentProvider(String contentProviderId) {
		String query = "SELECT e.examId, e.title, e.description, e.passCode, e.duration, count(eq.questionId) as questionCount FROM exam e INNER JOIN exam_question eq ON eq.examId = e.examId INNER JOIN client_exam ce ON e.examId = ce.examId WHERE ce.clientId = '" + contentProviderId +"' group by ce.examId";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Exam>(Exam.class));
	}
	
	public Exam getExamDetailById(int examId){
		String query = "SELECT * FROM exam e WHERE e.examId="+examId;
		Exam exam = null;
		try {
			exam  = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<Exam>(Exam.class));
		} catch (DataAccessException e) {
			if(e instanceof EmptyResultDataAccessException){
				throw new NoResultException("No Results Found");
			}else{
				throw e;
			}
		}
		return exam;
	}
	
	public int createAndReturnExamId(Exam exam) throws SQLException{
		int examId = 0;
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("exam");
		jdbcInsert.setGeneratedKeyName("examId");
		Number key = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(exam));
		if (key != null){
			examId = key.intValue();
		} 
		return examId;
	}
	
	public int mapExamToClient(int examId, String contentProviderId){
		int rowsUpdated = 0;
		if(examId>0 &&(contentProviderId!=null && contentProviderId.isEmpty())){
			rowsUpdated = jdbcTemplate.update("INSERT INTO client_exam (clientId, examId) VALUES(?,?)", new Object[] {contentProviderId, examId });
		}
		return rowsUpdated;
	}
}
