package com.avisit.vijayam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.QuestionDao;
import com.avisit.vijayam.model.Question;

@Service
public class QuestionService {

	@Autowired
	private QuestionDao questionDao; 
	
	public List<Question> fetchQuestionsByTopic(int topicId) {
		List<Question> questionList = new ArrayList<Question>();
		if(topicId > 0){
			questionList = questionDao.getQuestionsByTopic(topicId);	
		}
		return questionList;
	}

}
