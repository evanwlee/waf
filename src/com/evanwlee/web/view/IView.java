package com.evanwlee.web.view;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface IView {
	  public void setRequestModel(HttpServletRequest request) ;
	  
	  public void setServletContext(ServletContext svcntx);
	  public ServletContext getServletContext();
}
