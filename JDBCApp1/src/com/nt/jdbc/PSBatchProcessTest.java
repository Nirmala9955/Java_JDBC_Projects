package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PSBatchProcessTest {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement ps = null;
		int result[]=null;
		try {
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) 
				ps=con.prepareStatement("insert into student values(?,?,?,?)");
			//add queries to the batch
			if(ps!=null) {
				//add multiple sets of param values to the batch
				ps.setInt(1, 222);
				ps.setString(2, "kumar");
				ps.setString(3, "delhi");
				ps.setFloat(4, 78.9f);
				ps.addBatch();
				
				ps.setInt(1, 333);
				ps.setString(2, "krtik");
				ps.setString(3, "vizag");
				ps.setFloat(4, 76.9f);
				ps.addBatch();
				
				//execute batch
				result=ps.executeBatch();
			}
			//Process the Result
			if(result!=null) {
				System.out.println("Records are inserted");
			}
			else {
				System.out.println("Records are not inserted");
			}
		} //try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			//close all jdbc object
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		} //finally
	} //main
} //class
