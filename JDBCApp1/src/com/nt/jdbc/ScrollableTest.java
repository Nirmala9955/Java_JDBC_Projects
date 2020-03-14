package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ScrollableTest {
	
	private static final String EMP_DETAILS = "SELECT EMPNO, ENAME, SAL, JOB FROM EMP";
	public static void main(String[] args) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			//Send and execute SQL Query in database software
			if(st!=null) {
				rs=st.executeQuery(EMP_DETAILS);
			}
			//Process the ResultSet object
			if(rs!=null) {
				System.out.println("---------------------------------------------");
				System.out.println("Top--Bottom");
				System.out.println("---------------------------------------------");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				} //while			
				System.out.println("---------------------------------------------");
				System.out.println();
				System.out.println("---------------------------------------------");
				System.out.println("Bottom--Top");
				System.out.println("---------------------------------------------");
				while(rs.previous()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				} //while			
				System.out.println("---------------------------------------------");
				System.out.println();
				System.out.println("----------------------------------------------");
				
				//first Record
				rs.first();
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				
				//last record
				rs.last();
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				
				//3rd record from top
				rs.absolute(3);
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				
				//3rd row from bottom
				rs.absolute(-3);
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				
				//relative
				rs.relative(2);	
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				
				//relative
				rs.relative(-4);
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				System.out.println("---------------------------------------------");
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
