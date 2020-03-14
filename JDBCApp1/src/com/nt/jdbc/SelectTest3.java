package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest3 {

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
				st=con.createStatement();
			}
			//Frame the SQL Query 
			//select count(*) from emp;;
			query = "SELECT COUNT(*) FROM EMP";
			//Send and execute SQL Query in database software
			if(st!=null) {
				rs=st.executeQuery(query);
			}
			//Process the ResultSet object
			if(rs!=null) {
				if(rs.next()) {
					count = rs.getInt("count(*)");
					System.out.println("Records count : "+count);
				} //if			
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
