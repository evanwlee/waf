<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.evanwlee.web.view.View"%>
<%@page import="com.evanwlee.string.StringUtils"%>
<%@page import="com.evanwlee.web.hos.view.OpportunityView"%>
<%@page import="com.evanwlee.web.hos.view.PagingOpportunityView"%>
<%
OpportunityView manageOppView = (OpportunityView)request.getSession().getAttribute(OpportunityView.class.getName());
	if( manageOppView == null ){
		manageOppView = new OpportunityView();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">




<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" title="hunteroppswap" href="style/hunteroppswap.css" type="text/css" media="all">
	<script type="text/javascript" language="JavaScript" src="scripts/general.js"></script>
	<script>
		function init() {
			document.getElementById('<%=PagingOpportunityView.fld_DESCRIPTION%>').focus();
		}


	  function checkStatus(element, length) {
			if( element != null){
				maxLength(element,length);
				limiter(element,length);
			}
	  }

	</script>
<title>Edit Opportunity</title>
</head>
<body style="background-color:white" onload="init()" >
		<%@ include file="inc/headerBar.jsp" %>
		<div  style="width:762px; position: absolute; left: 0px; top:30px; border: solid 1px #BDBDBD; border-left:0px;border-right:0px;border-bottom:0px; height:99%;">
			<%@ include file="inc/links.jsp" %>
	<!-- Portlet Center-->
			<div id="centerPortlet" style="width:600px; position: absolute; left: 160px; top:5px; height: 400px; border-right:0px">
				<!-- Portlet HEADER -->
				<b class="roundTitle">
					<b class="roundLeft"></b>
					<b class="roundCenter"></b>
					<b class="roundRight"></b>
				</b>	
				<div id="header" class="portletHeaderDiv" >
					<span class="portletHeaderFont">Edit Opportunity</span>
				</div>		
				<!-- Portlet BODY -->
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%; border-top:0px; border-bottom:0px; border-right: 1px solid #BDBDBD;border-left: 1px solid #BDBDBD; padding: 0px 0px 0px 0px; margin: 0px 0px 0px 0px;">
					<tr>
						<td width="60%" nowrap  valign="center" style="background-color:#BDBDBD;">
						&nbsp;
						</td>
					</tr>
				</table>
				<!-- Portlet BODY -->
				<form method="POST" action="main" class="login" target="_self" id="addEntry"  name="addEntry" style="margin:0px; padding:5px;">
					<input name="<%=Action.ACTION_ID%>" id="<%=Action.ACTION_ID%>" value="" type="hidden"/>
					<input name="<%=OpportunityView.OPP_ID%>" id="<%=OpportunityView.OPP_ID%>" value="<%=manageOppView.getEntryId()%>" type="hidden"/>
					<table border="0" cellpadding="0" cellspacing="0" style="width:100%; border-top:0px; border-bottom:0px; padding: 0px 0px 0px 0px; margin: 0px 0px 0px 0px;">
						<tr>
							<td width="25%" valign="top">
								Region:
							</td>
							<td width="75%" >
								<%=manageOppView.renderRegionDdl(OpportunityView.ddl_REGION,manageOppView.getSelectedRegionID(),"formField")%>
							</td>
						</tr>
						<tr>
							<td width="25%" valign="top">
								Category:
							</td>
							<td width="75%" >
							<%=manageOppView.renderCategoryDdl(OpportunityView.ddl_CATEGORY,manageOppView.getSelectedCategoryID(),"formField")%>
							</td>
						</tr>
						<tr>
							<td width="25%" valign="top">
								Short Description:
							</td>
							<td width="75%" >
								<input name="<%=PagingOpportunityView.fld_DESCRIPTION%>" id="<%=PagingOpportunityView.fld_DESCRIPTION%>" type="text" class="formField" value="<%=manageOppView.getOppDescription()%>"></input>
							</td>
						</tr>
						<tr>
							<td width="25%" valign="top">
								Long Description:
							</td>
							<td width="75%" >
								<textarea cols="50" rows="3" name="<%=PagingOpportunityView.fld_TEXT%>" id="<%=PagingOpportunityView.fld_TEXT%>" style="width: 98%; border:1px #999 solid;" onKeyUp="checkStatus(this,'<%=OpportunityView.MAX_ENTRY_LENGTH%>');" onKeyDown="checkStatus(this,'<%=OpportunityView.MAX_ENTRY_LENGTH%>');"><%=manageOppView.getOppDetails()%></textarea>
								<span id="<%=PagingOpportunityView.fld_TEXT%>Count" class="entryTextCount"><%=OpportunityView.MAX_ENTRY_LENGTH%></span><span id="entryTextCountMax" class="entryTextCount">/<%=OpportunityView.MAX_ENTRY_LENGTH%> Characters</span>
							</td>
						</tr>
						<tr>
							<td width="25%" nowrap align="right"><%=View.renderSubmitButton("addEntry",Action.POST_EDITTED_OPP,"Post","","loginButton","Post",user)%></td>
							<td width="75%" nowrap><input class="loginButton" id="clearbutton" name="clearbutton" type="reset" style="" tabindex="9" onclick="" value="Clear" /></td>
						</tr>
					</table>
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
	
</div>
</body>
</html>