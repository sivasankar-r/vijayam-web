package com.avisit.vijayam.managed;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Exam;
import com.avisit.vijayam.service.ExamService;

@Component
@ManagedBean(name = "examsMBean")
@SessionScoped
public class ExamsMBean implements Serializable{
	
	private static final long serialVersionUID = 3485937020246662440L;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	
	private List<Exam> examList;

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public ExamService getExamService() {
		return examService;
	}

	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}

	public List<Exam> getExamList() {
		return examList;
	}
	
	public String loadExams(){
		String clientId = contentProviderMBean.getContentProvider().getContentProviderId();
		examList = examService.fetchExamsByClientId(clientId);
		return "showExams";
	}
}
