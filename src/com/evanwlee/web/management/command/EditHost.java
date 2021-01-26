package com.evanwlee.web.management.command;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.evanwlee.date.DateUtils;
import com.evanwlee.management.HostQBE;
import com.evanwlee.persistence.model.GenericQBE;
import com.evanwlee.web.framework.commands.EditTableCmd;



public class EditHost extends EditTableCmd {
	@Override
	public boolean canDoCommand(HttpServletRequest request) throws Exception {
		return true;//AppSession.validateSession(request);
	}

	@Override
	public String getDatabaseId() {
		return "MYSQL";
	}

	@Override
	public GenericQBE getQbeInstance() {
		return new HostQBE();
	}

	@Override
	public Object translateType(String columnName, String value)
			throws Exception {
		if("ACTIVE".equalsIgnoreCase(columnName)){
			int returnVal = 0;
			try{
				returnVal =Integer.parseInt(value);
			}catch(Exception e){}
			return returnVal;
		}
		if("CREATION_DATE".equalsIgnoreCase(columnName)){
			Date returnVal = new Date();
			try{
				if(value != null || (!"".equals(value.trim()))){
					returnVal =DateUtils.dateFromString(value);
				}
				
			}catch(Exception e){}
			return returnVal;
		}
		return value;
	}

}
