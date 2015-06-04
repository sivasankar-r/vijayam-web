package com.avisit.vijayam.managed;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.Metric;

@Component
@ManagedBean(name = "dashboardMBean")
public class DashboardMBean implements Serializable{
	
	private static final long serialVersionUID = 4671695153857560699L;
	private List<Metric> metricList;
	
	public void setMetricList(List<Metric> metricList) {
		this.metricList = metricList;
	}
	public List<Metric> getMetricList() {
		return metricList;
	}
	
}