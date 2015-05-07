package com.avisit.vijayam.model;

public class Course {
	private int contentProviderId;
	private int courseId;
	private String name;
	private String description;
	private boolean enabledFlag;
	
	public int getContentProviderId() {
		return contentProviderId;
	}
	public void setContentProviderId(int contentProviderId) {
		this.contentProviderId = contentProviderId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(boolean enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
}
