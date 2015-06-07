package com.avisit.vijayam.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.ContentProviderMBean;
import com.avisit.vijayam.model.ContentProvider;
import com.avisit.vijayam.service.LoginService;

@Component
@ManagedBean
@Scope(value="request")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	
	@Autowired
	private LoginService loginService;

	private String loginMessage;
	
	@Autowired
	private DashboardController dashboardController;	
	
	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
	}
	
	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String login() throws Exception{
		String toPage = null;
		ContentProvider contentProvider = null;
		try {
			contentProvider = loginService.isValidUser(contentProviderMBean.getContentProvider().getUsername(), contentProviderMBean.getContentProvider().getPassword());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", contentProviderMBean.getContentProvider().getUsername());
			setLoginMessage(null);
		} catch (Exception e) {
			setLoginMessage(e.getMessage());
			logger.error(e.getMessage());
		}
		if(contentProvider!=null){
			contentProviderMBean.getContentProvider().setContentProviderId(contentProvider.getContentProviderId());
			contentProviderMBean.getContentProvider().setName(contentProvider.getName());
			contentProviderMBean.getContentProvider().setUsername(contentProvider.getUsername());
			toPage = dashboardController.loadDashboard();
		}		
		return toPage;
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		setLoginMessage("Signed out successfully");
		return "login";
	}
}
