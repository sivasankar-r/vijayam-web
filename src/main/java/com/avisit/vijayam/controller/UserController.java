package com.avisit.vijayam.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.avisit.vijayam.managed.ContentProviderMBean;
import com.avisit.vijayam.managed.RepeatPaginator;
import com.avisit.vijayam.managed.UsersMBean;
import com.avisit.vijayam.model.User;
import com.avisit.vijayam.service.UserService;

@Component
@ManagedBean(name="userController")
@Scope(value="request")
public class UserController {
	private User newUser;
	private String message;
	@Autowired
	private UserService userService;
	@Autowired
	private ContentProviderMBean contentProviderMBean;
	@Autowired
	private UsersMBean usersMBean;

	@PostConstruct
	public void init() {
		newUser = new User();
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ContentProviderMBean getContentProviderMBean() {
		return contentProviderMBean;
	}

	public void setContentProviderMBean(ContentProviderMBean contentProviderMBean) {
		this.contentProviderMBean = contentProviderMBean;
	}

	public UsersMBean getUsersMBean() {
		return usersMBean;
	}

	public void setUsersMBean(UsersMBean usersMBean) {
		this.usersMBean = usersMBean;
	}

	public String loadUsers() {
		message = null;
		contentProviderMBean.setSelectedUser(new User());
		contentProviderMBean.getBreadCrumbs().clear();
		contentProviderMBean.getBreadCrumbs().add("All Users");
		usersMBean.setPaginator(new RepeatPaginator<User>(userService.fetchUsers(contentProviderMBean.getContentProvider().getContentProviderId())));
		return "users";
	}

	public void addUser() {
		boolean success = false;
		if (newUser != null) {
			newUser.setContentProviderId(contentProviderMBean.getContentProvider().getContentProviderId());
			newUser.setDeviceRegId("default");
			success = userService.addNew(newUser);
		}
		if (success) {
			loadUsers();
			setMessage("Info : New user added Successfully");
		} else {
			setMessage("Error : Failed to add new user");
		}
	}
	
	public void toggleEnableFlag() {
		if(contentProviderMBean.getSelectedUser()!=null){
			if(userService.toggleEnableFlag(contentProviderMBean.getSelectedUser())){
				message = contentProviderMBean.getSelectedUser().isActive() ? "Info : User disabled" : "Info : User enabled" ;
				loadUsers();
			} else {
				message = "Error : Failed to toggle the user enable / disable";
			}
		}
	}
	
	public void editUser() {
		if(contentProviderMBean.getSelectedUser()!=null){
			if(userService.editUser(contentProviderMBean.getSelectedUser())){
				loadUsers();
				message = "Info : User updated successfully";
			} else {
				message = "Error : Failed to update the user";
			}
		}
	}
	
	public void deleteUser() {
		if(contentProviderMBean.getSelectedUser()!=null){
			userService.deleteUser(contentProviderMBean.getSelectedUser());
			loadUsers();
		}
	}
}
