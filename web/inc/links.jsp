                        <!-- Portlet Left-->
                        


<%@page import="com.evanwlee.web.framework.widget.WidgetFactory"%>
<%@page import="com.evanwlee.web.framework.security.permission.SideNavigationMenuPermission"%><div id="leftPortlet1" style="width:150px; position: absolute; left: 5px; top:5px; ">
                                <!-- Portlet HEADER -->
                                <b class="linkroundTitle">
                                        <b class="linkroundLeft"></b>
                                        <b class="linkroundCenter"></b>
                                        <b class="linkroundRight"></b>
                                </b>

                                <!-- Portlet BODY -->
                                <table border="0" cellpadding="1" cellspacing="0" style="width:100%; border-top:0px; border-bottom:0px; border-right: 1px solid #BDBDBD;border-left: 1px solid #BDBDBD; padding: 0px 0px 0px 0px; margin: 0px 0px 0px 0px;">
                                        <tr>
                                                <td nowrap><img src="img/right_bullet.JPG"/><%=WidgetFactory.createAnchorRef("/waf/main?ACTION=ACTION.HOME","Home") %></td>
                                        </tr>
                                        <tr>
                                                <td nowrap><img src="img/right_bullet.JPG"/><%=WidgetFactory.createAnchorRef("/waf/main?ACTION=ACTION.STATUS_HOME","Host Status", new SideNavigationMenuPermission(user.getRole())) %></td>
                                        </tr>
                                        <tr>
                                                <td nowrap><img src="img/right_bullet.JPG"/><%=WidgetFactory.createAnchorRef("/waf/main?ACTION=ACTION.TRAINING_HOME","Training", new SideNavigationMenuPermission(user.getRole())) %></td>
                                        </tr>
                                </table>

                                <!-- Portlet FOOTER -->
                                <div id="footer" class="linkportletFooterDiv">
                                </div>
                                <b class="roundTitle">
                                        <b class="linkroundRightBottom"></b>
                                        <b class="linkroundCenterBottom"></b>
                                        <b class="linkroundLeft"></b>
                                </b>
                        </div>
                        <!-- End Portlet -->

                        <%if(Role.MANAGER == user.getRole()){ 
                        	session = request.getSession (true);
                    		String serverUrl = (String)session.getAttribute(AppSession.SERVER);
                        %>
                         <!-- Portlet 2 Left-->
                        <div id="leftPortlet2" style="width:150px; height:300px; position: absolute; left: 5px; top:80px; ">
							<iframe src="<%= serverUrl%>/homeLinks.php" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="no" scrolling="no" style="border-width:0px; background:#FFF;"></iframe>
						</div>
						<%} %>
