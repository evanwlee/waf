package com.evanwlee.web.framework;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AppSession {

		private UserProfile myUserProfile;
		private HashMap attributes = new HashMap(10);
	    private String catalogName;
	    
	    public static final String USER = "user";
	    public static final String SERVER = "server";
	    public static final String MESSAGE_BOARD = "msgBoard";

	    /**
		 * The homeUrl can be set by the application to indicate
		 * what url to redirect to after the session times-out and
		 * the user performs some action.  If this is null, the
		 * framwork will determine the homeUrl from the http request
		 * received during login.  This should be sufficient for
		 * most applications.
		 */
		private String homeUrl;
		/**
		 * An application can set a url for the session server
		 * to use for communicating with the application. This
		 * server-server connection requires a different url than
		 * can be inferred from an http request when the application
		 * is behind an array device.  
		 */
		private String checkSessionUrl;
		private Vector coordinatorsToShutDown = new Vector(10);
		//holds TaskCoordinators that will be told to shutdown prior to GC

		/**
		 * The session's locale.
		 */
		public java.util.Locale locale;

		//flag used to indicate whether to update the timeout information
		//on the session server.
		private boolean updateTimeout = false;

		private boolean isNew;

		private boolean expired;

		private boolean overrideReadOnly = false;
		/**
		 *     zero-argument constructor - constructs anEISession object.
		 *
		 *     
		 */
		public AppSession() {
		}
		/**
		 * Constructor for an EISession that takes as it's single argument an
		 * EIUserProfile.
		 * @param aUserProfile the user profile.
		 */
		public AppSession(UserProfile aUserProfile) {
			myUserProfile = aUserProfile;
		}

		//	Holds current session in thread-local storage.
		private static InheritableThreadLocal threadSession =
			new InheritableThreadLocal();

		/**
		 * Return EISession instance previously stored for this thread
		 * @return EISession
		 */
		//public static EISession getThreadSession() {
			//EISession result = (EISession) threadSession.get();
			//return result;
		//}

		/**
		 * Set EISession instance for this thread.
		 * @param aSession
		 */
		//public static void setThreadSession(EISession aSession) {
			//threadSession.set(aSession);
		//}
		/**
		*   Add anEITaskCoordinator to my collection of coordinatorsToShutDown.
		*
		*   @param aCoord = anEITaskCoordinator.
		*/
		//public void addTaskCoordinatorToShutdownList(EITaskCoordinator aCoord) {
			///coordinatorsToShutDown.add(aCoord);
		//}
		/**
		 * Clear all attributes.
		 * Creation date: (8/14/02 4:31:40 PM)
		 * 
		 */
		public void clearAttributes() {

			attributes.clear();
		}
		/**
		 *   Returns an attribute value from the EISession.
		 *   Note that the implementation is not synchronized.
		 *
		 *   @param key a String that identifies the attribute.
		 *   @return the Object associated with the key.
		 */
		public Object getAttribute(String key) {
			return attributes.get(key);
		}
		/**
		 * Insert the method's description here.
		 * Creation date: (5/20/02 10:34:52 AM)
		 * 
		 * @return java.util.Locale
		 */
		public java.util.Locale getLocale() {

			if (locale == null)
				locale = Locale.getDefault();
			return locale;
		}
		/**
		 *    public getter method for my instance variable <myProfile>
		 *
		 *    @return UserProfile = myUserProfile
		 */
		public UserProfile getUserProfile() {
			return myUserProfile;
		}

		/**
		 * Answer an XML string that contains status information
		 * about this session.
		 * @return java.lang.String
		 */
		public String getStatusXML() {

			StringBuffer sb = new StringBuffer();
			sb.append("<EISESSION id=\"");
			sb.append(id);
			sb.append("\" timestamp=\"");
			sb.append(timeStamp == null ? 0 : timeStamp.getTime());
			sb.append("\" lastaction=\"");
			sb.append(lastAction);
	        sb.append("\" maxinactiveinterval=\"");
	        sb.append(maxInactiveInterval);
			sb.append("\"/>\n");
			return sb.toString();

		}

		/**
		 * Is this a new session?
		 * @return boolean
		*/
		public boolean isNew() {
			return isNew;
		}

		/**
		 * Set the state of this session.  Used by EIWSServlet to 
		 * determine how to redirect to the next context (either
		 * by response.redirect or via a dispatcher).
		 * @param b
		 */

		public void setIsNew(boolean b) {
			isNew = b;
		}
		/**
		 * Answer the value of overrideReadOnly.
		 * Creation date: (8/30/02 2:24:29 PM)
		 * 
		 * @return boolean
		 */
		public boolean isOverrideReadOnly() {
			return overrideReadOnly;
		}
		/**
		*   removes and returns an attribute value from the EISession based on
		 *   the key of the attribute.
		 *
		*   <b> Note that the implementation is not synchronized.</b>
		*
		*   @param key a String that identifies the attribute.
		*   @return the Object associated with the key.
		*/
		public Object removeAttribute(String key) {
			return attributes.remove(key);
		}

		/**
		 * Is this session authenticated?
		 * 
		 * @return boolean
		 */
		public boolean isAuthenticated() {
			return !(myUserProfile == null);
		}

		/**
		 *   Sets an attribute in the EISession. Note that the
		 *   implementation is not synchronized.
		 *
		 *   @param key a String that identifies the attribute.
		 *   @param value an Object to associate with the key.
		 */
		public void setAttribute(String key, Object value) {
			attributes.put(key, value);
		}



		/**
		 * Insert the method's description here.
		 * Creation date: (5/20/02 10:34:52 AM)
		 * 
		 * @param newLocale java.util.Locale
		 */
		public void setLocale(java.util.Locale newLocale) {
			locale = newLocale;
		}
		/**
		 * Set the value of overrideReadOnly.
		 * Creation date: (8/30/02 2:24:29 PM)
		 * 
		 * @param newOverrideReadOnly boolean
		 */
		public void setOverrideReadOnly(boolean newOverrideReadOnly) {
			overrideReadOnly = newOverrideReadOnly;
		}
		/**
		 *    public setter method for my instance variable <myProfile>
		 * @param aProfile a user profile.
		 *
		 */
		public void setUserProfile(UserProfile aProfile) {

			if ((myUserProfile != null) && (aProfile != null)) {
				if (!myUserProfile.getUserName().equals(aProfile.getUserName())) {
				}
			}
			myUserProfile = aProfile;
		}

		private java.lang.String id;
		private java.lang.String lastAction;
		private java.util.Date timeStamp;
		private int maxInactiveInterval = -1;

		/**
		 * Insert the method's description here.
		 * Creation date: (10/10/02 8:15:27 AM)
		 * @return java.lang.String
		 */
		public java.lang.String getId() {
			return id;
		} /**
					 * Insert the method's description here.
					 * Creation date: (10/9/02 4:19:32 PM)
					 * @return java.lang.String
					 */
		public java.lang.String getLastAction() {
			return lastAction;
		} /**
					 * Insert the method's description here.
					 * Creation date: (10/9/02 4:19:15 PM)
					 * @return java.util.Date
					 */
		public java.util.Date getTimeStamp() {
			return timeStamp;
		} /**
					 * Insert the method's description here.
					 * Creation date: (10/10/02 8:15:27 AM)
					 * @param newId java.lang.String
					 */
		public void setId(java.lang.String newId) {
			id = newId;
		} /**
					 * Insert the method's description here.
					 * Creation date: (10/9/02 4:19:32 PM)
					 * @param newLastAction java.lang.String
					 */
		public void setLastAction(java.lang.String newLastAction) {
			lastAction = newLastAction;
		} /**
					 * Insert the method's description here.
					 * Creation date: (10/9/02 4:19:15 PM)
					 * @param newTimeStamp java.util.Date
					 */
		public void setTimeStamp(java.util.Date newTimeStamp) {
			timeStamp = newTimeStamp;
		}
		/**
		 * Returns the maxInactiveInterval.
		 * @return int
		 */
		public int getMaxInactiveInterval() {
			return maxInactiveInterval;
		}

		/**
		 * Sets the maxInactiveInterval.  Set a flag for updating
		 * the timeout on the session server for app integration.
		 * @param maxInactiveInterval The maxInactiveInterval to set
		 */
		public void setMaxInactiveInterval(int maxInactiveInterval) {
			this.maxInactiveInterval = maxInactiveInterval;
			this.setUpdateTimeout(true);
		}

		/**
		 * Has this session expired?
		 * @return boolean
		*/
		public boolean isExpired() {
			return expired;
		}

		/**
		 * Sets the session to be expired.
		 * @param b boolean
		*/
		public void setExpired(boolean b) {
			expired = b;
		}

		/**
		 * Used by EIWSServlet to determine whether to update
		 * the session server information about this session's 
		 * timeout.
		 * @return boolean
		*/
		public boolean getUpdateTimeout() {
			return updateTimeout;
		}

		/**
		 * Set the updateTimeout attribute.
		 * @param b boolean
		*/
		public void setUpdateTimeout(boolean b) {
			updateTimeout = b;
		}


		/**
		 * @return String
		*/
		public String getCheckSessionUrl() {
			return checkSessionUrl;
		}

		/**
		 * @param string
		*/
		public void setCheckSessionUrl(String string) {
			checkSessionUrl = string;
		}

		/**
		 * @return String Returns the homeUrl.
		 */
		public String getHomeUrl() {
			return homeUrl;
		}
		/**
		 * @param homeUrl The homeUrl to set.
		 */
		public void setHomeUrl(String homeUrl) {
			this.homeUrl = homeUrl;
		}

	    /**
	     * @return Returns the name of the catalog XML file.
	     */
	    public String getCatalogName()
	    {
	        return catalogName;
	    }

	    /**
	     * @param catalogName the name of the catalog XML file.
	     */
	    public void setCatalogName(String catalogName)
	    {
	        this.catalogName = catalogName;
	    }
	    
	    public static boolean validateSession(HttpServletRequest request){
			   HttpSession session = request.getSession ();
			   if(session != null && session.getAttribute(AppSession.USER) != null){
				   return true;
			   }
			   
			   return false;
	    }
	}

