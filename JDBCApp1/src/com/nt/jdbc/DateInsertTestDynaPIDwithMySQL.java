package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DateInsertTestDynaPIDwithMySQL {
	
	private static final String INSERT_DATE_QUERY ="INSERT INTO PERSON_TAB1 (NAME, DOB, DOJ, DOM) VALUES(?,?,?,?)";
	
	public static void main(String[] args) {
		Scanner sc = null;
		String name=null, dob=null, doj=null, dom=null;
		SimpleDateFormat sdf1 = null, sdf2=null;
		java.util.Date udob=null, udoj=null;
		java.sql.Date sqdob=null, sqdoj=null, sqdom=null;
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			//Read data from KeyBoard
			sc = new Scanner(System.in);
			if(sc!=null) {
			System.out.print("Enter Person Name : ");
			name = sc.next();
			System.out.print("Enter Person DOB(dd-MM-yyyy) : ");
			dob = sc.next();
			System.out.print("Enter Person DOJ(MM-dd-yyyy) : ");
			doj = sc.next();
			System.out.print("Enter Person DOM(yyyy-MM-dd) : ");
			dom = sc.next();
			} 
			//Convert String date values to java.sql.Date class objects
			//For DOB 
			sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			if(sdf1!=null)
				udob = sdf1.parse(dob);	//gives java.util.Date class object
			if(udob!=null)
				sqdob = new java.sql.Date(udob.getTime());	//gives java.sql.Date class object
			
			//For DOJ
			sdf2 = new SimpleDateFormat("MM-dd-yyyy");
			if(sdf2!=null)
				udoj = sdf2.parse(doj);	//gives java.util.Date class object
			if(udoj!=null)
				sqdoj = new java.sql.Date(udoj.getTime());	//gives java.sql.Date class object
			
			//For DOM
			sqdom = java.sql.Date.valueOf(dom);
				
			//register JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///nsaj413", "root","root");
			
			//create statement object
			if(con!=null) {
				ps = con.prepareStatement(INSERT_DATE_QUERY);
			}
			//set value Query params
			if(ps!=null) {
				ps.setString(1, name);
				ps.setDate(2, sqdob);
				ps.setDate(3, sqdoj);
				ps.setDate(4, sqdom);
			}
			//execute the Query 
			if(ps!=null)
				result = ps.executeUpdate();
			//process the result
			if(result==0) {
				System.out.println("Record not inserted");
			} else {
				System.out.println("Record is inserted");
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
			//Close all jdbc object
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
