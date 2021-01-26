
<%@page import="com.evanwlee.web.hos.database.model.UserQBE"%>
<%@page import="com.evanwlee.web.framework.security.Role"%>
<%@page import="com.evanwlee.web.framework.UserProfile"%>
<%@page import="com.evanwlee.web.framework.AppSession"%>  


<% UserProfile user = getUserProfile(request);%>


<%!

public UserProfile getUserProfile(HttpServletRequest request){
	HttpSession session = request.getSession (false);
	UserProfile user = null;
	if( session != null ){
		user = (UserProfile)session.getAttribute(AppSession.USER);
	}
	if( user == null ){
		user = new UserProfile(new UserQBE());
	}

	return user;
} 

%>