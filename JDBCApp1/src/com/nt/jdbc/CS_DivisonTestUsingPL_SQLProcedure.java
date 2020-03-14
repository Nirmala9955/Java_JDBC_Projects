package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;


public class CS_DivisonTestUsingPL_SQLProcedure {
	
	private static final String CALL_DIV_PROCEDURE = "{CALL P_DIV(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		int firstNum=0, secondNum=0;
		Connection con=null;
		CallableStatement cs=null;
		int result=0;
		try {
			//Read input data 
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.print("Enter First number : ");
				firstNum = sc.nextInt();			
				System.out.print("Enter Second number : ");
				secondNum = sc.nextInt();
			}
			//Register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//Prepare CallableStatement Object
			if(con!=null) {
				cs = con.prepareCall(CALL_DIV_PROCEDURE);
			}
			if(cs!=null) {
				//Register JDBC type to OUT Param
				cs.registerOutParameter(3, Types.INTEGER);
				//set Value to IN Param
				cs.setInt(1, firstNum);
				cs.setInt(2, secondNum);
				//Execute the PL/SQL Procedure
				cs.execute();
				//geter the Result from OUT param
				result=cs.getInt(3);
				System.out.print("Divison is : "+result); 
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
