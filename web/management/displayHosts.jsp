<%@ include file="../inc/jspf/header.jspf" %>
<%@ include file="../inc/headerBar.jsp" %>
<%@ page import="com.evanwlee.web.management.view.StatusView" %>
<%@page import="com.evanwlee.web.framework.Action"%>
<%
StatusView statusView = (StatusView)request.getSession().getAttribute(StatusView.class.getName());
if(statusView == null){ 
	statusView = new StatusView();
} 
String pageTitle = "Server Information";
%>
	
<body>
<br/>
		<%if(statusView != null){ %>
			<i><%=statusView.hosts(user)%></i>
		<%} %>
		
		<form action="main" method="POST" target="_self" id="testForm"  name="testForm">
			<input name="<%=Action.ACTION_ID %>" id="<%=Action.ACTION_ID %>" value="" type="hidden"/>
			<input name="HOST_ID" id="HOST_ID" value="<%=statusView.getValue("HOST_ID") %>" type="text"/>
			<%= statusView.renderSubmitButton("testForm",Action.SHOW_HOSTS,"viewspecficHostInfo","","styledButton","View Specific Server Info",user)%>
			<br/>
			<!-- button onclick="submitForm(document.testForm,'<%=Action.PREVIOUS_CONTEXT%>',document.testForm.<%=Action.ACTION_ID%>);" name="test" id="test" class="styledButton">Back</button-->
		</form>
		<%if(statusView != null){ %>
			<%=statusView.serverInfo() %>
		<%} %>
		<br/>
		<a href="/waf/main?ACTION=<%=Action.HOME%>">Main Page</a>||<a href="/waf/main?ACTION=<%=Action.STATUS_HOME%>">Status Menu</a>
<!-- body -->

<%@ include file="../inc/jspf/footer.jspf" %>