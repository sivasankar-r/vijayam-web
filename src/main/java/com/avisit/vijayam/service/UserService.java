package com.avisit.vijayam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.dao.UserDao;
import com.avisit.vijayam.model.User;

@Component
public class UserService {
	@Autowired
	private UserDao userDao;

	public String register(User user) {
		return userDao.register(user);
	}

	public boolean deactivateDevice(User user) {
		boolean success = false;
		int updateCount = userDao.deactivateDevice(user);
		if(updateCount == 1){
			success = true;
		}
		return success;
	} 
	
	
}
