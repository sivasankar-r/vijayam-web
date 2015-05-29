package com.avisit.vijayam.managed;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.ContentProvider;

@Component
@ManagedBean(name="contentProviderMBean")
@SessionScoped
public class ContentProviderMBean implements Serializable{

	private static final long serialVersionUID = -1670860734020604646L;
	private ContentProvider contentProvider;
	
	@PostConstruct
	public void init(){
		if(contentProvider==null){
			contentProvider = new ContentProvider();
		}
	}

	public ContentProvider getContentProvider() {
		return contentProvider;
	}

	public void setContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
	
}
