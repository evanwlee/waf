package com.evanwlee.web.management.view;


import org.apache.log4j.Logger;

import com.evanwlee.logging.LoggerFactory;
import com.evanwlee.management.ServerManager;
import com.evanwlee.management.ServiceWrapper;
import com.evanwlee.persistence.IWrapper;
import com.evanwlee.web.framework.Style;
import com.evanwlee.web.framework.components.HTMLTable;
import com.evanwlee.web.view.IView;
import com.evanwlee.web.view.View;
import com.evanwlee.network.URL;


public class ServiceView extends View implements IView{
	
	private static Logger log = LoggerFactory.getLogger(ServiceView.class.getName());
	
	private ServerManager serverManager = null;
	
	Style style = null;


	public String servicesStringList(){
		return servicesStringList("<br>");
	}
	public String servicesStringList(String delim){
		try{
			serverManager = ServerManager.current();
			java.util.List<IWrapper> services = serverManager.fetchServicesForHost(getValue("HOST_ID"));
			java.util.Iterator<IWrapper> serviceIterator = services.iterator();
			
			String result = "";
			while(serviceIterator.hasNext()){
				ServiceWrapper host = (ServiceWrapper)serviceIterator.next();
				result = result + delim + host.toString();
			}
			
			
			return result;
		}catch(Exception e){	
			log.error(e.toString());
		}
		return "";
	}
	
	public String editServicesList(){
		try{
			serverManager = ServerManager.current();
			java.util.List<IWrapper> services = serverManager.fetchServicesForHost(getValue("HOST_ID"));
			
			style =  Style.current(getServletContext()); 

			HTMLTable table = new HTMLTable(services); 
			
			//java.util.List<URL> urls =  serverManager.fetchURLsForHost(getValue("HOST_ID"));
			java.util.List<URL> urls =  serverManager.fetchContextsForHost(getValue("HOST_ID"));
			java.util.Iterator<URL> it = urls.iterator();
			String urlList = "";
			
			while(it.hasNext()){
				URL url = (URL)it.next();
				urlList = urlList +" <br><a href=\""+url.getAddress()+"/"+url.getContext().getContextPath()+"\">"+url.getName()+" - "+url.getContext().getContextPath()+"</a>";
			}
			
			return table.render(style.getClass(Style.HTML_TABLE)) +urlList ;
		}catch(Exception e){	
			log.error(e.toString());
		}
		return "";
	}
	
	public String serviceURLs(){
		try{
			serverManager = ServerManager.current();
			java.util.List<IWrapper> services = serverManager.fetchServicesForHost(getValue("HOST_ID"));
			
			style =  Style.current(getServletContext()); 

			HTMLTable table = new HTMLTable(services);
			
			return table.render(style.getClass(Style.HTML_TABLE));
		}catch(Exception e){	
			log.error(e.toString());
		}
		return "";
	}
	
	public java.util.List<IWrapper> getHosts(){
		try{
			serverManager = ServerManager.current();
			return serverManager.fetchAllHosts();
		}catch(Exception e){	
			log.error(e.toString());
		}
		return null;
	}
	
}
