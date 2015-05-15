package com.avisit.vijayam.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.User;

@Component
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String register(User user) {
		String response = "SUCCESS";
		int updateCount = 0;
		if(isContentProviderExist(user)){
			if(isUserExist(user)){
				updateCount = jdbcTemplate.update("UPDATE user SET deviceRegId=?, active=?, createdTS=? WHERE email = ? AND contentProviderId = ?", new Object[] { user.getRegistrationId(), 1, new Timestamp(new Date().getTime()), user.getEmail(), user.getContentProviderId()});
				response = updateCount == 1 ? "SUCCESS" : "Failed to register";
			} else {
				response = "Invalid email or password";
			}
		} else {
			response = "Institute code not found";
		}
		
		return response;
	}

	private boolean isUserExist(User user) {
		String query = "SELECT COUNT(*) FROM user WHERE contentProviderId = ? AND email = ? AND password = ?";
		int count = jdbcTemplate.queryForObject(query, new Object[] { user.getContentProviderId(), user.getEmail(), user.getPassword() }, Integer.class);
		return count == 1? true : false;
	}

	private boolean isContentProviderExist(User user) {
		String query = "SELECT COUNT(*) FROM content_provider WHERE contentProviderId = ?";
		int count = jdbcTemplate.queryForObject(query, new Object[] { user.getContentProviderId() }, Integer.class);
		return count == 1? true : false;
	}

	public int deactivateDevice(User user) {
		int updateCount = 0;
		updateCount = jdbcTemplate.update("UPDATE user SET active=?, updatedTS=? WHERE deviceRegId = ?", new Object[]{0, new Timestamp(new Date().getTime()), user.getRegistrationId()});
		return updateCount;
	}
}
