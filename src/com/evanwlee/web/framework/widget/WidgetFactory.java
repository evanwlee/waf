package com.evanwlee.web.framework.widget;

import com.evanwlee.web.framework.security.permission.IPermission;

public class WidgetFactory {
	
	
	/**
	 * Create an 'a href' that is not controlled by security
	 * 
	 * @param url
	 * @param displayedValue
	 * @return
	 */
	public static AnchorReference createAnchorRef(String url, String displayedValue){
		return new AnchorReference(url,displayedValue); 
	}
	
	/**
	 ** Create an 'a href' that is controlled by security
	 * @param url
	 * @param displayedValue
	 * @param permission
	 * @return
	 */
	public static SecureAnchorReference createAnchorRef(String url, String displayedValue, IPermission permission){
		return new SecureAnchorReference(url,displayedValue,permission); 
	}

}
