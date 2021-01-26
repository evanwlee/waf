package com.evanwlee.web.management.command;


import javax.servlet.http.HttpServletRequest;


import com.evanwlee.management.HostQBE;
import com.evanwlee.persistence.PersistenceManager;
import com.evanwlee.persistence.model.GenericQBE;
import com.evanwlee.string.StringUtils;
import com.evanwlee.tokens.CmdReturnTokenImpl;
import com.evanwlee.tokens.IReturnToken;
import com.evanwlee.web.hos.command.ICommandable;

import com.evanwlee.web.view.View;



public class DeleteHost implements ICommandable  {
	public boolean canDoCommand(HttpServletRequest request) throws Exception {
		return true;//AppSession.validateSession(request);
	}

	public IReturnToken doCommand(HttpServletRequest request) throws Exception {
		CmdReturnTokenImpl results= new CmdReturnTokenImpl();
		
		deleteEntry( request.getParameter(View.HIDDEN_ROW_ID));
		results.setResult(request);
		return results;
	}

	
	public boolean deleteEntry( String id){

		if(StringUtils.isEmpty(id)){
			return false;
		}
		HostQBE qbe = new HostQBE();
		qbe.setAttribute(GenericQBE.ID_COLUMN, id);
		qbe.setAttribute(HostQBE.COLUMN_ACTIVE, "0");
		
		try{
			PersistenceManager.current("MYSQL").update(qbe);
			return true;
		}catch(Exception e){
		
		}
		
		return false;
	}
}
