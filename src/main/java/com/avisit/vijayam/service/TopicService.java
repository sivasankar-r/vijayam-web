package com.avisit.vijayam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.TopicDao;
import com.avisit.vijayam.model.Topic;

@Service
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

	public boolean toggleEnableFlag(Topic topic) {
		return topicDao.toggleEnableFlag(topic) == 1;
	}

	public boolean editTopic(Topic topic) {
		return topicDao.editTopic(topic) == 1;
	}

	public int deleteTopic(Topic topic) {
		return topicDao.deleteTopic(topic);
	}

	public boolean addNew(Topic topic) {
		return topicDao.insertTopic(topic) > 0;
	}
}
