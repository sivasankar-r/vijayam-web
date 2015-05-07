package com.avisit.vijayam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.dao.ExamDao;
import com.avisit.vijayam.model.Exam;

@Component
public class ExamService {
	
	@Autowired
	private ExamDao examDao; 
	
	public List<Exam> fetchAllExams() {
		List<Exam> examList = null;
		examList = examDao.getAllExams();
		return examList;
	}
}
