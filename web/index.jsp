<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.evanwlee.web.view.View"%>
<%@page import="com.evanwlee.web.hos.database.model.OpportunityQBE"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.evanwlee.web.view.PagingArrayList"%>
<%@page import="com.evanwlee.string.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.evanwlee.web.view.IPagingView,com.evanwlee.web.hos.view.PagingOpportunityView"%>
<%
	PagingOpportunityView oppView = (PagingOpportunityView)request.getSession().getAttribute(PagingOpportunityView.class.getName());
	if( oppView == null ){
		oppView = new PagingOpportunityView();
	}
	
	String createdDateStyle = "";
	String regionStyle = "";
	String categoryStyle = "";
	if( OpportunityQBE.COLUMN_CREATION_DATE.equals(oppView.getResultsOrderedBy())){
		createdDateStyle = " bold "; 
	}
	
	if( OpportunityQBE.COLUMN_REGION.equals(oppView.getResultsOrderedBy())){
		regionStyle = " bold "; 
	}

	if( OpportunityQBE.COLUMN_CATEGORY.equals(oppView.getResultsOrderedBy())){
		categoryStyle = " bold "; 
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
		<meta name="description" content="huntingoppswap provides forums for hunting related swaps and exchanges " >
		<meta name="keywords" content=", huntingoppswap, huntingopp, hunting, hunt, hunter, swap, trade, exchange, hunting trade" >
		<link href="/waf/style/page.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" title="hunteroppswap" href="style/hunteroppswap.css" type="text/css" media="all">
		<script type="text/javascript" language="JavaScript" src="scripts/general.js"></script>
        <script>
                function followLink(to){
                        window.location=to;
                }
        </script>

	
	</head>
	<body style="background-color:white">
		<%@ include file="inc/headerBar.jsp" %>
 
		<div style="width:762px; position: absolute; left: 0px; top:30px; border: solid 1px #CCB78F; border-left:0px;border-right:0px;border-bottom:0px; height:99%;">

			<%@ include file="inc/links.jsp" %>
			

				<!-- Portlet Center-->
				<div id="centerPortlet" style="width:600px; position: absolute; left: 160px; top:5px; height: 400px; ">
					<!-- Portlet HEADER -->
					<b class="roundTitle">
						<b class="roundLeft"></b>
						<b class="roundCenter"></b>
						<b class="roundRight"></b>
					</b>	
					<div id="header" class="portletHeaderDiv" >
					<%if(user.getRole() == Role.NO_ACCESS){ %>
						<span class="portletHeaderFont">Entry</span>
					<%}else{ %>
						<span class="portletHeaderFont">Entry [<a href="#" onclick="editEntry(document.manageExistingEntry,document.manageExistingEntry.<%=Action.ACTION_ID%>,'<%=Action.EDIT_OPP%>',document.manageExistingEntry.OPP_ID,'');" style=" font: normal x-small tahoma, helvetica, sans-serif;color: white;">Post</a>]</span>
					<%} %>	
					</div>
	

					<!-- Portlet BODY -->
					<table border="0" cellpadding="0" cellspacing="0" style="width:600px; border-top:0px; border-bottom:0px; border-right: 1px solid #BDBDBD;border-left: 1px solid #BDBDBD; padding: 0px 0px 0px 0px; margin: 0px 0px 0px 0px;">
						<tr>
							<td width="60%" nowrap colspan="2" valign="center" style="background-color:#e7e7e4;">
								<font class="filterCategories">&nbsp;&nbsp;Sort By: 
									<a href="/waf/main?<%=Action.ACTION_ID %>=<%=Action.SHOW_OPPS %>&<%=IPagingView.ORDER_BY%>=<%=OpportunityQBE.COLUMN_CREATION_DATE %>" style="text-decoration:none;font: <%=createdDateStyle %> small tahoma, helvetica, sans-serif;">Date Posted</a> | 
									<a href="/waf/main?<%=Action.ACTION_ID %>=<%=Action.SHOW_OPPS %>&<%=IPagingView.ORDER_BY%>=<%=OpportunityQBE.COLUMN_REGION %>" style="text-decoration:none;font: <%=regionStyle %> small tahoma, helvetica, sans-serif;">Region</a> | 
									<a href="/waf/main?<%=Action.ACTION_ID %>=<%=Action.SHOW_OPPS %>&<%=IPagingView.ORDER_BY%>=<%=OpportunityQBE.COLUMN_CATEGORY %>" style="text-decoration:none;font: <%=categoryStyle %> small tahoma, helvetica, sans-serif;">Category</a>
								</font>
							</td>
							<td width="40%" nowrap colspan="1" valign="top" align="right" style="background-color:#e7e7e4;font: normal x-small tahoma, helvetica, sans-serif;">
								<%=oppView.renderOppPagingButton()%>
							</td>
						</tr>
					</table>
					<!-- Portlet BODY -->
					<form style="margin:0px; padding:0px;" action="main" method="POST" name="manageExistingEntry" id="manageExistingEntry">
						<input name="<%=Action.ACTION_ID%>" id="<%=Action.ACTION_ID%>" value="" type="hidden"/>
						<input name="OPP_ID" id="OPP_ID" value="" type="hidden"/>
					<table border="0" cellpadding="0" cellspacing="0" style="width:600px; border-top:0px; border-bottom:0px; border-right: 1px solid #BDBDBD;border-left: 1px solid #BDBDBD; padding: 0px 0px 0px 0px; margin: 0px 0px 0px 0px;">
						<%  
						List<OpportunityQBE> opps = oppView.getCurrentPageOpps();
					 		for (Iterator<OpportunityQBE> iterator = opps.iterator(); iterator.hasNext();) {
								OpportunityQBE opp = iterator.next();
						%>
						
							<tr>
								<td colspan="3" style="border-bottom: solid 0px #CCB78F;">
									<table width="100%" border="0" cellpadding="1" cellspacing="0">
										<tr >
											<td colspan="3" width="565px" style="border-bottom: solid 1px #A9D0F5;font: normal x-small tahoma, helvetica, sans-serif;background-color:#A9D0F5;border-top:solid 1px #A9D0F5" valign="top" nowrap>
												
												<b><%=opp.getDescription()%></b>
												(posted by <a href="" class="userLink" style="text-decoration:none;"><%=opp.getOwner().getUserName()%></a>: on <%=opp.getCreationDate("MM/dd/yyyy")%>)
												<%if( (user != null) && user.getUserName().equalsIgnoreCase(opp.getOwner().getUserName())){ %>
														<button title="Delete Entry" onclick="editEntry(document.manageExistingEntry,document.manageExistingEntry.<%=Action.ACTION_ID%>,'<%=Action.DELETE_OPP%>',document.manageExistingEntry.OPP_ID,'<%=opp.getUID() %>');" style="height:12px; width:10px;border:none;background:url('./img/close.jpg') no-repeat"></button>
														<button title="Edit Entry" onclick="editEntry(document.manageExistingEntry,document.manageExistingEntry.<%=Action.ACTION_ID%>,'<%=Action.EDIT_OPP%>',document.manageExistingEntry.OPP_ID,'<%=opp.getUID() %>');" style="height:12px; width:8px;border:none;background:url('./img/edit.JPG') no-repeat"></button>
												<%}%>
												
											</td>
										</tr>
										<tr>
											<td colspan="3" width="565px" style="padding-left:10px; font: normal small tahoma, helvetica, sans-serif;background:#fff url(./img/input_bg.gif) 0 0px repeat-x;">
												<pre style="color:#0C3006;word-wrap: break-word; font: normal small tahoma, helvetica, sans-serif;margin:0px;"><%=StringUtils.HTMLEntityEncode(opp.getDetails())%></pre>
											</td>
										</tr>
										<% if( user != null && !user.getUserName().equalsIgnoreCase(opp.getOwner().getUserName())){ %>
										<tr>
											<td colspan="3" align="right">
												<a href="" class="respondLink" style="text-decoration:none;margin:0px;">Respond...</a>
											</td>
										</tr>
										<% } %>
									</table>
								</td>
							</tr>
						
			
						<%
							}
						%>
						
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
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
		<%--@ include file="inc/footer.jsp" --%>
	</body>
</html>