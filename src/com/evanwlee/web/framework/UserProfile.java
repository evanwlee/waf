package com.evanwlee.web.framework;

import com.evanwlee.web.framework.security.Role;
import com.evanwlee.web.hos.database.model.UserQBE;

public class UserProfile {
	private UserQBE user = null;
	
	public UserProfile(UserQBE thesUser){
		user = thesUser;
	}
	
	public String getUserName(){
		if(user == null || user.getUserName() == null){
			return "";
		}
		return user.getUserName();
	}
	
	
	public UserQBE getUserQbe(){
		return user;
	}
	
	public Role getRole(){
		return Role.determineRole(user.getRole());
	}
	


}
