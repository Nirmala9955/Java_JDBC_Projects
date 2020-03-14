package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;


public class CS_Get_Employee_DetailsUsingEMPNO {
	
	private static final String CALL_PROCEDURE = "{CALL P_EMPDETAILS_BY_NO(?,?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		int result=0;
		try {
			//Read input data 
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.print("Enter Employee Id : ");
				no = sc.nextInt();			
			}
			//Register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//Prepare CallableStatement Object
			if(con!=null) {
				cs = con.prepareCall(CALL_PROCEDURE);
			}
			if(cs!=null) {
				//Register JDBC type to OUT Param
				cs.registerOutParameter(2, Types.VARCHAR);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.INTEGER);
				//set Value to IN Param
				cs.setInt(1, no);			
				//Execute the PL/SQL Procedure
				cs.execute();
				//geter the Result from OUT param
				System.out.print("Employee Name : "+cs.getString(2)); 
				System.out.print("\nEmployee Desgination : "+cs.getString(3)); 
				System.out.print("\nEmployee Salary : "+cs.getInt(4)); 
			}
		} //try
		catch (SQLException se) {
			se.printStackTrace();
		}
		catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			//CLose all JDBC object
			try {
				if(cs!=null)
					cs.close();
			}
			catch (SQLException  se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch (SQLException  se) {
				se.printStackTrace();
			}
			try {
				if(sc!=null)
					sc.close();
			}
			catch (Exception  e) {
				e.printStackTrace();
			}
		} //finally
	} //main
} //class
