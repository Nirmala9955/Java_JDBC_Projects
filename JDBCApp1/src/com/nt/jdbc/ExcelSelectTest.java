package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ExcelSelectTest {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//Register JDBC driver Software
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:Excel:///E:\\JAVA\\02AdvancedJava\\02JDBC\\WorkSpace\\ExcelDB");
			//create statement object
			if(con!=null) {
				st=con.createStatement();
			}
			//Frame the SQL Query 
			query =  "Select * from College.Student_Details";
			//Send and execute SQL Query in database software
			if(st!=null) {
				rs=st.executeQuery(query);
			}
			//Process the ResultSet object
			if(rs!=null) {
				while(rs.next()) {
					flag=true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
				} //while
				if(flag==false)
					System.out.println("No Record Found");
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
