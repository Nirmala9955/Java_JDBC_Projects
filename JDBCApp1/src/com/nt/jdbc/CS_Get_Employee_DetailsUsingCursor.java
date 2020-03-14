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
CREATE OR REPLACE PROCEDURE P_GET_EMPDETAILS_BY_NAME 
(
  INITCHAR IN VARCHAR2 
, DETAILS OUT NOCOPY SYS_REFCURSOR 
) AS 
BEGIN
  OPEN DETAILS FOR
  SELECT EMPNO, ENAME, JOB, SAL FROM EMP WHERE ENAME=INITCHAR;
END P_GET_EMPDETAILS_BY_NAME;
*/

public class CS_Get_Employee_DetailsUsingCursor {
	
	private static final String CALL_PROCEDURE = "{CALL P_GET_EMPDETAILS_BY_NAME(?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		String initialChars = null;
		Connection con=null;
		CallableStatement cs=null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//Read input data 
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.print("Enter Employee initial characters : ");
				initialChars = sc.next().toUpperCase()+"%";
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
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				//set Value to IN Param
				cs.setString(1, initialChars);			
				//Execute the PL/SQL Procedure
				cs.execute();
				//geter the Result from OUT param
				rs = (ResultSet) cs.getObject(2);
			}
			if(rs!=null) {
				while(rs.next()) {
					flag = true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				}
				if(flag=false)
					System.out.println("No record found");
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
