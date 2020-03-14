//PsInsertTest.java

package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/* App to insert Student details 
 * version: 2.0
 * author : Nirmala
 * Date   : 12-12-2019
 */

public class PsInsertTest {
	
	private static final String INSERT_STUDENT_QUERY = "INSERT INTO STUDENT VALUES(?,?,?,?)"; 

	public static void main(String[] args) {
		
		//Declare all the local variable
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		int sno = 0;
		String sname=null, addrs = null;
		float avg = 0.0f;
		int count = 0;
		int result = 0;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			if (sc!=null) {
				System.out.print("Enter Student count : ");
				count = sc.nextInt(); 
			} //if(-)
			//Register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//Create statement object
			if (con!=null) {
				ps = con.prepareStatement(INSERT_STUDENT_QUERY);
			} //if(-)
			//Send and execute SQL Query in database software
			if (ps!=null && sc!=null) {
				for (int i=1; i<=count; i++) {
					System.out.println("Enter "+i+" Student Details");
					System.out.print("Enter Student No : ");
					sno = sc.nextInt(); 
					System.out.print("Enter Student Name : ");
					sname = sc.next(); 
					System.out.print("Enter Student Address : ");
					addrs = sc.next(); 
					System.out.print("Enter Student Avg : ");
					avg = sc.nextFloat(); 
					
					//set the input values read from enduser to query params
					ps.setInt(1, sno);
					ps.setString(2, sname);
					ps.setString(3, addrs);
					ps.setFloat(4, avg);
					
					//Execute Query
					result = ps.executeUpdate();
					if(result==0) {
						System.out.println(i+" student details are not inserted");
					}
					else {
						System.out.println(i+" student details are inserted");
					}
					System.out.println();
				}//for
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
				if (ps!=null)
					ps.close();
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
