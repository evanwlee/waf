package com.evanwlee.web.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


import com.evanwlee.logging.LoggerFactory;
import com.evanwlee.persistence.PersistenceManager;
import com.evanwlee.properties.PropertyLoader;
import com.evanwlee.reflection.Reflector;
import com.evanwlee.tokens.CmdReturnTokenImpl;
import com.evanwlee.tokens.IReturnToken;
import com.evanwlee.web.hos.command.ICommandable;
import com.evanwlee.web.view.IView;




public class Action  {


	 
	 private static Logger log = LoggerFactory.getLogger(Action.class.getName());
	private static  Properties actions  = null;
	
	//private static  HashMap<String,IView> views  = null;
	private static  Map<String,ArrayList<String>> viewMap  = java.util.Collections.synchronizedMap(new HashMap<String,ArrayList<String>>());
	private static  Map<String,ArrayList<String>> commandMap  = java.util.Collections.synchronizedMap(new HashMap<String,ArrayList<String>>());
	
	public static final String ACTION_ID = "ACTION";
	public static final String VIEW_ID = "VIEW_ID";
	
	
	public static String SHOW_STATUS = ACTION_ID+".SHOW_STATUS";
	public static String SHOW_HOSTS = ACTION_ID+".SHOW_HOSTS";
	public static String SAVE_HOSTS = ACTION_ID+".SAVE_HOSTS";
	public static String DELETE_HOSTS = ACTION_ID+".DELETE_HOSTS";
	public static String SHOW_SERVICES = ACTION_ID+".SHOW_SERVICES";
	public static String SHOW_OPPS = ACTION_ID+".SHOW_OPPS";
	
	public static String ADD_OPP = ACTION_ID+".ADD_OPP";
	public static String EDIT_OPP = ACTION_ID+".EDIT_OPP";
	public static String DELETE_OPP = ACTION_ID+".DELETE_OPP";
	public static String POST_EDITTED_OPP = ACTION_ID+".POST_EDITTED_OPP";
	
	
	public static String LOGIN = ACTION_ID+".LOGIN";
	public static String LOGIN_FAIL = ACTION_ID+".LOGIN.FAIL";
	public static String LOGOUT = ACTION_ID+".LOGOUT";

	public static String PREVIOUS_CONTEXT = ACTION_ID+".PREVIOUS_CONTEXT";
	public static String HOME = ACTION_ID+".HOME";
	public static String PORTAL_HOME = ACTION_ID+".PORTLET.HOME";
	public static String STATUS_HOME = ACTION_ID+".STATUS_HOME";
	public static String SHOW_MILES= ACTION_ID+".SHOW_MILES";
	public static String TRAINING_HOME = ACTION_ID+".TRAINING_HOME";
	
	public static String SAVE_MILES = ACTION_ID+".SAVE_MILES";
	
	public static final int DISPATCH =0;
	public static final int REDIRECT =1;

	public static String getContext(String key) {
    	if(!actionsLoaded ){
	    	try {
	    		actions = PropertyLoader.loadProperties(NAV_CONFIG);
	            actionsLoaded = true;
	        } catch (Exception e) {
	        	actionsLoaded = false;
	        }
    	}else{
    		actionsLoaded = true;
		}
    	
    	return actions.getProperty(key, "/index.jsp");
    }
	
	public static Map<String,ArrayList<String>> loadViews(String action) throws Exception{
		String viewKey = "VIEW."+action;
		
		try {
			Properties viewProperties = PropertyLoader.loadProperties(VIEW_CONFIG);
			int viewCount = Integer.parseInt(viewProperties.getProperty(viewKey+".COUNT","-1"));
				ArrayList<String> loadedViews = new ArrayList<String>();
				for(int i=0; i <viewCount;  i++){
					String viewName = viewProperties.getProperty(viewKey + "." + i );
					loadedViews.add(viewName);
					log.debug("Loading VIEW: "+viewName+" for ACTION: " + action);
				}
			
				viewMap.put(action, loadedViews);
	    } catch (Exception e) {
	        throw e;
	    }
    	
    	return viewMap;
    }
	
	public static Map<String,ArrayList<String>> loadCommands(String action) throws Exception{
		String viewKey = "COMMAND."+ action;
		
		try {
			Properties cmdProperties = PropertyLoader.loadProperties(ACTION_CONFIG);
			int cmdCount = Integer.parseInt(cmdProperties.getProperty(viewKey+".COUNT","-1"));
			
				ArrayList<String> loadedCommands = new ArrayList<String>();
				for(int i=0; i <cmdCount;  i++){
					String cmdname  = cmdProperties.getProperty(viewKey + "." + i );
					loadedCommands.add(cmdname);
					log.debug("Loading CMD: "+cmdname+" for ACTION: " + action);
				}
				commandMap.put(action, loadedCommands);

	    } catch (Exception e) {
	        throw e;
	    }
    	
    	return commandMap;
    }
	
	public static ArrayList<String> getViews(String action) throws Exception{
		if( !viewMap.containsKey(action)){
			loadViews(action);
		}
    	return viewMap.get(action);
    }
	
	public static ArrayList<String> getCommands(String action) throws Exception{
		if( !commandMap.containsKey(action)){
			loadCommands(action);
		}
		return commandMap.get(action);
    }
	
	public static HttpServletRequest setViewsOntoRequest(String action, HttpServletRequest request) throws Exception{
		
		ArrayList<String> currentViews = getViews(action);
		HttpSession session = request.getSession();
		
		for (Iterator<String> iterator = currentViews.iterator(); iterator.hasNext();) {
			String viewName = iterator.next();
			
			IView view = (IView)session.getAttribute(viewName);
			if(view == null){
				view = (IView)Reflector.createObject(viewName);
			}
			view.setRequestModel(request);
			view.setServletContext(request.getSession().getServletContext());
			session.setAttribute(view.getClass().getName(),  view);
			
		}

    	return request;
    }
	
	public static IReturnToken doAction(String action, HttpServletRequest request) throws Exception{
		ArrayList<String> currentCmds  = getCommands(action);
		IReturnToken token = null;;
		
		for (Iterator<String> iterator = currentCmds.iterator(); iterator.hasNext();) {
			String commandName = iterator.next();
			ICommandable command = (ICommandable)Reflector.createObject(commandName);
			if(command != null){
				if( command.canDoCommand(request)){
					log.debug("Doing CMD: "+commandName+" for ACTION: " + action);
					token = command.doCommand(request);
					if( token.isFailure()){
						break;
					}
				}
				
			}
		}
		if( token == null){
			CmdReturnTokenImpl results= new CmdReturnTokenImpl();
			results.setNextAction(action);
			results.setResult(request);
			token = results;
		}
		

    	
    	return token;
    }
	


    
	


	private static final String NAV_CONFIG = new String("resources.nav.properties");
    private static final String ACTION_CONFIG = new String("resources.action.properties");
    private static final String VIEW_CONFIG = new String("resources.view.properties");
    
    private static boolean actionsLoaded = false;

   
}
