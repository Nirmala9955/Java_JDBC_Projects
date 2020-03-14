package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ResultSetMetaDataTest {
	
	private static final String GET_STUDENT_DETAILS = "SELECT SLNO, SNAME, ADDRESS, AVG FROM STUDENT";
	
	public static void main(String[] args) {
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd= null;
		int colCnt=0;
		try {
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				st=con.createStatement();
			}
			//Send and execute SQL Query 
			if(st!=null) {
				rs=st.executeQuery(GET_STUDENT_DETAILS);
			}
			//Process the ResultSet object
			if(rs!=null) {
				//create ResultSet MetaData
				rsmd=rs.getMetaData();
				//get column count 
				colCnt=rsmd.getColumnCount();
				//display column names
				for(int i=1; i<=colCnt; i++) {
					System.out.print(rsmd.getColumnLabel(i)+"  ");
				} //for
				System.out.println();
				
				//display column types
				for(int i=1; i<=colCnt; i++) {
					System.out.print(rsmd.getColumnTypeName(i)+"  ");
				} //for
				System.out.println();
				
				//Process the ResultSet
				while(rs.next()) {
					for(int i=1; i<=colCnt; i++) {
						System.out.print(rs.getString(i)+"  ");
					} //for
					System.out.println();
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
