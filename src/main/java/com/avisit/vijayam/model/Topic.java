package com.avisit.vijayam.model;

import java.util.Date;
import java.util.List;

public class Topic {
	private int id;
	private String name;
	private String description;
	private boolean enabledFlag;
	private int courseId;
	private int sortOrder;
	private Date createdTs;
	private Date lastModifiedTs;
	private List<Question> questions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getLastModifiedTs() {
		return lastModifiedTs;
	}

	public void setLastModifiedTs(Date lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
