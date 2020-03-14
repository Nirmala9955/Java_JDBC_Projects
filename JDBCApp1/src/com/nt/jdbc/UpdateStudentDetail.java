//UpdateStudentDetail.java

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

public class UpdateStudentDetail {

	public static void main(String[] args) {
		
		//Declare all the local variable
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
		int sno = 0;
		String newAddrs = null;
		float newAvg = 0.0f;
		String query = null;
		int count = 0;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			if (sc!=null) {
				System.out.print("Enter Student No : ");
				sno = sc.nextInt(); 
				System.out.print("Enter Student New Address : ");
				sc.nextLine();
				newAddrs = sc.nextLine(); 
				System.out.print("Enter Student New Avg : ");
				newAvg = sc.nextFloat(); 
			} //if(-)
			//convert input values as required type
			newAddrs = "'"+newAddrs+"'";
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
			// update student_tab2 set address = 'new delhi', avg = '67.5' where slno = 102;
			query = "UPDATE STUDENT_TAB2 SET ADDRESS="+newAddrs+" , AVG="+newAvg+"WHERE SLNO="+sno;
			//Send and execute SQL Query in database software
			if (st!=null) {
				count = st.executeUpdate(query);
			} //if(-)
			//Process the ResultSet
			if (count==0) {
				System.out.println("Record is not found to update");
			} //if(-)
			else {
				System.out.println("Record is found and updated");
			}
			
		} //try
		catch(SQLException se) {	//known Exception
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

} //class
