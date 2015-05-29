package com.avisit.vijayam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.LoginDao;
import com.avisit.vijayam.model.ContentProvider;

@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public ContentProvider isValidUser(String username, String password) throws Exception{
		ContentProvider contentProvider = null;
		try {
			contentProvider = loginDao.isValidUser(username, password);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new Exception("Unknown Exception Occurred. Contact Administrator.", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return contentProvider;
	}
	
	
}
