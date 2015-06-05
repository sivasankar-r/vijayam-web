package com.avisit.vijayam.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.common.Constant;
import com.avisit.vijayam.managed.ContentProviderMBean;
import com.avisit.vijayam.service.FileUploadService;

@Component
@ManagedBean(name = "questionUploadController")
@Scope(value="request")
public class QuestionUploadController {
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	@Autowired
	private QuestionController questionController;

	private Part filePart;

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
	}

	public Part getFilePart() {
		return filePart;
	}

	public void setFilePart(Part filePart) {
		this.filePart = filePart;
	}

	public String upload() {
		String toPage = "questionUpload";
		try {
			fileUploadService.uploadFile(filePart.getInputStream(), contentProviderMBean.getSelectedTopic());
			toPage = questionController.loadQuestions();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		return toPage;
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		Part file = (Part) value;
		if(file!=null){
			if (!Constant.EXCEL_CONTENTTYPE.equals(file.getContentType())) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uploaded file is not an .xls file. Download the template to upload the questions", null));
			}	
		} else {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please choose a file to upload", null));
		}
	}
}
