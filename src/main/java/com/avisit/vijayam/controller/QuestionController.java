package com.avisit.vijayam.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.QuestionsMBean;
import com.avisit.vijayam.managed.ContentProviderMBean;
import com.avisit.vijayam.service.QuestionService;

@Component
@ManagedBean(name = "questionController")
@Scope(value="request")
public class QuestionController {
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionsMBean questionsMBean;
	private String message;
	private String newOptionText;
	
	
	@PostConstruct
	public void init() {

	}
	
	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
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
		int size = contentProviderMBean.getBreadCrumbs().size();
		for(int i = size-1; i >= 2; i--){
			contentProviderMBean.getBreadCrumbs().remove(i);	
		}
		if(contentProviderMBean.getSelectedTopic()!=null){
			questionsMBean.setQuestionList(questionService.fetchQuestions(contentProviderMBean.getSelectedTopic().getId()));
			contentProviderMBean.getBreadCrumbs().add(contentProviderMBean.getSelectedTopic().getName());
			toPage = "questions";
		}
		
		return toPage;
	}
	
	public void deleteQuestion() {
		if(contentProviderMBean.getSelectedQuestion()!=null){
			questionService.deleteQuestion(contentProviderMBean.getSelectedQuestion());
			loadQuestions();
		}
	}
}
