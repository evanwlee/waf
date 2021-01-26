package com.evanwlee.web.framework.security.permission;

import com.evanwlee.web.framework.security.Role;

public class AllowAccessPermission extends Permission {

	public AllowAccessPermission() {
		super(Role.MANAGER);
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isVisible() {
		return true;
	}
}
