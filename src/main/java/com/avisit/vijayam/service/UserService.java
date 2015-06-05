package com.avisit.vijayam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.UserDao;
import com.avisit.vijayam.model.User;

@Service
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
	
	public List<User> fetchUsers(String contentProviderId){
		return userDao.fetchUsers(contentProviderId);
	}

	public boolean addNew(User user) {
		return userDao.insertUser(user) > 0;
	}

	public boolean toggleEnableFlag(User user) {
		return userDao.toggleEnableFlag(user) == 1;
	}

	public boolean editUser(User user) {
		return userDao.editUser(user) > 0;
	}

	public int deleteUser(User user) {
		return userDao.deleteUser(user);
	}
	
	public boolean isValidUser(User user){
		return userDao.isUserExist(user);
	}
	
}
