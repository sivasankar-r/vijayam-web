package com.avisit.vijayam.controller;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.DashboardMBean;
import com.avisit.vijayam.managed.ContentProviderMBean;
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
	private ContentProviderMBean contentProviderMBean;
	
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

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
	}

	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}
	
	public String loadDashboard() throws Exception{
		dashboardMBean.setMetricList(dashboardService.getDashboardMetrics(contentProviderMBean.getContentProvider().getContentProviderId()));
		return "dashboard";
	}

}