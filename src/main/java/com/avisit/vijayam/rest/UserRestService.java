package com.avisit.vijayam.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.model.User;
import com.avisit.vijayam.service.UserService;

@Component
@Path("/user")
public class UserRestService {
	
	@Autowired
	private UserService userService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/register")
	public String register(User user){
		String response = "Null input received. Failed to register";
		if(user!=null){
			response = userService.register(user);
		}
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deRegisterDevice")
	public String deactivateDeviceRegId(User user){
		String response = "FAILED";
		if(user!=null){
			if(userService.deactivateDevice(user)){
				response = "SUCCESS";
			}
		}
		return response;
	}
}
