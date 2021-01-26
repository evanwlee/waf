<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.evanwlee.web.view.View"%>
<%@page import="com.evanwlee.web.framework.Action"%>
<%@page import="com.evanwlee.web.framework.AppSession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" title="hunteroppswap" href="style/hunteroppswap.css" type="text/css" media="all">
<script type="text/javascript" language="JavaScript" src="scripts/general.js"></script>
<%@ include file="../inc/messageBoard.jspf" %>
<title>Login</title>
</head>
	<body>

			<!-- Portlet Left-->
			<div id="leftPortlet1" style="width:300px; position: relative; left: 250px; top:30px; ">
				<!-- Portlet HEADER -->
				<b class="roundTitle">
					<b class="roundLeft"></b>
					<b class="roundCenter"></b>
					<b class="roundRight"></b>
				</b>	
				<div id="header" class="portletHeaderDiv" >
				<span class="portletHeaderFont"><center>Log In</center></span>
				
				<%=board.getAllMessagesAsHtmlList("white")%>
				</div>
				<form method="POST" action="main" class="login" target="_self" id="loginForm"  name="loginForm" style="margin:0px">
				<input name="<%=Action.ACTION_ID%>" id="<%=Action.ACTION_ID%>" value="" type="hidden"/>
				<input name="<%=AppSession.SERVER%>" type="hidden" value="">
				<!-- Portlet BODY -->
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%; border-top:0px; border-bottom:0px; border-right: 0px solid #BDBDBD;border-left: 0px solid #BDBDBD; padding: 0px 0px 0px 0px; margin: 0px 0px 0px 0px;">
					<tr>
						<td width="50%" nowrap>User:</td>
						<td width="50%" nowrap><input id="username" class="formField" name="username" type="text"/></td>
					</tr>
					<tr>
						<td width="50%" nowrap>Password:</td>
						<td width="50%" nowrap><input id="password" class="formField" name="password" type="password"/></td>
					</tr>
					<tr><td width="50%" nowrap><%=View.renderSubmitButton("loginForm",Action.LOGIN,"Login","loginButton","Login")%></td>
						<td width="50%" nowrap><%=View.renderSubmitButton("loginForm",Action.HOME,"Cancel","loginButton","Cancel")%></td>
					</tr>
				</table>
					<script>
	                    var loc = window.location.href;
	                    var context_loc = loc.substring(0, loc.lastIndexOf('/'));
	                    document.forms['loginForm'].<%=AppSession.SERVER%>.value = context_loc.substring(0, context_loc.lastIndexOf('/'));
	                    //alert("Base Path=" + document.forms['loginForm'].<%=AppSession.SERVER%>.value);
				  	</script>
				</form>
				
				<!-- Portlet FOOTER -->
				<div id="footer" class="portletFooterDiv">
				</div>
				<b class="roundTitle">
					<b class="roundRightBottom"></b>
					<b class="roundCenterBottom"></b>
					<b class="roundLeft"></b>
				</b>	
			</div>
			<!-- End Portlet -->
			

	</body>
</html>