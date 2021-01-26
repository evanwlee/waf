<%@ include file="../inc/jspf/header.jspf" %>

<%@ include file="../inc/headerBar.jsp" %>
<%@ page import="com.evanwlee.web.management.view.*" %>
<%
StatusView statusView = new StatusView();
%>

<body>
<br/>
	Previous Context=<%=request.getSession().getAttribute("PREVIOUS_CONTEXT") %><br>
	Context=<%=request.getSession().getAttribute("CURRENT_CONTEXT") %>
	<form action="main" method="POST" target="_self" id="mainForm"  name="mainForm">
		<input name="<%=Action.ACTION_ID %>" id="<%=Action.ACTION_ID %>" value="" type="hidden"/>
		<table>
			<%if(user.getRole() != Role.NO_ACCESS){ %>
			<tr>
				<td>
					<%= statusView.renderSubmitButton("mainForm",Action.SHOW_STATUS,"Status","","loginButton","Status",user)%>
				</td>
				<td>
					<%= statusView.renderSubmitButton("mainForm",Action.SHOW_HOSTS,"viewHostInfo","","loginButton","Servers",user)%>
				</td>
				<td>
					<%= statusView.renderSubmitButton("mainForm",Action.SHOW_SERVICES,"viewHostServiceInfo","","loginButton","Services",user)%>
				</td>
			</tr>
			<%} %>	
			<tr>
				<td colspan="3">
					<%= statusView.renderSubmitButton("mainForm",Action.HOME,"goHome","","loginButton","Home",user)%>
				</td>
			</tr>
		</table>
	</form>

<!-- body -->
<%@ include file="../inc/jspf/footer.jspf" %>