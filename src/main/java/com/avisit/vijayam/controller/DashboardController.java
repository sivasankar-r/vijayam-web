package com.avisit.vijayam.controller;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.DashboardMBean;
import com.avisit.vijayam.managed.UserMBean;
import com.avisit.vijayam.service.DashboardService;

@Component
@ManagedBean
@Scope(value="request")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private DashboardMBean dashboardMBean;
	
	@Autowired
	private UserMBean userMBean;
	
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardMBean(DashboardMBean dashboardMBean) {
		this.dashboardMBean = dashboardMBean;
	}

	public DashboardMBean getDashboardMBean() {
		return dashboardMBean;
	}

	public void setUserMBean(UserMBean userMBean) {
		this.userMBean = userMBean;
	}

	public UserMBean getUserMBean() {
		return userMBean;
	}
	
	public String loadDashboard() throws Exception{
		dashboardMBean.setMetricList(dashboardService.getDashboardMetrics(userMBean.getContentProvider().getContentProviderId()));
		return "dashboard";
	}

}