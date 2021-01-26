<%@page import="com.evanwlee.web.framework.UserProfile"%>	
<%@page import="com.evanwlee.web.framework.Action"%>
<%@ include file="framework.jsp" %>
<%@ include file="messageBoard.jspf" %>
		<form method="POST" action="main" target="_self" id="generic"  name="generic" style="margin:0px">
			<input name="<%=Action.ACTION_ID%>" id="<%=Action.ACTION_ID%>" value="" type="hidden"/>
		</form>
		<div id="headerBar" name="headerBar" class="headerDiv">
			<table border="0" style="width:100%;padding:0;margin:0px 5px 3px 3px;" cellpadding="0" cellspacing="0">
				<tr>
					<td nowrap><!-- img src="img/__title.GIF"/--></td>
					<td nowrap>&nbsp;</td>
					<td nowrap align="right">
					 
					
					 <span class="topRightNavCellRect" style="position: realative; top: -1px;padding-bottom: 2px;  ">
		                 <% if( user.getRole() != Role.NO_ACCESS ){ %>
		                 	<a class="NavRight" href="Order_Management_Bookings_Help.htm" title="Help" style="text-decoration:none;" target="_blank">Preferences</a>
		                 	&nbsp;|&nbsp;
			                <a class="NavRight" href="" onclick="return submitForm(document.generic,'<%=Action.LOGOUT%>',document.generic.<%=Action.ACTION_ID%>);"
			                  title="Logout" target="_top" style="text-decoration:none;">Log out <%=user.getUserName()%></a>
		                 <% }else{%> 
		                    <a class="NavRight" href="login.jsp"
		                     title="Logout" target="_top" style="text-decoration:none;">Log in</a>
		                 <% }%> 
		              </span>
		            </td>
				</tr>
			</table>
		</div>