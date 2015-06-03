package com.avisit.vijayam.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.OptionDao;
import com.avisit.vijayam.dao.QuestionDao;
import com.avisit.vijayam.model.Option;
import com.avisit.vijayam.model.Question;

@Service
public class QuestionService {
	Logger logger = LoggerFactory.getLogger(QuestionService.class);
	@Autowired
	private QuestionDao questionDao; 
	@Autowired
	private OptionDao optionDao;
	
	public List<Question> fetchQuestionsByTopic(int topicId) {
		List<Question> questionList = new ArrayList<Question>();
		if(topicId > 0){
			questionList = questionDao.getQuestionsByTopic(topicId);	
		}
		return questionList;
	}

	public List<Question> fetchQuestions(int topicId){
		return questionDao.fetchQuestions(topicId);
	}

	public void update(Question question) {
		if(question!=null){
			try {
				questionDao.update(question);
				int optionId = 1;
				for (Option option : question.getOptionsList()) {
					option.setOptionId(optionId++);
					questionDao.persistOptions(question.getQuestionId(), option);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int persistQuestion(Question question) throws Exception {
		int questionId = 0;
		if(question!=null && !question.getContent().trim().isEmpty()){
			try {
				questionId = questionDao.createAndReturnQuestionId(question);
				logger.info("QuestionId : "+questionId+" added successfully");	
			} catch (SQLException e) {
				throw new Exception("Exception occurred in creating question with question content \""+question.getContent()+"\"", e);
			}
			if(questionId >0){
				for(Option option : question.getOptionsList()){
					int rowsUpdated = 0;
					try {
						rowsUpdated = optionDao.persistOptions(questionId, option);
					} catch (SQLException e) {
						throw new Exception("Exception occurred in persisting optionId : " + option.getOptionId()+ "for the question \""+question.getContent()+"\"", e);
					}
					if(rowsUpdated>0){
						logger.info("Persisted Question ID: "+questionId+ ", optionId: "+option.getOptionId());
					}else{
						logger.error("Failed to Persist Question ID: "+questionId+ ", optionId: "+option.getOptionId());
					}
				}
			}
		}
		return questionId;
	}

	public int deleteQuestion(Question question) {
		return questionDao.deleteQuestion(question);
	}
}
