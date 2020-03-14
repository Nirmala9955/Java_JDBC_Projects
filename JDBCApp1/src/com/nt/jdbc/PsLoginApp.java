package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PsLoginApp {
	
	private static final String AUTH_QUERY = "SELECT COUNT(*) FROM USERLIST WHERE USERNAME=? AND PASSWORD=?";
	
	public static void main(String[] args) {
		
		Scanner sc = null;
		String user=null, pass=null;
		String cond=null;
		Connection con = null;
		PreparedStatement ps = null;
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
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				ps = con.prepareStatement(AUTH_QUERY);
			}
			//Send param valueand execute SQL Query in database software
			if(ps!=null) {
				ps.setString(1, user);
				ps.setString(2, pass);
				rs=ps.executeQuery();
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
				if(ps!=null)
					ps.close();
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
