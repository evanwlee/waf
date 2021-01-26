package com.evanwlee.web.framework.security;

public enum Role {
	NO_ACCESS(),
	MANAGER();
	
	public static Role determineRole(String roleValue){
		if(roleValue == null){
			return NO_ACCESS;
		}
		for (Role role : Role.values()){
    		if( role.toString().equalsIgnoreCase(roleValue)){
    			return role;
    		}
    	}
    	return NO_ACCESS;
		
	}
}
