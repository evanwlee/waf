
<%@page import="com.evanwlee.web.hos.view.PagingOpportunityView"%>
<%
	PagingOpportunityView oppView = (PagingOpportunityView)request.getSession().getAttribute(PagingOpportunityView.class.getName());
%>

<%=oppView.getOppsAsString() %>


