package com.evanwlee.tokens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.evanwlee.string.StringUtils;
import com.evanwlee.web.framework.Action;


public class CmdReturnTokenImpl extends ReturnTokenImpl implements IReturnToken{
	
	Object result = null;
	String nextAction = "";
	boolean failed = false;
	
	List<IStatusMessage> errorMessage = new ArrayList<IStatusMessage>();
	List<IStatusMessage> warningMessage = new ArrayList<IStatusMessage>();
	List<IStatusMessage> infoMessage = new ArrayList<IStatusMessage>();

	public String getNextAction(){
		if( StringUtils.isEmpty(nextAction)){
			if( result != null){
				return ((HttpServletRequest)result).getParameter(Action.ACTION_ID);
			}else{
				return nextAction;
			}
		}
		return nextAction;
		
	}
	
	public void setNextAction(String next){
		nextAction = next;
	}


}
