<%@page pageEncoding="UTF-8"%><%@ page import="java.util.List,java.io.PrintWriter" isErrorPage="true" %>
<!--
/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//-->
<html>
<head>
  <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=UTF-8" />
  <title>WAF - Error Message</title>
</head>
<body>
<style>
body {
	font-family : arial, verdana, Geneva, Arial, Helvetica, sans-serif;
    font-size : 1.1em;
}
.errorHeader {
	font-size: 1.6em;
	background-color: #6392C6;
	color: white;
	font-weight: bold;
	padding: 3px;
	margin-bottom: 10px;
}

.errorFooter {
	font-size: 0.8em;
	background-color: #6392C6;
	color: white;
	font-style: italic;
	padding: 3px;
	margin-top: 5px;
}

.errorMessage {
	color: red;
	font-weight: bold;
}
.errorExceptions {
}
.errorExceptionStack {
	margin-top: 5px;
	padding: 3px;
	border-style: solid;
	border-width: 1px;
	border-color: #9F9F9F;
	background-color: #E0E0E0;
}
.errorExceptionCause {
	font-size: 1.1em;
	padding: 3px;
	border-style: solid;
	border-width: 1px;
	border-color: #9F9F9F;
	background-color: #E0E0E0;
}
.errorException {
	font-size: 1.0em;
}
</style>
<div class="errorHeader">WAF server encountered an error.</div>

 	<br/>
<script language="JavaScript">
   if (parent.isPopupParent == undefined)
   {
      document.write("<a href='index.jsp'>Return to Home Page</a>");
   }
   else
   {
      document.write("<a href=javascript:parent.hidePopWin(false);window.refresh();>Close this window</a>");
      document.write("<br/><p/>If this problem persists, please close your browser and log in again.");
   }
</script>
    <br/>

<div class="errorFooter">Exception Report</div>
</body>
</html>