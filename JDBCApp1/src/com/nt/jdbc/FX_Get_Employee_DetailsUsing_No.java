package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.internal.OracleTypes;
import oracle.jdbc.oracore.OracleType;

/*
CREATE OR REPLACE FUNCTION FX_GET_EMPDETAILS_BY_NO 
(
  NO IN NUMBER 
, NAME OUT VARCHAR2 
, SALARY OUT NUMBER 
) RETURN VARCHAR2 
AS
 DESG VARCHAR(5);
BEGIN
  SELECT ENAME, SAL, JOB INTO NAME, SALARY, DESG FROM EMP WHERE EMPNO=NO;  
  RETURN DESG;
END FX_GET_EMPDETAILS_BY_NO;
*/

public class FX_Get_Employee_DetailsUsing_No {
	
	private static final String CALL_FUNCTION = "{?=call FX_GET_EMPDETAILS_BY_NO(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		boolean flag = false;
		try {
			//Read input data 
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.print("Enter Employee No : ");
				no = sc.nextInt();
			}
			//Register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//Prepare CallableStatement Object
			if(con!=null) {
				cs = con.prepareCall(CALL_FUNCTION);
			}
			if(cs!=null) {
				//Register JDBC type to return param, OUT Param
				cs.registerOutParameter(1, Types.VARCHAR);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.INTEGER);
				//set Value to IN Param
				cs.setInt(2, no);			
				//Execute the PL/SQL Procedure
				cs.execute();
				//geter the Result from OUT param
				System.out.print("Employee Name : "+cs.getString(3));
				System.out.print("\nEmployee Desgination : "+cs.getString(1));
				System.out.print("\nEmployee Salary : "+cs.getInt(4));
			}
		} //try
		catch (SQLException se) {
			if(se.getErrorCode()==1403)
				System.out.println("Record not found");
			else
				System.out.println("Internal problem");
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
