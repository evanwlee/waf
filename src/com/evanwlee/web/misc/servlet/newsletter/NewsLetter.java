package com.evanwlee.web.misc.servlet.newsletter;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.evanwlee.email.EmailUtil;
import com.evanwlee.persistence.PersistenceManager;
import com.evanwlee.persistence.pool.DataFactory;
import com.evanwlee.string.StringUtils;

/**
 * Servlet implementation class NewsLetter
 */
public class NewsLetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewsLetter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn =null;
		String update_first = "";
		String update_last = "";
		String update_email ="";
		try{
			update_first = request.getParameter("FIRST")== null?"":request.getParameter("FIRST");
			update_last = request.getParameter("LAST")== null?"":request.getParameter("LAST");
			update_email = request.getParameter("EMAIL")== null?"":request.getParameter("EMAIL");
			String message = EmailUtil.validateEmail(update_email);
			if( message != null){
				response.getWriter().write("Sorry, " +message);
			}else{
				if(StringUtils.isEmpty(update_first) || StringUtils.isEmpty(update_last)|| StringUtils.isEmpty(update_email)){
					response.getWriter().write("Please enter all fields to register.");
				}else{
					conn = DataFactory.getConnection("MYSQL");
					Statement selectStatement = conn.createStatement();
					ResultSet rs = selectStatement.executeQuery( "SELECT * FROM NEWSLETTER WHERE FIRST = '"+update_first+"' AND LAST = '"+update_last+"' AND EMAIL ='"+update_email+"'");
					
					if(rs.first()){
						response.getWriter().write("Great, " +update_first+", you are already registered!");
					}else{
					
						Statement statement = conn.createStatement();
						statement.executeUpdate("INSERT INTO NEWSLETTER" + 
								" VALUES ('"+update_first+"', " +
										 "'"+update_last +"', " +
										 "'"+update_email+"')");
						response.getWriter().write(update_first+", you are now registered!");
					}
				}
			}

		}catch(Exception e){
			//System.out.println("Oh man! Couldn't add a newsletter recipeint("+update_first+" "+update_last+" "+update_email);
			response.getWriter().write("Oh man! Couldn't add a newsletter recipeint("+update_first+" "+update_last+" "+update_email+")! "+e.getMessage());
			//response.getWriter().write(e.getMessage());
		}finally{
			if(conn != null){
				//try{conn.close();}catch(Exception e){}
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
