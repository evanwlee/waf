package com.evanwlee.data;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;



import com.evanwlee.email.Recipient;

import com.evanwlee.persistence.pool.DataFactory;
import com.evanwlee.string.StringUtils;


public class NewsletterFetcher {


		private static final long serialVersionUID = 1L;

	    public NewsletterFetcher() {
	    }

		protected List<Recipient> fetchListOfNewslettersRecipients() {
			Connection conn =null;

			List<Recipient> recipients = new ArrayList<Recipient>();
			try{
				conn = DataFactory.getConnection("MYSQL");
				// CREATE PROCEDURE GetAllNewsletter()
				//   SELECT *  FROM newsletter;
				

				 CallableStatement cStmt = conn.prepareCall("{call GetAllNewsletter()}");
				 boolean hadResults = cStmt.execute();

				 if (hadResults) {
				        ResultSet rs = cStmt.getResultSet();

				        while(rs.next()){
				        	Recipient r = new Recipient();
				        	r.setEmailAddr(rs.getString("EMAIL"));
				        	r.setFirstName(rs.getString("FIRST"));
				        	r.setLastName(rs.getString("LAST"));
				        	recipients.add(r);
				        }
				  }
					
			}catch(Exception e){
				System.out.println("Oh man! Couldn't find a newsletter recipeint.");
			}finally{
				
			}
			
			return recipients;
		}
		
		public static void main(String args[]){
			NewsletterFetcher fetcher = new NewsletterFetcher();
			List<Recipient> recip = fetcher.fetchListOfNewslettersRecipients();
			for(int index = 0; index< recip.size(); index++){
				Recipient r = recip.get(index);
				r.toStream(System.out);
				System.out.println("------------------------------------");
			}
		}

}
