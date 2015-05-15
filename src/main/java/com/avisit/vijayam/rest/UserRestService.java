package com.avisit.vijayam.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

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
		try{
			if(user!=null){
				response = userService.register(user);
			}
		} catch (Exception e){
			response = e.getMessage();
			e.printStackTrace();
		}
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/register")
	public String register(MultivaluedMap<String, String> params){
		String response = "Null input received. Failed to register";
		try{
			if(params!=null && !params.isEmpty()){
				User user = new User();
				user.setContentProviderId(params.getFirst("contentProviderId"));
				user.setEmail(params.getFirst("email"));
				user.setPassword(params.getFirst("password"));
				user.setRegistrationId(params.getFirst("registrationId"));
				
				response = userService.register(user);
			}
		} catch (Exception e){
			response = e.getMessage();
			e.printStackTrace();
		}
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/deRegisterDevice")
	public String deactivateDeviceRegId(User user){
		String response = "FAILED";
		try{
			if(user!=null){
				if(userService.deactivateDevice(user)){
					response = "SUCCESS";
				}
			}
		} catch (Exception e){
			response = e.getMessage();
			e.printStackTrace();
		}
		return response;
	}
}
