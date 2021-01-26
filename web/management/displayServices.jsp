<%@ include file="../inc/jspf/header.jspf" %>
<%@ include file="../inc/headerBar.jsp" %>
<%@ page import="com.evanwlee.web.management.view.ServiceView" %>
<%@ page import="com.evanwlee.persistence.IWrapper" %>
<%@ page import="com.evanwlee.management.HostWrapper" %>
<%
//Need to add view config to view.properties to associated the view with the action so that it
//will be set-up and returned correctly.
ServiceView serviceView = (ServiceView)request.getSession().getAttribute(ServiceView.class.getName());
%>
	<body>
	<br/>
	<br/>
	<%=serviceView.editServicesList()%>
		 <!-- <%=serviceView.servicesStringList("<BR><BR><BR>")%>-->
		 <form action="main" method="POST" target="_self" id="testForm"  name="testForm">
			<input name="<%=Action.ACTION_ID %>" id="<%=Action.ACTION_ID %>" value="" type="hidden"/>
			<select name="HOST_ID" id="HOST_ID">
	    		<% java.util.List<IWrapper> hosts = serviceView.getHosts();
	    				out.println("<option value=\"\">Choose Host...</option>");
						for (IWrapper host : hosts) {
							HostWrapper theHost = (HostWrapper)host;
							String selected = "";
							selected =  theHost.getColumnValue("ID").equalsIgnoreCase(serviceView.getValue("HOST_ID"))?"selected":"";
							out.println("\t\t\t\t\t<option value=\""+theHost.getColumnValue("ID")+"\""+selected+">"+theHost.getName()+"</option>");
						}%>
  			</select>
			<%= serviceView.renderSubmitButton("testForm",Action.SHOW_SERVICES,"viewSpecficHostInfo","","styledButton","View Specific Server Services",user)%>
		</form>
		<a href="/waf/main?ACTION=<%=Action.HOME%>">Main Page</a>||<a href="/waf/main?ACTION=<%=Action.STATUS_HOME%>">Status Menu</a>
<!-- body -->

<%@ include file="../inc/jspf/footer.jspf" %>