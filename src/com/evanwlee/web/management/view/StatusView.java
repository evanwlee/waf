package com.evanwlee.web.management.view;



import java.util.HashMap;
import java.util.Map;

import com.evanwlee.logging.LoggerFactory;
import com.evanwlee.management.ServerManager;


import com.evanwlee.web.framework.Action;
import com.evanwlee.web.framework.Style;
import com.evanwlee.web.framework.UserProfile;
import com.evanwlee.web.framework.components.ColumnConfig;
import com.evanwlee.web.framework.components.EditableHTMLTable;
import com.evanwlee.web.framework.components.HTMLTable;
import com.evanwlee.web.framework.security.permission.ColumnPermission;
import com.evanwlee.web.view.IView;
import com.evanwlee.web.view.View;

import com.evanwlee.management.HostWrapper;

import com.evanwlee.persistence.IWrapper;
import com.evanwlee.persistence.constants.PersistenceConstants;
import com.evanwlee.string.StringUtils;




public class StatusView extends View implements IView{
	
	private static org.apache.log4j.Logger log = LoggerFactory.getLogger(ServiceView.class.getName());
	Style style = null;
	
	private ServerManager serverManager = null;

	public String serverStringList(String delim){
		try{
			serverManager = ServerManager.current();
			java.util.List<IWrapper> allHosts = serverManager.fetchAllHosts();
			
			java.util.Iterator<IWrapper> hostIterator = allHosts.iterator();
			
			String result = "";
			while(hostIterator.hasNext()){
				HostWrapper host = (HostWrapper)hostIterator.next();
				result = result + delim + host.toString();
			}
			
			 
			return result;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return "";
	}
	
	public String hosts(UserProfile user){
		try{
			serverManager = ServerManager.current();
			java.util.List<IWrapper> allHosts = serverManager.fetchAllHosts();
			
			style =  Style.current(getServletContext()); 

			EditableHTMLTable table = new EditableHTMLTable(allHosts);
			
			Map<String, ColumnConfig> colConfig = new HashMap<String, ColumnConfig>();
			
			ColumnConfig idColumn = new ColumnConfig();
			idColumn.setPrimaryKey(true);
			idColumn.setEditable(false);
			colConfig.put(PersistenceConstants.ID_COLUMN_NAME, idColumn);
			
			ColumnConfig createdByColum = new ColumnConfig();
			createdByColum.setPrimaryKey(false);
			createdByColum.setEditable(false);
			colConfig.put("CREATED_BY", createdByColum);
			
			return table.render(style.getClass(Style.HTML_TABLE),colConfig,new HostColumnOrder(), Action.SAVE_HOSTS,Action.DELETE_HOSTS,user);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return "";
	}


	
	public String serverStringList(){
		return serverStringList(",");
	}
	
	public String serverInfo(){
		try{
			serverManager = ServerManager.current();
			String hostId= getValue("HOST_ID");
			if(StringUtils.isEmpty(hostId)){
				return "";
			}
			HostWrapper host = serverManager.fetchHost( Integer.parseInt(getValue("HOST_ID")));
			
			if( host == null){
				return "";
			}
			return host.toString();
		}catch(Exception e){	
			log.warn(e.toString());
		}
		return "";
	}
	
	public String hostStatus(){
		try{
			serverManager = ServerManager.current();
			java.util.List<IWrapper> statuses = serverManager.fetchAllStatusesForHost(getValue("HOST_ID"));

			style =  Style.current(getServletContext()); 

			HTMLTable table = new HTMLTable(statuses);
			
			return table.render(style.getClass(Style.HTML_TABLE));
		}catch(Exception e){	
			log.error(e.toString());
		}
		return "";
	}
	
}
