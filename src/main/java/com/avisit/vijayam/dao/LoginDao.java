package com.avisit.vijayam.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.avisit.vijayam.model.ContentProvider;

@Repository
public class LoginDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public ContentProvider isValidUser(String username, String password) throws Exception{
		String query = "SELECT * FROM content_provider c WHERE c.username='"+username+"' AND c.password='"+password+"'";
		ContentProvider user = null;
		try{
			user = getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper<ContentProvider>(ContentProvider.class));
		}catch(DataAccessException e){
			if(e instanceof EmptyResultDataAccessException){
				throw new Exception("Invalid Username / Password", e);
			}else{
				throw e;
			}
		}
		return user;
	}

}
