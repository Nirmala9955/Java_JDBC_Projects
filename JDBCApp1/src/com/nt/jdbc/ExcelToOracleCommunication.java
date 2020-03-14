package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExcelToOracleCommunication {

	private static final String INSERT_STUDENT_DETAILS = "INSERT INTO STUDENT VALUES(?,?,?,?)";

	public static void main(String[] args) {
		
		Connection oraCon = null, xlsCon=null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int sno = 0;
		String sname=null, addrs = null;
		float avg = 0.0f;
		
		try {
			//Register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.hxtt.sql.excel.ExcelDriver");
			//Establish the connection
			oraCon = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			xlsCon = DriverManager.getConnection("jdbc:Excel:///E:\\JAVA\\02AdvancedJava\\02JDBC\\WorkSpace\\ExcelDB");
			//Create statement objects
			if (oraCon!=null) 
				ps = oraCon.prepareStatement(INSERT_STUDENT_DETAILS);
			if(xlsCon!=null)
				st=xlsCon.createStatement();
			//Send and execute SQL Query in Excel
			if(st!=null) {
				rs=st.executeQuery("Select * from College.Student_Details");
			}
			//process the resultset to copy to oracle db
			if(rs!=null && ps!=null) {
				while(rs.next()) {
					//Get each record form resultset (Excel)
					sno=rs.getInt(1);
					sname=rs.getString(2);
					addrs=rs.getString(3);
					avg=rs.getFloat(4);
					
					//set each record values insert query param values
					ps.setInt(1, sno);
					ps.setString(2, sname);
					ps.setString(3, addrs);
					ps.setFloat(4, avg);
					//Execute SQL Query 
					ps.executeUpdate();
				} //while
				System.out.println("Records are copied");
			} //if
		} //try
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
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
				if(ps!=null)
					ps.close();
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
				if(oraCon!=null)
					oraCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(xlsCon!=null)
					xlsCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		} //finally
	}

}
