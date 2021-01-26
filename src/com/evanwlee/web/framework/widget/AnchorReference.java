package com.evanwlee.web.framework.widget;

import com.evanwlee.web.framework.security.permission.IPermission;


public class AnchorReference{
	
	private String url = "";
	private String displayedLink = "";
	
	public AnchorReference(String url, String displayedLink){
		this.url = url;
		this.displayedLink = displayedLink;
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
		return "<a href=\""+url+"\">"+displayedLink+"</a>";

	}
}
