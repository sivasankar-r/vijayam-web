package com.avisit.vijayam.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Topic;

@Component
@ManagedBean(name = "topicsMBean")
@RequestScoped
public class TopicsMBean {
	private RepeatPaginator<Topic> paginator;
	
	public RepeatPaginator<Topic> getPaginator() {
		return paginator;
	}

	public void setPaginator(RepeatPaginator<Topic> paginator) {
		this.paginator = paginator;
	}

	
}
