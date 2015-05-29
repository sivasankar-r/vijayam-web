package com.avisit.vijayam.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.avisit.vijayam.model.Option;
import com.avisit.vijayam.model.Question;

@Repository
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

	public List<Question> fetchQuestions(int examId) {
		String questionQry = "select ques.questionId, ques.content, ques.type, ques.points, ques.difficulty, eq.sortOrder from question ques inner join exam_question eq on eq.questionId = ques.questionId where eq.examId="
				+ examId + " order by eq.sortOrder";
		List<Question> questionList = jdbcTemplate.query(questionQry,
				new BeanPropertyRowMapper<Question>(Question.class));
		String optionQry = "select options.optionId, options.questionId, options.content, options.correct from options where questionId=";
		for (Question question : questionList) {
			List<Option> options = jdbcTemplate.query(
					optionQry + question.getQuestionId(),
					new BeanPropertyRowMapper<Option>(Option.class));
			question.setOptionsList(options);
		}
		return questionList;
	}

	public List<Question> fetchQuestionsWithTypeDesc(int examId) {
		String questionQry = "select ques.questionId, ques.content, ques.type, qt.description as typeDesc, ques.points, ques.difficulty, eq.sortOrder from question ques inner join exam_question eq on eq.questionId = ques.questionId INNER JOIN question_type qt ON ques.type = qt.type where eq.examId="
				+ examId + " order by eq.sortOrder";
		List<Question> questionList = jdbcTemplate.query(questionQry,
				new BeanPropertyRowMapper<Question>(Question.class));
		String optionQry = "select options.optionId, options.questionId, options.content, options.correct from options where questionId=";
		for (Question question : questionList) {
			List<Option> options = jdbcTemplate.query(
					optionQry + question.getQuestionId(),
					new BeanPropertyRowMapper<Option>(Option.class));
			question.setOptionsList(options);
		}
		return questionList;
	}

	public int fetchQuestionCount(int examId) {
		String query = "SELECT COUNT(*) FROM exam_question where examId= ?";
		int count = jdbcTemplate.queryForObject(query, new Object[] {examId}, Integer.class);
		return count;
	}

	/*
	 * public int createQuestion(Question question){ int rowsUpdated = 0;
	 * if(question!=null){ rowsUpdated = jdbcTemplate.update(
	 * "INSERT INTO question (questionId, content) VALUES(?,?)", new Object[] {
	 * null, question.getContent() }); } return rowsUpdated; }
	 */

	public int createAndReturnQuestionId(Question question) throws SQLException {
		int questionId = 0;
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("question");
		jdbcInsert.setGeneratedKeyName("questionId");
		Number key = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(
						question));
		if (key != null) {
			questionId = key.intValue();
		}
		return questionId;
	}

	public int persistOptions(int questionId, Option option)
			throws SQLException {
		int rowsUpdated = 0;
		if (questionId > 0 && option != null
				&& !option.getContent().trim().isEmpty()) {
			rowsUpdated = jdbcTemplate.update("INSERT INTO options (questionId, optionId, content, correct ) VALUES(?,?,?,?)",
							new Object[] { questionId, option.getOptionId(),
									option.getContent(), option.isCorrect() });
		}
		return rowsUpdated;
	}

	public void update(Question question) throws SQLException {
		if (question.getQuestionId() > 0) {
			jdbcTemplate.update("update question SET content=?,type=?,points=?,difficulty=? WHERE questionId=?",
							new Object[] { question.getContent(),
									question.getType(), question.getPoints(),
									question.getDifficulty(),
									question.getQuestionId() });

			jdbcTemplate.update("delete from options WHERE questionId=?",
					new Object[] { question.getQuestionId() });
		}
	}
}
