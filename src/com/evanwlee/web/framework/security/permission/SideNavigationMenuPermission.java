package com.evanwlee.web.framework.security.permission;

import com.evanwlee.web.framework.security.Role;

public class SideNavigationMenuPermission extends Permission {

	public SideNavigationMenuPermission(Role theRole) {
		super(theRole);
	}

	public boolean isEnabled() {
		 switch (getRole()) {
		 	case MANAGER:  return true;
		 	default: return false;

		 }
	}

	public boolean isVisible() {
		return true;
		/* switch (getRole()) {
		 	case MANAGER:  return true;
		 	default: return false;

		 }*/
	}
}
