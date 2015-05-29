package com.avisit.vijayam.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.ExamDao;
import com.avisit.vijayam.dao.QuestionDao;
import com.avisit.vijayam.model.Exam;

@Service
public class ExamService {
	
	@Autowired
	private ExamDao examDao;
	@Autowired
	private QuestionDao questionDao;
	public static final Logger logger = Logger.getLogger("ExamService");
	
	public List<Exam> fetchAllExams() {
		List<Exam> examList = null;
		examList = examDao.getAllExams();
		return examList;
	}
	
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}
	
	public List<Exam> fetchExamsByClientId(String contentProviderId) {
		return examDao.getExamsByContentProvider(contentProviderId);
	}
	
	public Exam fetchExamDetailById(int examId) {
		Exam exam = null;
		exam = examDao.getExamDetailById(examId);
		if(exam!=null){
			exam.setQuestionCount(questionDao.fetchQuestionCount(exam.getExamId()));
		}
		return exam;
	}

	public Exam fetchExamById(int examId) {
		Exam exam = fetchExamDetailById(examId);
		if(exam!=null){
			exam.setQuestionList(questionDao.fetchQuestions(exam.getExamId()));
		}
		return exam;
	}

	public int persistExam(Exam exam) throws Exception {
		int examId = 0;
		if(exam!=null){
			try {
				examId = examDao.createAndReturnExamId(exam);
				logger.log(Level.INFO, "ExamId : "+examId+" added successfully");	
			} catch (SQLException e) {
				throw new Exception("Exception occurred in creating exam. Verify the Exam sheet in the uploaded workbook", e);
			}
		}
		return examId;
	}

	public void mapExamToClient(int examId, String contentProviderId) {
		int rowsUpdated = 0;
		try {
			rowsUpdated = examDao.mapExamToClient(examId, contentProviderId);
			if(rowsUpdated<=0){
				throw new Exception("Mapping Exam to ContentProvider failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
