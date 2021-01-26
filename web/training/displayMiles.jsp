<%@ include file="../inc/jspf/header.jspf" %>
<%@ include file="../inc/headerBar.jsp" %>
<%@page import="com.evanwlee.web.training.view.MileView"%>
<%@ page import="com.evanwlee.persistence.IWrapper" %>
<%@ page import="com.evanwlee.management.HostWrapper" %>
<%
//Need to add view config to view.properties to associated the view with the action so that it
//will be set-up and returned correctly.

MileView mileView = (MileView)request.getSession().getAttribute(MileView.class.getName());


%>
	
<body>
		 <%=mileView.displayMilesTable()%>

		<a href="/waf/main?ACTION=<%=Action.HOME%>">Main Page</a>||<a href="/waf/main?ACTION=<%=Action.TRAINING_HOME%>">Training Menu</a>
<!-- body -->


<%@ include file="../inc/jspf/footer.jspf" %>