package com.avisit.vijayam.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.QuestionEditMBean;
import com.avisit.vijayam.model.Option;
import com.avisit.vijayam.service.QuestionService;

@Component
@ManagedBean(name="questionEditController")
@Scope(value="request")
public class QuestionEditController {

	@Autowired
	private QuestionEditMBean questionEditMBean;
	
	@Autowired
	private QuestionService questionService;
	
	public QuestionEditMBean getQuestionEditMBean() {
		return questionEditMBean;
	}

	public void setQuestionEditMBean(QuestionEditMBean questionEditMBean) {
		this.questionEditMBean = questionEditMBean;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	public String updateQuestion(){
		for(Option option : questionEditMBean.getSelQuestion().getOptionsList()){
			if(questionEditMBean.getOptionSelected().getContent().equals(option.getContent())){
				option.setCorrect(true);
			}else{
				option.setCorrect(false);
			}
		}
		questionService.update(questionEditMBean.getSelQuestion());
		return "questions";
	}
	
	public String loadEditQuestion(){
		for(Option option : questionEditMBean.getSelQuestion().getOptionsList()){
			if(option.isCorrect()){
				questionEditMBean.setOptionSelected(option);
			}
		}
		return "editQuestion";
	}
	
	public void addOption(AjaxBehaviorEvent event){
		Option option = new Option(questionEditMBean.getSelQuestion().getQuestionId(), questionEditMBean.getNewOptionText());
		questionEditMBean.getSelQuestion().addOption(option);
	}
	
	public void removeOption(){
		questionEditMBean.getSelQuestion().removeOption(questionEditMBean.getOptionToRemove());
	}
}
