package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class AgeCalculationOnMySQL {

	public static final String  AGE_CAL_QUERY ="SELECT YEAR(CURDATE())-YEAR(DOB) FROM PERSON_TAB1 WHERE PID=?";
	public static void main(String[] args) {
		
		Scanner sc = null;
		int pid = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int age = 0;
		
		try {
			//Read data from KeyBoard
			sc = new Scanner(System.in);
			if(sc!=null) {
			System.out.print("Enter Person id : ");	
			pid = sc.nextInt();
			}
			//register JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///nsaj413", "root","root");
			//create statement object
			if(con!=null) {
				ps = con.prepareStatement(AGE_CAL_QUERY);
			}
			//set value Query params
			if(ps!=null) {
				ps.setInt(1, pid);
			}
			//execute the Query 
			if(ps!=null)
				rs = ps.executeQuery();
			//process the Result
			if(rs!=null) {
				rs.next();
					age = rs.getInt(1);
					System.out.println("Person age is : "+age+" yrs");
			}
			else {
				System.out.println("Records not found");
			}
		}	
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
			//Close all jdbc object
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
