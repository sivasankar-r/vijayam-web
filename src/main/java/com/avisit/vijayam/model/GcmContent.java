package com.avisit.vijayam.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GcmContent implements Serializable {
	private static final long serialVersionUID = 8199928423774681283L;
    private Map<String,String> data;
	private List<String> registration_ids;

    
    public void addRegId(String regId){
        if(getRegistration_ids() == null) {
        	setRegistration_ids(new LinkedList<String>());
        }
        getRegistration_ids().add(regId);
    }

    public void createData(String title, String message){
        if(getData() == null) {
            setData(new HashMap<String,String>());
        }
        getData().put("title", title);
        getData().put("message", message);
    }

	public Map<String,String> getData() {
		return data;
	}

	public void setData(Map<String,String> data) {
		this.data = data;
	}

	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}
}
