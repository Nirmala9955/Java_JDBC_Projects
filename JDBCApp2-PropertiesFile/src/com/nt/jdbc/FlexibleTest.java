package com.nt.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class FlexibleTest {
	
	private static final String GET_STUDENT_DETAILS = "SELECT SLNO, SNAME, ADDRESS, AVG FROM STUDENT";
		
	public static void main(String[] args) {
		
		InputStream is = null;
		Properties props = null;
		String driver=null, url=null, user=null, password=null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//locate fiel using inputStream
			is=new FileInputStream("src/com/nt/commons/DBDetails.properties");
			//load Properties file data into java.util.properties class object
			props=new Properties();
			props.load(is);
			//Read JDBC properties from properties file
			driver=props.getProperty("jdbc.driver");
			url=props.getProperty("jdbc.url");
			user=props.getProperty("jdbc.user");
			password=props.getProperty("jdbc.password");
			//Register JDBC driver s/w
			Class.forName(driver);
			//Establish the connection
			con = DriverManager.getConnection(url, user, password);
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
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
				} //while
			} //if
		} //try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
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

