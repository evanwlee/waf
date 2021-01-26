package com.evanwlee.web.framework.security.permission;

public interface IPermission {
	
	
	/**
	 * Default is restrictive so it will should return false
	 * unless specifically implemented for the cases to return true.
	 * 
	 * @return true if the element should be visible.
	 */
	public boolean isVisible();
	
	/**
	 * Default is restrictive so it will should return false
	 * unless specifically implemented for the cases to return true.
	 * 
	 * @return true if the element should be enabled. If the items is not
	 * visible then this setting really shouldn't matter.
	 */
	public boolean isEnabled();

}
