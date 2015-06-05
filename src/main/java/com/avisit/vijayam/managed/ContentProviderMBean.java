package com.avisit.vijayam.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.ContentProvider;
import com.avisit.vijayam.model.Course;
import com.avisit.vijayam.model.Question;
import com.avisit.vijayam.model.Topic;
import com.avisit.vijayam.model.User;

@Component
@ManagedBean(name="contentProviderMBean")
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ContentProviderMBean implements Serializable{

	private static final long serialVersionUID = -1670860734020604646L;
	private ContentProvider contentProvider;
	private Course selectedCourse;
	private Topic selectedTopic;
	private Question selectedQuestion;
	private User selectedUser;
	private List<String> breadCrumbs;
	
	@PostConstruct
	public void init(){
		if(contentProvider == null){
			contentProvider = new ContentProvider();
		}
		if(breadCrumbs == null){
			breadCrumbs = new ArrayList<String>();
		}
	}

	public ContentProvider getContentProvider() {
		return contentProvider;
	}

	public void setContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
	
	public Course getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(Course selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	
	public Topic getSelectedTopic() {
		return selectedTopic;
	}

	public void setSelectedTopic(Topic selectedTopic) {
		this.selectedTopic = selectedTopic;
	}
	
	public Question getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<String> getBreadCrumbs() {
		return breadCrumbs;
	}

	public void setBreadCrumbs(List<String> breadCrumbs) {
		this.breadCrumbs = breadCrumbs;
	}
	
}
