package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;


public class CS_SquareCubeTestUsingPL_SQLProcedure {
	
	private static final String CALL_SQUARE_CUBE_PROCEDURE = "{CALL P_SQUARE_CUBE(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		int firstNum=0;
		Connection con=null;
		CallableStatement cs=null;
		int result1=0, result2=0;
		try {
			//Read input data 
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.print("Enter a number : ");
				firstNum = sc.nextInt();			
			}
			//Register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//Prepare CallableStatement Object
			if(con!=null) {
				cs = con.prepareCall(CALL_SQUARE_CUBE_PROCEDURE);
			}
			if(cs!=null) {
				//Register JDBC type to OUT Param
				cs.registerOutParameter(2, Types.INTEGER);
				cs.registerOutParameter(3, Types.INTEGER);
				//set Value to IN Param
				cs.setInt(1, firstNum);
				//Execute the PL/SQL Procedure
				cs.execute();
				//geter the Result from OUT param
				result1=cs.getInt(2);
				result2=cs.getInt(3);
				System.out.print("Square is : "+result1); 
				System.out.print("\nCube is : "+result2); 
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
