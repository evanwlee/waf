

<%@ page import="java.util.*"%>
<%@page import="com.evanwlee.web.framework.SystemHealthCheck"%>



<html>
<head>
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<title>webcheck</title>
</head>
<body>
datetime: <%=new Date() %><br>
<br>

<br>
WAF.<br>

<br>
System Information:<br>
<table>
<tr><td>MYSQL DB</td><td><%= SystemHealthCheck.isDbOk("MYSQL")? "<font color=\"green\">OK</font>" : "<font color=\"red\">ERROR</font>"%></td></tr>
<tr><td>HOS DB</td><td><%= SystemHealthCheck.isDbOk("HOS")? "<font color=\"green\">OK</font>" : "<font color=\"red\">ERROR</font>"%></td></tr>
<tr><td>TRAINING DB</td><td><%= SystemHealthCheck.isDbOk("TRAINING")? "<font color=\"green\">OK</font>" : "<font color=\"red\">ERROR</font>"%></td></tr>
<%
out.println("<tr><td>Java Runtime</td><td>" + System.getProperty("java.runtime.name") + "</td></tr>");
out.println("<tr><td>Java Runtime Version</td><td>" + System.getProperty("java.runtime.version") + "</td></tr>");
out.println("<tr><td colspan=\"2\"></td></tr>");
out.println("<tr><td>Operating System Name</td><td>" + System.getProperty("os.name") + "</td></tr>");
out.println("<tr><td>Operating System Version</td><td>" + System.getProperty("os.version") + "</td></tr>");
%>
</table>

</body>
</html>
