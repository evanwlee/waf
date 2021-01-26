package com.evanwlee.web.framework;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.evanwlee.string.StringUtils;
import com.evanwlee.tokens.CmdReturnTokenImpl;
import com.evanwlee.tokens.IReturnToken;
import com.evanwlee.tokens.MessageBoard;



/*Some notes to self
 * How to handle different redirects
if (token.isPathRelativeToServerRoot()){
    dispatcher =getServletContext().getRequestDispatcher(token.getPath());
    dispatcher.forward(request, response);
else if
    dispatcher = request.getRequestDispatcher(token.getPath());
	dispatcher.forward(request, response);
else
	response.sendRedirect(newURL);
*/

/**
 * Basic controller for the web app -> redirects based on an action
 */
public class ControllerServlet extends HttpServlet{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException  {
		
		try{
			HttpSession session = request.getSession (true);
			
			//Do forward of type  based on either action passed or just use generic for the action
			if(request.getParameter(Action.ACTION_ID) != null){
			  if (request.getParameter(Action.ACTION_ID).equalsIgnoreCase(Action.PREVIOUS_CONTEXT)) {
		        	dispatch(request, response,(String)request.getSession().getAttribute("PREVIOUS_CONTEXT"),Action.DISPATCH );
		        }else{
		        	request = Action.setViewsOntoRequest( request.getParameter(Action.ACTION_ID), request);
		        	CmdReturnTokenImpl result = (CmdReturnTokenImpl)Action.doAction(request.getParameter(Action.ACTION_ID), request);
		        	//session.setAttribute(AppSession.MESSAGE_BOARD, new MessageBoard(result));
		        	request.setAttribute(AppSession.MESSAGE_BOARD, new MessageBoard(result));
		        	request = (HttpServletRequest)result.getResult();
		        	String next = result.getNextAction();
		        	if(StringUtils.isEmpty(next)){
		        		next = request.getParameter(Action.ACTION_ID);
		        	}
		        	dispatch(request, response, next, Action.DISPATCH );
		        }
			}else{
				dispatch(request, response, null, -1 );
			}
		}catch(Exception e){
			//Let all server side errors bubble out to client until we are ready to handle....
			e.printStackTrace(response.getWriter());
		}
	}
	  
	  private boolean dispatch(HttpServletRequest request,HttpServletResponse response,String action, int redirectionType ) throws  ServletException, IOException{
		   if(redirectionType ==  Action.DISPATCH){
	        	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Action.getContext(action));
	            request.getSession().setAttribute("PREVIOUS_CONTEXT",  request.getSession().getAttribute("CURRENT_CONTEXT"));
	            request.getSession().setAttribute("CURRENT_CONTEXT", action);
	            dispatcher.forward(request, response);
	        }else if(redirectionType ==  Action.REDIRECT){
	        	response.sendRedirect("");
	        }else{
	        	response.sendRedirect("index.jsp");
	        }
		   return true;
	  }
	  
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException  {
		  doGet(request,response);
	}
	  


}
