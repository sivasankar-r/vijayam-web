package com.avisit.vijayam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.ContentProviderMBean;
import com.avisit.vijayam.managed.RepeatPaginator;
import com.avisit.vijayam.managed.TopicsMBean;
import com.avisit.vijayam.model.Course;
import com.avisit.vijayam.model.Topic;
import com.avisit.vijayam.service.TopicService;

@Component
@ManagedBean
@RequestScoped
public class TopicController {
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicsMBean topicsMBean;
	
	private String message;
	private Topic selectedTopic;
	private Topic newTopic;
	private Course selectedCourse;
	private List<String> breadCrumbs;
	
	@PostConstruct
	public void init() {
		newTopic = new Topic();
		breadCrumbs = new ArrayList<String>();
	}
	
	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
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

	public Topic getSelectedTopic() {
		return selectedTopic;
	}

	public void setSelectedTopic(Topic selectedTopic) {
		this.selectedTopic = selectedTopic;
	}

	public Topic getNewTopic() {
		return newTopic;
	}

	public void setNewTopic(Topic newTopic) {
		this.newTopic = newTopic;
	}

	public Course getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(Course selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public List<String> getBreadCrumbs() {
		return breadCrumbs;
	}

	public void setBreadCrumbs(List<String> breadCrumbs) {
		this.breadCrumbs = breadCrumbs;
	}

	public String loadTopics() {
		message = null;
		String toPage = "courses";
		if(selectedCourse!=null){
			topicsMBean.setPaginator(new RepeatPaginator<Topic>(topicService.fetchTopicsByCourseId(selectedCourse.getId())));
			breadCrumbs.clear();
			breadCrumbs.add("All Courses");
			breadCrumbs.add(selectedCourse.getName());
			toPage = "topics";
		}
		return toPage;
	}
	
	public void toggleEnableFlag() {
		if(selectedTopic!=null){
			if(topicService.toggleEnableFlag(selectedTopic)){
				message = selectedTopic.isEnabledFlag() ? "Info : Topic disabled" : "Info : Topic enabled" ;
				loadTopics();
			} else {
				message = "Error : Failed to toggle the topic enable / disable";
			}
		}
	}
	
	public void editTopic() {
		if(selectedTopic!=null){
			if(topicService.editTopic(selectedTopic)){
				loadTopics();
				message = "Info : Course updated successfully";
			} else {
				message = "Error : Failed to update the course";
			}
		}
	}
	
	public void deleteTopic() {
		if(selectedTopic!=null){
			if(topicService.deleteTopic(selectedTopic)){
				loadTopics();
				message = "Info : Topic deleted successfully";
			} else {
				message = "Error : Failed to delete the topic";
			}
		}
	}
	
	public void addTopic() {
		boolean success = false;
		if (newTopic != null && selectedCourse != null) {
			newTopic.setCourseId(selectedCourse.getId());
			newTopic.setSortOrder(topicsMBean.getPaginator().getRecordsTotal() + 1);
			newTopic.setCreatedTs(new Date());
			newTopic.setLastModifiedTs(new Date());
			success = topicService.addNew(newTopic);
		}
		if (success) {
			loadTopics();
			setMessage("Info : New course added Successfully");
		} else {
			setMessage("Error : Failed to add new course");
		}
	}
	
}
