package com.avisit.vijayam.model;

import java.io.Serializable;

public class ContentProvider implements Serializable{

	private static final long serialVersionUID = -5928842318010424718L;
	private String contentProviderId;
	private String name;
	private String username;
	private String password;
	
	public String getContentProviderId() {
		return contentProviderId;
	}
	public void setContentProviderId(String clientId) {
		this.contentProviderId = clientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
