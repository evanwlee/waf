package com.evanwlee.web.framework.widget;

import com.evanwlee.web.framework.Action;
import com.evanwlee.web.framework.security.Role;
import com.evanwlee.web.framework.security.permission.IPermission;

public class SubmitButton extends SecureHtmlWidget{
	String formName = "";
	String action = "";
	String buttonID= "";
	String style= "";
	String styleClass= "";
	String label= "";
	IPermission permission;
	
	public SubmitButton(IPermission permission){
		this.permission =permission;
	}
	public String getFormName() {
		return formName;
	}


	public void setFormName(String formName) {
		this.formName = formName;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getButtonID() {
		return buttonID;
	}


	public void setButtonID(String buttonID) {
		this.buttonID = buttonID;
	}


	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = style;
	}


	public String getStyleClass() {
		return styleClass;
	}


	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String toString(){
		if(permission.isVisible()){
			if( permission.isEnabled()){
				return "<input type=\"submit\" onclick=\"this.disabled=true;submitForm(document."+formName+",'"+action+"',document."+formName+"."+Action.ACTION_ID+");\" name=\""+buttonID+"\" id=\""+buttonID+"\" class=\""+styleClass+"\" style=\""+style+"\" value=\""+label+"\"></input>";
			}else{
				return "NONO<input type=\"submit\" onclick=\"this.disabled=true;submitForm(document."+formName+",'"+action+"',document."+formName+"."+Action.ACTION_ID+");\" name=\""+buttonID+"\" id=\""+buttonID+"\" class=\""+styleClass+"\" style=\""+style+"\" value=\""+label+"\"></input>";
			}
		}
		return "";
	  }

}
