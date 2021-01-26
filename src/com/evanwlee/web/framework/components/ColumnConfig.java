package com.evanwlee.web.framework.components;

import com.evanwlee.web.framework.security.permission.IPermission;

public class ColumnConfig {
	private boolean editable = true;
	private boolean visible = true;
	private String displayName = "";
	private boolean isPrimaryKey = false;
	private IPermission permission = null;
	
	
	public IPermission getPermission() {
		return permission;
	}
	public void setPermission(IPermission permission) {
		this.permission = permission;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public boolean isEditable() {
		if( permission!= null)
			return permission.isEnabled();
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isVisible() {
		if( permission!= null)
			return permission.isVisible();
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public static boolean isConfigDefined(ColumnConfig currentConfig){
		if(currentConfig == null){
			return false;
		}

		return true;
	}

}
