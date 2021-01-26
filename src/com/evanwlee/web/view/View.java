package com.evanwlee.web.view;


import javax.servlet.ServletContext;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.evanwlee.web.framework.Action;
import com.evanwlee.web.framework.UserProfile;
import com.evanwlee.web.framework.security.Role;
import com.evanwlee.web.framework.security.permission.AllowAccessPermission;
import com.evanwlee.web.framework.security.permission.DefaultAccessPermission;
import com.evanwlee.web.framework.security.permission.Permission;
import com.evanwlee.web.framework.widget.SubmitButton;
import com.evanwlee.web.hos.database.model.UserQBE;


public abstract class View {
	public static final String HIDDEN_ROW_ID = "ROW_ID";
	
	private Map<String, String[]> parameters = null;
	
	private ServletContext servletContext = null;
	

	  public String getValue(String key) {
		    if (key ==null || ("").equals(key.trim())) {
		      throw new IllegalArgumentException("key must not be null or empty");
		    }

		    if (parameters == null) {
		      parameters = new java.util.HashMap<String, String[]>();
		    }
		    Object param = null;
		    String[] value = null;
		    String result = "";
		    try{
		    	param = parameters.get(key);
		    	if( param instanceof String[]){
		    		value =(String[]) param;
		    		result = value[0];
		    	}else{
		    		result = (String)param;
		    	}
		    	if(result == null){
		    		result = "";
		    	}
		    	return result;
		    	 
		    }catch(Exception e){
		    	e.toString();
		    }
		    return "";
		  }

	@SuppressWarnings("unchecked")
	public void setRequestModel(HttpServletRequest request) {
	    this.parameters = request.getParameterMap();
	  }
	
	

	  /**
	   * Returns this view's form data
	   *
	   * @return Map
	   */
	  public Map<String, ?> getRequestModel() {
	    return parameters;
	  }
	  
	  public void setServletContext(ServletContext svcntx){
		  this.servletContext = svcntx;
	  }
	  

	  
	  public ServletContext getServletContext(){
		 return  this.servletContext;
	  }
	  
	  /**
	  *Render a generic submit button to post the form with action specifed.
	 * @param formName
	 * @param action
	 * @param buttonID
	 * @param style
	 * @param label
	 * @return
	 */
	public static String renderSubmitButton(String formName, String action, String buttonID, String style, String label){
	  	
	  	return "<button onclick=\"this.disabled=true;submitForm(document."+formName+",'"+action+"',document."+formName+"."+Action.ACTION_ID+");\" name=\""+buttonID+"\" id=\""+buttonID+"\" style=\""+style+"\">"+label+"</button>";
	  } 
	  
	  /**
	  *Render a generic submit button to post the form with action specified.
	 * @param formName
	 * @param action
	 * @param buttonID
	 * @param style
	 * @param styleClass
	 * @param label
	 * @return
	 */
	public static String renderSubmitButton(String formName, String action, String buttonID, String style, String styleClass, String label){
		return renderSubmitButton(formName,  action,  buttonID,  style,  styleClass,  label, null);
	}
	public static String renderSubmitButton(String formName, String action, String buttonID, String style, String styleClass, String label, UserProfile user){
	  	
		SubmitButton button = null;
		if( user == null){
			button = new SubmitButton(new DefaultAccessPermission(Role.NO_ACCESS));
		}else{
			button = new SubmitButton(new DefaultAccessPermission(user.getRole()));
		}
	  	button.setFormName(formName);
	  	button.setAction(action);
	  	button.setButtonID(buttonID);
	  	button.setStyle(style);
	  	button.setStyleClass(styleClass);
	  	button.setLabel(label);
	  	return button.toString();
	  }
	
	public static String renderTableRowDeleteButton(String formName, String deleteAction,String rowId, String buttonID, String style, String styleClass, String label){
	  	
	  	return "<button onclick=\"this.disabled=true;handleTableRowDeleteClick(document."+formName+",'"+deleteAction+"',document."+formName+"."+Action.ACTION_ID+",document."+formName+"."+View.HIDDEN_ROW_ID+",'"+rowId+"');\" name=\""+buttonID+"\" id=\""+buttonID+"\" class=\""+styleClass+"\" style=\""+style+"\">"+label+"</button>";
	  } 
}
