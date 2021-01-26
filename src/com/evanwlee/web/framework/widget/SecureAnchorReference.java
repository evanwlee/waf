package com.evanwlee.web.framework.widget;

import com.evanwlee.web.framework.security.permission.IPermission;


public class SecureAnchorReference extends SecureHtmlWidget{
	
	private String url = "";
	private String displayedLink = "";
	private IPermission permission = null;
	
	public SecureAnchorReference(String url, String displayedLink, IPermission permission){
		this.url = url;
		this.displayedLink = displayedLink;
		this.permission = permission;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDisplayedLink() {
		return displayedLink;
	}

	public void setDisplayedLink(String displayedLink) {
		this.displayedLink = displayedLink;
	}

	public String toString(){
		if(permission.isVisible()){
			if(permission.isEnabled()){
				return "<a href=\""+url+"\">"+displayedLink+"</a>";
			}else{
				return "<font style=\"font: normal x-small tahoma, helvetica, sans-serif;color: #3E2B07;\">"+displayedLink+"</font>";
			}
		}else{
			return "";
		}
	}

}
