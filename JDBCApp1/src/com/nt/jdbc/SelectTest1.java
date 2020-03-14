package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest1 {

	public static void main(String[] args) {
		
		Scanner sc = null;
		String desg1=null, desg2=null, desg3=null;
		String cond=null;
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//read input from keyboard
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Designation one ");
				desg1 = sc.next().toUpperCase();
				System.out.println("Enter Designation two ");
				desg2 = sc.next().toUpperCase();
				System.out.println("Enter Designation two ");
				desg3 = sc.next().toUpperCase();
			}
			//Frame Condition ('CLEARK','MANAGER','SALESMAN') 
			cond = "('"+desg1+"','"+desg2+"','"+desg3+"')";
			//gives ('CLEARK','MANAGER','SALESMAN') 
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				st=con.createStatement();
			}
			//Frame the SQL Query 
			// select* from emp where job in ('CLERK', 'MANAGER', 'SALESMAN') order by job;
			query =  "SELECT * FROM EMP WHERE JOB IN"+cond+ "ORDER BY JOB";
			//Send and execute SQL Query in database software
			if(st!=null) {
				rs=st.executeQuery(query);
			}
			//Process the ResultSet object
			if(rs!=null) {
				while(rs.next()) {
					flag=true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
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
