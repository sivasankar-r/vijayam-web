package com.avisit.vijayam.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.QuestionsMBean;
import com.avisit.vijayam.managed.UserMBean;
import com.avisit.vijayam.service.QuestionService;

@Component
@ManagedBean(name = "questionController")
@RequestScoped
public class QuestionController {
	@Autowired
	private UserMBean userMBean;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionsMBean questionsMBean;
	private String message;
	private String newOptionText;
	
	
	@PostConstruct
	public void init() {

	}
	
	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionsMBean(QuestionsMBean questionsMBean) {
		this.questionsMBean = questionsMBean;
	}

	public QuestionsMBean getQuestionsMBean() {
		return questionsMBean;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNewOptionText() {
		return newOptionText;
	}

	public void setNewOptionText(String newOptionText) {
		this.newOptionText = newOptionText;
	}

	public String loadQuestions(){
		message = null;
		String toPage = "topics";
		int size = userMBean.getBreadCrumbs().size();
		for(int i = size-1; i >= 2; i--){
			userMBean.getBreadCrumbs().remove(i);	
		}
		if(userMBean.getSelectedTopic()!=null){
			questionsMBean.setQuestionList(questionService.fetchQuestions(userMBean.getSelectedTopic().getId()));
			userMBean.getBreadCrumbs().add(userMBean.getSelectedTopic().getName());
			toPage = "questions";
		}
		
		return toPage;
	}
	
	public void deleteQuestion() {
		if(userMBean.getSelectedQuestion()!=null){
			questionService.deleteQuestion(userMBean.getSelectedQuestion());
			loadQuestions();
		}
	}
}
