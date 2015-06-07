package com.avisit.vijayam.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.avisit.vijayam.model.User;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String register(User user) {
		String response = "SUCCESS";
		int updateCount = 0;
		if(isContentProviderExist(user)){
			if(isUserExist(user)){
				updateCount = jdbcTemplate.update("UPDATE user SET deviceRegId=?, updatedTs=? WHERE email = ? AND contentProviderId = ? and active=1", new Object[] { user.getDeviceRegId(), new Timestamp(new Date().getTime()), user.getEmail(), user.getContentProviderId()});
				response = updateCount == 1 ? "SUCCESS" : "User not activiated by admin";
			} else {
				response = "Invalid email or password";
			}
		} else {
			response = "Institute code not found";
		}
		
		return response;
	}

	public boolean isUserExist(User user) {
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
		updateCount = jdbcTemplate.update("UPDATE user SET active=?, updatedTS=? WHERE deviceRegId = ?", new Object[]{0, new Timestamp(new Date().getTime()), user.getDeviceRegId()});
		return updateCount;
	}

	public List<User> fetchUsers(String contentProviderId) {
		String query = "SELECT * FROM user WHERE contentProviderId = ? ";
		List<User> userList = jdbcTemplate.query(query, new Object[]{contentProviderId}, new BeanPropertyRowMapper<User>(User.class));
		return userList;
	}

	public int insertUser(User user) {
		String insertQuery = "insert into user (contentProviderId, email, password, deviceRegId, active, createdTs, updatedTs) values (?,?,?,?,?,?,?)";
		int insertCount = jdbcTemplate.update(insertQuery, new Object[]{user.getContentProviderId(), user.getEmail(), user.getPassword(), user.getDeviceRegId(), user.isActive(), new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())});
		return insertCount;
	}

	public int toggleEnableFlag(User user) {
		int updateCount = 0; 
		if (user.getEmail() != null && !user.getEmail().isEmpty()) {
			updateCount = jdbcTemplate.update("update user SET active=?, updatedTs=? WHERE email=? and contentProviderId=?", new Object[] { !user.isActive(), new Date(), user.getEmail(), user.getContentProviderId() });
		}
		return updateCount;
	}

	public int editUser(User user) {
		int updateCount = 0; 
		if (user.getEmail() != null && !user.getEmail().isEmpty()) {
			updateCount = jdbcTemplate.update("update user SET password=?, deviceRegId=? WHERE email=? and contentProviderId=?", new Object[] {user.getPassword(), user.getDeviceRegId(), user.getEmail(), user.getContentProviderId() });
		}
		return updateCount;
	}

	public int deleteUser(User user) {
		return jdbcTemplate.update("delete from user where email = ? and contentProviderId = ?", new Object[]{user.getEmail(), user.getContentProviderId()});
	}
}
