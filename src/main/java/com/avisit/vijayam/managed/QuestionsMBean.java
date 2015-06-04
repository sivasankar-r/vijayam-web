package com.avisit.vijayam.managed;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Question;

@Component
@ManagedBean(name = "questionsMBean")
public class QuestionsMBean{
	private List<Question> questionList;
	
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

}