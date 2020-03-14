//InsertStudentDetail.java

package com.nt.jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.util.Scanner;

/* App to insert Student details 
 * version: 1.0
 * author : Nirmala
 * Date   : 03-12-2019
 */

public class InsertStudentDetail {

	public static void main(String[] args) {
		
		//Declare all the local variable
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
		int sno = 0;
		String sname=null, addrs = null;
		float avg = 0.0f;
		int count = 0;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			if (sc!=null) {
				System.out.print("Enter Student No : ");
				sno = sc.nextInt(); 
				System.out.print("Enter Student Name : ");
				sname = sc.next(); 
				System.out.print("Enter Student Address : ");
				addrs = sc.next(); 
				System.out.print("Enter Student Avg : ");
				avg = sc.nextFloat(); 
			} //if(-)
			//convert input values as required type
			sname = "'"+sname+"'";
			addrs = "'"+addrs+"'";
			System.out.println();
			//Register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
			//Create statement object
			if (con!=null) {
				st = con.createStatement();
			} //if(-)
			//Frame the SQL Query
			// insert into student_tab2 values(105,'nimu','hinjilicut',97.3);
			String query = "INSERT INTO STUDENT_TAB2 VALUES("+sno+","+sname+","+addrs+","+avg+")";
			//Send and execute SQL Query in database software
			if (st!=null) {
				count = st.executeUpdate(query);
			} //if(-)
			//Process the ResultSet
			if (count!=0) {
				System.out.println("Records insertion successed");
			} //if(-)
			
		} //try
		catch(SQLException se) {	//known Exception
			if(se.getErrorCode()==1) {
				System.out.println("can't insert duplicates in unique, pk cols");
			} else if(se.getErrorCode()==12899) {
				System.out.println("Value is too large than column size");
			} else if(se.getErrorCode()>=900 && se.getErrorCode()<=1000) {
				System.out.println("Query syntax problem");
			}
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {	//known Exception
			cnf.printStackTrace();
			System.out.println("Internal problem");
		}
		catch(Exception e) {	//known Exception
			e.printStackTrace();
			System.out.println("Internal problem");
		}

		finally {
			//close objects
			try {
				if (st!=null)
					st.close();
			}
			catch (SQLException se){
				se.printStackTrace();
			}
			try {
				if (con!=null)
					con.close();
			}
			catch (SQLException se){
				se.printStackTrace();
			}
			try {
				if (sc!=null)
					sc.close();
			}
			catch (Exception se){
				se.printStackTrace();
			}
		} //finally

	} //main(-)

} //DeleteStudentDetails_From_cityName {}
