package com.avisit.vijayam.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.BreadCrumbMBean;
import com.avisit.vijayam.managed.ContentProviderMBean;
import com.avisit.vijayam.managed.DashboardMBean;
import com.avisit.vijayam.service.DashboardService;

@Component
@ManagedBean
@RequestScoped
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private DashboardMBean dashboardMBean;
	
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	
	@Autowired
	private BreadCrumbMBean breadCrumbMBean;
	
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
	
	public void setBreadCrumbMBean(BreadCrumbMBean breadCrumbMBean) {
		this.breadCrumbMBean = breadCrumbMBean;
	}

	public BreadCrumbMBean getBreadCrumbMBean() {
		return breadCrumbMBean;
	}

	public String loadDashboard() throws Exception{
		dashboardMBean.setMetricList(dashboardService.getDashboardMetrics(contentProviderMBean.getContentProvider().getContentProviderId()));
		breadCrumbMBean.resetAll();
		return "dashboard";
	}

}