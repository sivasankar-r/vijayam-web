package com.avisit.vijayam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.dao.TopicDao;
import com.avisit.vijayam.model.Topic;

@Component
public class TopicService {
	
	@Autowired
	private TopicDao topicDao; 
	
	public List<Topic> fetchAllTopics() {
		List<Topic> topicList = null;
		topicList = topicDao.getAllTopics();
		return topicList;
	}
	
	public List<Topic> fetchTopicsByCourseId(int courseId){
		List<Topic> topicList = new ArrayList<Topic>();
		if(courseId > 0){
			topicList = topicDao.getTopicsByCourseId(courseId);	
		}
		return topicList;
	}
}
