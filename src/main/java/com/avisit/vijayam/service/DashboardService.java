package com.avisit.vijayam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.avisit.vijayam.model.Metric;

@Service
public class DashboardService {
	public static final Logger logger = Logger.getLogger("QuestionService");

	public List<Metric> getDashboardMetrics(String contentProviderId) throws Exception {
		List<Metric> metricList = new ArrayList<Metric>();
		//metricList.add(new Metric("Total Courses", String.valueOf(courseDao.getCourseCount(contentProviderId))));
		//metricList.add(new Metric("Total Topics", String.valueOf(topicDao.getActiveTopicCountByProvider(contentProviderId))));
		return metricList;
	}
}
