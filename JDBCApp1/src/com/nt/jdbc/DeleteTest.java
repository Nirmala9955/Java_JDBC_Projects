package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteTest {

	public static void main(String[] args) {
		
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
		String query = null;
		int no =0;
		int result = 0;
		
		try {
			//Read from keyboard
			sc = new Scanner(System.in);
			System.out.println("Enter Student no to delete : ");
			no = sc.nextInt();
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) {
				st=con.createStatement();
			}
			//Frame the SQL Query 
			//delete from student where slno=106;
			query =  "DELETE FROM STUDENT WHERE SLNO="+no;
			//Send and execute SQL Query in database software
			if(st!=null) {
				result=st.executeUpdate(query);
			}
			//Process the result
			if (result==0)
				System.out.println("No record found for deletion");
			else
				System.out.println(result+" no. of record found for deletion");
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
