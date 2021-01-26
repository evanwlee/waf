package com.evanwlee.web.framework.security.permission;

import com.evanwlee.web.framework.security.Role;

public class DefaultAccessPermission extends Permission {

	public DefaultAccessPermission(Role theRole) {
		super(theRole);
	}

	public boolean isEnabled() {
		 switch (getRole()) {
		 	case MANAGER:  return true;
		 	default: return false;

		 }
	}

	public boolean isVisible() {
		 switch (getRole()) {
		 	case MANAGER:  return true;
		 	default: return false;

		 }
	}
}
