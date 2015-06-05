package com.avisit.vijayam.managed;

import javax.faces.bean.ManagedBean;

import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.User;

@Component
@ManagedBean(name = "usersMBean")
public class UsersMBean {
	private RepeatPaginator<User> paginator;
	
	public RepeatPaginator<User> getPaginator() {
		return paginator;
	}

	public void setPaginator(RepeatPaginator<User> paginator) {
		this.paginator = paginator;
	}

	
}
