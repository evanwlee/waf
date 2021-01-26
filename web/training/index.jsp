<%@ include file="../inc/jspf/header.jspf" %>
<%@ include file="../inc/headerBar.jsp" %>
<%@ page import="com.evanwlee.web.management.view.*" %>
<%
StatusView statusView = new StatusView();
%>

<body>
<br/>
	<form action="main" method="POST" target="_self" id="mainForm"  name="mainForm">
		<input name="<%=Action.ACTION_ID %>" id="<%=Action.ACTION_ID %>" value="" type="hidden"/>
		<table>
			<%if(user.getRole() != Role.NO_ACCESS){ %>
			<tr>
				<td>
					<%= statusView.renderSubmitButton("mainForm",Action.SHOW_MILES,"Miles","","loginButton","Miles",user)%>
				</td>
			</tr>
			<%}%>
			<tr>
				<td>
					<%= statusView.renderSubmitButton("mainForm",Action.HOME,"goHome","","loginButton","Home",user)%>
				</td>
			</tr>
		</table>
	</form>
	
<!-- body -->
<%@ include file="../inc/jspf/footer.jspf" %>