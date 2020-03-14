package com.nt.jdbc;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetEmpDetails {

	public static void main(String[] args) {
		//declare local variable
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//Register jdbc driver 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish connection 
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			//Create statement
			if(con!=null)
				st = con.createStatement();
			//prepare SQL Query
			query = "SELECT EMPNO, ENAME, JOB, SAL FROM EMP WHERE SAL=(SELECT MAX(SAL)FROM EMP)";
			//send and execute SQL Query in DB s/w
			if(st!=null)
				rs = st.executeQuery(query);
			//process the ResultSet
			if (rs!=null) {
				while (rs.next()) {
					flag = true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				}
				if(flag) {
					System.out.println("Record not found");
				}
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
			//close jdbc objects 
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
		}//finally
		
	} //main

} //class
