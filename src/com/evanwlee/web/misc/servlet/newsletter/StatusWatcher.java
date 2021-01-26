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
public class StatusWatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusWatcher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn =null;
		String id = "";

		try{
			id = request.getParameter("ID")== null?"":request.getParameter("ID");

			conn = DataFactory.getConnection("MYSQL");
			Statement selectStatement = conn.createStatement();
			ResultSet rs = selectStatement.executeQuery( "SELECT STATUS FROM QUEUE WHERE ID = '"+id+"'");
			
			if(rs.first()){
				response.getWriter().write(id+":"+rs.getString("STATUS"));
			}


		}catch(Exception e){
			//System.out.println("Oh man! Couldn't add a newsletter recipeint("+update_first+" "+update_last+" "+update_email);
			response.getWriter().write("Oh man! Couldn't find status for("+id+")! "+e.getMessage());
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
