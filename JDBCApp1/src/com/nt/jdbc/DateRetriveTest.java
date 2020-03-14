package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DateRetriveTest {

	public static final String  RETRIVE_DATE_QUERY ="SELECT PID, PNAME, DOB, DOJ, DOM FROM PERSON_TAB1 WHERE PID=?";
	
	public static void main(String[] args) {
		
		Scanner sc = null;
		int pid = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String name = null;
		java.sql.Date sqdob=null, sqdoj=null, sqdom=null;
		java.util.Date udob=null, udoj=null, udom=null;
		SimpleDateFormat sdf = null;
		String sdob=null, sdoj=null, sdom=null;
		boolean flag = false;
		
		try {
			//Read data from KeyBoard
			sc = new Scanner(System.in);
			if(sc!=null) {
			System.out.print("Enter Person id : ");	
			pid = sc.nextInt();
			}
			
			/*
			 * //register JDBC Driver Class.forName("com.mysql.cj.jdbc.Driver"); //Establish
			 * the connection con =
			 * DriverManager.getConnection("jdbc:mysql:///nsaj413","root","root");
			 */
			 
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			 
			//create statement object
			if(con!=null) {
				ps = con.prepareStatement(RETRIVE_DATE_QUERY);
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
				while(rs.next()) {
					flag = true;
					pid = rs.getInt(1);
					name = rs.getString(2);
					sqdob = rs.getDate(3);
					sqdoj = rs.getDate(4);
					sqdom = rs.getDate(5);
					
					//convert java.sql.Date class objects to java.util.Date
					//class objects
					udob = sqdob;
					udoj = sqdoj;
					udom = sqdom;
					
					//Convert java.util.Date class objects to String date
					//vales
					sdf = new SimpleDateFormat("yyyy-MMM-dd");
					sdob = sdf.format(udob);
					sdoj = sdf.format(udoj);
					sdom = sdf.format(udom);
					System.out.println(pid+" "+name+" "+sdob+" "+sdoj+" "+sdom);
				} //while
			}
			if(flag==false) {
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
