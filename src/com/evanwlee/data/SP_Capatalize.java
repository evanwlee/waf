package com.evanwlee.data;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;


import com.evanwlee.persistence.pool.DataFactory;

/*DELIMITER $$
CREATE PROCEDURE `Capitalize`(INOUT str VARCHAR(1024))
BEGIN
	DECLARE i INT DEFAULT 1;
	DECLARE myc, pc CHAR(1);
 	DECLARE outstr VARCHAR(1000) DEFAULT str;
 	WHILE i <= CHAR_LENGTH(str) DO
		SET myc = SUBSTRING(str, i, 1);
		SET pc = CASE WHEN i = 1 THEN ' ' 
			      ELSE SUBSTRING(str, i - 1, 1) 
			 END;
		IF pc IN (' ', '&', '''', '_', '?', ';', ':', '!', ',', '-', '/', '(', '.') THEN
		    SET outstr = INSERT(outstr, i, 1, UPPER(myc));
		END IF;
		SET i = i + 1;
	END WHILE;
	SET str = outstr;
END$$
DELIMITER ; 
*/ 
public class SP_Capatalize {
			private static final long serialVersionUID = 1L;

		    public SP_Capatalize() {
		    }

			protected String capitalize(String whatToCapatalize) {
				Connection conn =null;

				String result = whatToCapatalize;
				try{
					conn = DataFactory.getConnection("MYSQL");

					
					 CallableStatement cStmt = conn.prepareCall("{call Capitalize(?)}");
					 cStmt.setString(1,whatToCapatalize);
					 cStmt.registerOutParameter(1, Types.VARCHAR);
					 boolean hadResults = cStmt.execute();
					 //System.out.println(hadResults);
					 String outputValue = cStmt.getString(1); 
					 result = outputValue;

						
				}catch(Exception e){
					System.out.println("Oh man! Couldn't find a newsletter recipeint.");
				}finally{
					
				}
				
				return result;
			}
			
			public static void main(String args[]){
				SP_Capatalize sp = new SP_Capatalize();
				String result = sp.capitalize("the dog likes me!");
				System.out.println(result);
			}



}
