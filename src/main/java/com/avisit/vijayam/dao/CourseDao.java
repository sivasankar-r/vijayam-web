package com.avisit.vijayam.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.avisit.vijayam.model.Course;
import com.avisit.vijayam.model.Option;
import com.avisit.vijayam.model.Question;
import com.avisit.vijayam.model.Topic;

@Repository
public class CourseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Course> getAllCourses() {
		String query = "SELECT id, name, description, imageName, enabledFlag FROM course ORDER BY id ";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Course>(Course.class));
	}

	public List<Course> getCourseTreeByProvider(String contentProviderId){
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
		
		String optionQry = "select optionId, questionId, content, correct from `option` where questionId=?";
		for (Question question : questionList) {
			List<Option> options = jdbcTemplate.query( optionQry,  new Object[]{ question.getQuestionId()}, new BeanPropertyRowMapper<Option>(Option.class));
			question.setOptionsList(options);
		}
		return questionList;
	}

	public List<Course> getCourseByProvider(String contentProviderId) {
		String query = "SELECT id, name, description, imageName, enabledFlag, sortOrder, contentProviderId FROM course WHERE contentProviderId = ? ORDER BY sortOrder";
		List<Course> courseList = jdbcTemplate.query(query, new Object[]{contentProviderId}, new BeanPropertyRowMapper<Course>(Course.class));
		return courseList;
	}

	public int insertCourse(Course course) {
		int id = 0;
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("course");
		jdbcInsert.setGeneratedKeyName("id");
		Number key = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(course));
		if (key != null) {
			id = key.intValue();
		}
		return id;
	}

	public int toggleEnableFlag(Course course) {
		int updateCount = 0; 
		if (course.getId() > 0) {
			updateCount = jdbcTemplate.update("update course SET enabledFlag=?, lastModifiedTs=? WHERE id=?", new Object[] { !course.isEnabledFlag(), new Date(), course.getId() });
		}
		return updateCount;
	}

	public int editCourse(Course course) {
		int updateCount = 0; 
		if (course.getId() > 0) {
			updateCount = jdbcTemplate.update("update course SET name=?, description=?, lastModifiedTs=? WHERE id=?", new Object[] { course.getName(), course.getDescription(), new Date(), course.getId() });
		}
		return updateCount;
	}

	public int deleteCourse(Course course) {
		return jdbcTemplate.update("call proc_delete_course_tree(?)", course.getId());
	}

	/**
	 * Returns the maximum sort order value available in the course table for the input content provider id
	 * 
	 * @param contentProviderId content provider id
	 * @return int max sort order available
	 */
	public int fetchMaxSortOrder(String contentProviderId) {
		int value = 0;
		if(contentProviderId!= null && !contentProviderId.isEmpty()){
			String query = "select sortOrder from course where contentProviderId = ? order by sortOrder desc limit 1";
			try{
				value = jdbcTemplate.queryForObject(query, new Object[] {contentProviderId}, Integer.class);
			} catch(EmptyResultDataAccessException e){
				// supress exception; already value is 0;
			}
		}
		return value;
	}
}
