package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlSelectTest {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		try {
			//register JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///nsaj413", "root","root");
			//Create statement object
			if(con!=null) {
				st = con.createStatement();
			}
			//frarm Query
			query = "SELECT * FROM STUDENT_TAB1";
			//Send and execute SQL Query in Database software
			if(st!=null) {
				rs = st.executeQuery(query);
			}
			//Process ResultSet object
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
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se ) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
			}
			catch(SQLException se ) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se ) {
				se.printStackTrace();
			}
		} //finally
	} //main
} //class
