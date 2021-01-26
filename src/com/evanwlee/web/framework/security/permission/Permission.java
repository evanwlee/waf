package com.evanwlee.web.framework.security.permission;

import com.evanwlee.web.framework.security.Role;

public abstract class Permission implements IPermission{
	
	Role role = Role.NO_ACCESS;
	
	public Permission(Role theRole){
		role = theRole;
	}

	public Role getRole() {
		return role;
	}
	/**
	 * @see com.evanwlee.web.framework.security.permission.IPermission#isEnabled()
	 */
	public boolean isEnabled() {
		 return false;
	}

	/**
	 * @see com.evanwlee.web.framework.security.permission.IPermission#isVisible()
	 */
	public boolean isVisible() {
		 return false;
	}
}
