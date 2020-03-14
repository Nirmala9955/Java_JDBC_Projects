package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class InSensitiveResultSetTest {
	
	private static final String GET_STUDENT_DETAILS = "SELECT SLNO, SNAME, ADDRESS, AVG FROM STUDENT";
	
	public static void main(String[] args) {
		
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		int count = 0;
		try {
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			}
			//Send and execute SQL Query 
			if(st!=null) {
				rs=st.executeQuery(GET_STUDENT_DETAILS);
			}
			//Process the ResultSet object
			if(rs!=null) {
				while(rs.next()) {
					if(count==0)
						Thread.sleep(30000);
					rs.refreshRow();
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
					count++;
				} //while
			} //if
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
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
