package com.avisit.vijayam.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.dao.LoginDao;
import com.avisit.vijayam.model.ContentProvider;

@Service
public class LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
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
			logger.info("*********************** Login Successful........");
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("Unknown Exception Occurred. Contact Administrator.", e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
		return contentProvider;
	}
	
	
}
