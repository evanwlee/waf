package com.evanwlee.web.framework.commands;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.evanwlee.management.HostQBE;
import com.evanwlee.persistence.PersistenceManager;
import com.evanwlee.persistence.model.GenericQBE;
import com.evanwlee.string.StringUtils;
import com.evanwlee.tokens.CmdReturnTokenImpl;
import com.evanwlee.tokens.IReturnToken;
import com.evanwlee.web.framework.Action;
import com.evanwlee.web.framework.AppSession;
import com.evanwlee.web.framework.UserProfile;
import com.evanwlee.web.hos.OppManager;
import com.evanwlee.web.hos.command.ICommandable;
import com.evanwlee.web.hos.view.OpportunityView;
import com.evanwlee.web.hos.view.PagingOpportunityView;


public abstract class EditTableCmd implements ICommandable {

	public IReturnToken doCommand(HttpServletRequest request)
			throws Exception {
		CmdReturnTokenImpl results= new CmdReturnTokenImpl();

		Enumeration paramsNames = request.getParameterNames();
		
		HashMap<String, GenericQBE> rowData = new HashMap<String, GenericQBE>();
		
		while(paramsNames.hasMoreElements()){
			String name = (String)paramsNames.nextElement();
			
			boolean isRowElement = name.indexOf("__")> -1;
			if( isRowElement){
				String index = name.substring(name.indexOf("__")+2);
				GenericQBE qbe = rowData.get(index);

				if(qbe == null){
					qbe = getQbeInstance();
				}
				
				String column = name.substring(0,name.indexOf("__"));
				String newValue = request.getParameter(name);
				//If all values are blank don't try to enter the row
				if(!StringUtils.isEmpty(newValue)){
					qbe.setHasAttribute(true);
				}
				qbe.setAttribute(column,translateType(column, request.getParameter(name)));
				rowData.put(index, qbe);
			}
		}
		
	    Collection c = rowData.values();     //obtain an Iterator for Collection    
	    Iterator itr = c.iterator();     //iterate through HashMap values iterator    
	    while(itr.hasNext()){
	    	GenericQBE qbe = (GenericQBE)itr.next();
	    	if(qbe.isAttributeSet()){
	    		PersistenceManager.current(getDatabaseId()).update(qbe);
	    	}else{
	    		//skip
	    	}
	    }
		results.setResult(request);
		return results;
	}
	
	public abstract Object translateType(String columnName, String value) throws Exception;
	public abstract boolean canDoCommand(HttpServletRequest request) throws Exception;
	public abstract GenericQBE getQbeInstance();
	/**
	 * This method is specifies which unique databse ID to use
	 * to get the persistence properties to save the QBE
	 * 
	 * @return string Unique DB ID corresponding to value
	 * in persistence.properties
	 */
	public abstract String getDatabaseId();

}
