package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoginApp {

	public static void main(String[] args) {
		
		Scanner sc = null;
		String user=null, pass=null;
		String cond=null;
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		int count = 0;
		try {
			//read input from keyboard
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.print("Enter userid : ");
				user = sc.nextLine();
				System.out.print("Enter password : ");
				pass = sc.nextLine();
				System.out.println();
			}
			//Convert input values as required for the SQL Query
			user = "'"+user+"'";
			pass = "'"+pass+"'";
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				st=con.createStatement();
			}
			//Prepare SQL Query
			//select count(*) from userlist where username='nimu' and password='nimu@123';
			query =  "SELECT COUNT(*) FROM USERLIST WHERE USERNAME="+user+"AND PASSWORD="+pass;
			//Send and execute SQL Query in database software
			if(st!=null) {
				rs=st.executeQuery(query);
			}
			//Process the ResultSet object
			if(rs!=null) {
				if(rs.next()) {
					count=rs.getInt(1);
				} //if
				if(count==0)
					System.out.println("Invalid UserId and password.....");
				else
					System.out.println("Login successful......");
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
