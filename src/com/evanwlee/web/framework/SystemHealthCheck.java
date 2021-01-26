package com.evanwlee.web.framework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.evanwlee.persistence.pool.DataFactory;

public class SystemHealthCheck {
	
	
	
	public static boolean isDbOk(String DBID) {
        Connection conn = null;
        try{
		conn = DataFactory.getConnection(DBID);
		if( conn != null){
	        
	        return true;

		}
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	if( conn != null){
        		try{conn.close();}catch(Exception e){}
        	}
        }
		
		return false;
	}
}
