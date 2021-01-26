package com.evanwlee.web.framework;

import org.apache.log4j.Logger;

import com.evanwlee.logging.LoggerFactory;
import com.evanwlee.management.ServerManager;
import com.evanwlee.properties.PropertyLoader;

import javax.servlet.ServletContext;


public class Style {
	public final static String STYLE_CONFIG = "/WEB-INF/configuration/style.config";
	public static String HTML_TABLE = "HTMLTable";
	
	private volatile static Style currentStyle= null;
	private static Logger log = LoggerFactory.getLogger(Style.class.getName());
	private String pathToSytleConfig = null;

	
	Style(String  pathToSytleConfig) {
		this.pathToSytleConfig = pathToSytleConfig;

	}
	/**
	 * Returns the current document type manager for the application.
	 */
	public static Style current(ServletContext context) {
		if (currentStyle == null) {
			synchronized (ServerManager.class) {
				if (currentStyle == null) {

					currentStyle = new Style(context.getRealPath(STYLE_CONFIG));
				}
			}
		}
		return currentStyle;
	}
	
	public String getClass(String key){
		String css_class = PropertyLoader.loadPropertyFromRealPath(key,pathToSytleConfig);
		log.debug(css_class);
		return css_class;
		
	}


	
	
}
