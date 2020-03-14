package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class SelectNonSelectTest {

	public static void main(String[] args) {
		
		Scanner sc = null;
		String cond=null;
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		boolean flag = false;
		int count = 0;
		try {
			//read input from keyboard
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter SQL Query for student table");
				query = sc.nextLine();
			} 
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				st=con.createStatement();
			}
			//Send and execute SQL Query in database software
			if(st!=null) {
				flag=st.execute(query);
			}
			//Process the ResultSet object
			if(flag==true) {
				System.out.println("select Query is executed");
				rs = st.getResultSet();
				//process ResultSet Object
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
				} //while
			} //if
			else {
				System.out.println("Non-select Query is executed");
				count = st.getUpdateCount();
				System.out.println("no. of records that are effected "+count);
			} //else
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
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		} //finally
	} //main
} //class
