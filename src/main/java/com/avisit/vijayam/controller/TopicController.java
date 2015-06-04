package com.avisit.vijayam.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.RepeatPaginator;
import com.avisit.vijayam.managed.TopicsMBean;
import com.avisit.vijayam.managed.UserMBean;
import com.avisit.vijayam.model.Topic;
import com.avisit.vijayam.service.TopicService;

@Component
@ManagedBean
@Scope(value="request")
public class TopicController {
	@Autowired
	private UserMBean userMBean;
	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicsMBean topicsMBean;
	private String message;
	private Topic newTopic;
	
	@PostConstruct
	public void init() {
		newTopic = new Topic();
	}
	
	public UserMBean getUserMBean() {
		return userMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public TopicService getTopicService() {
		return topicService;
	}

	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}

	public TopicsMBean getTopicsMBean() {
		return topicsMBean;
	}

	public void setTopicsMBean(TopicsMBean topicsMBean) {
		this.topicsMBean = topicsMBean;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Topic getNewTopic() {
		return newTopic;
	}

	public void setNewTopic(Topic newTopic) {
		this.newTopic = newTopic;
	}

	public String loadTopics() {
		message = null;
		String toPage = "courses";
		int size = userMBean.getBreadCrumbs().size();
		for(int i = size-1; i >= 1; i--){
			userMBean.getBreadCrumbs().remove(i);	
		}
		if(userMBean.getSelectedCourse()!=null){
			topicsMBean.setPaginator(new RepeatPaginator<Topic>(topicService.fetchTopicsByCourseId(userMBean.getSelectedCourse().getId())));
			userMBean.getBreadCrumbs().add(userMBean.getSelectedCourse().getName());
			toPage = "topics";
		}
		return toPage;
	}
	
	public void toggleEnableFlag() {
		if(userMBean.getSelectedTopic()!=null){
			if(topicService.toggleEnableFlag(userMBean.getSelectedTopic())){
				message = userMBean.getSelectedTopic().isEnabledFlag() ? "Info : Topic disabled" : "Info : Topic enabled" ;
				loadTopics();
			} else {
				message = "Error : Failed to toggle the topic enable / disable";
			}
		}
	}
	
	public void editTopic() {
		if(userMBean.getSelectedTopic()!=null){
			if(topicService.editTopic(userMBean.getSelectedTopic())){
				loadTopics();
				message = "Info : Topic updated successfully";
			} else {
				message = "Error : Failed to update the Topic";
			}
		}
	}
	
	public void deleteTopic() {
		if(userMBean.getSelectedTopic()!=null){
			topicService.deleteTopic(userMBean.getSelectedTopic());
			loadTopics();
		}
	}
	
	public void addTopic() {
		boolean success = false;
		if (newTopic != null && userMBean.getSelectedCourse() != null) {
			newTopic.setCourseId(userMBean.getSelectedCourse().getId());
			newTopic.setSortOrder(topicsMBean.getPaginator().getRecordsTotal() + 1);
			newTopic.setCreatedTs(new Date());
			newTopic.setLastModifiedTs(new Date());
			success = topicService.addNew(newTopic);
		}
		if (success) {
			loadTopics();
			setMessage("Info : New topic added Successfully");
		} else {
			setMessage("Error : Failed to add new topic");
		}
	}
	
}
