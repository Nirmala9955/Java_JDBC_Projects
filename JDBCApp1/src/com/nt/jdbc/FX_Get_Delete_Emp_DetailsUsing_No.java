package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.internal.OracleTypes;

/*
CREATE OR REPLACE FUNCTION FX_GET_STUDENT_FOR_DELETION 
(
  NO IN NUMBER 
, COUNT OUT NUMBER 
) RETURN SYS_REFCURSOR AS 
 DETAILS SYS_REFCURSOR;
BEGIN
  OPEN DETAILS FOR
    SELECT SLNO, SNAME, ADDRESS, AVG FROM STUDENT WHERE SLNO=NO;
  
  DELETE FROM STUDENT WHERE SLNO=NO;
  COUNT:=SQL%ROWCOUNT;
  RETURN DETAILS;
END FX_GET_STUDENT_FOR_DELETION;
*/

public class FX_Get_Delete_Emp_DetailsUsing_No {
	
	private static final String CALL_FUNCTION = "{?=call FX_GET_STUDENT_FOR_DELETION(?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
		ResultSet rs = null;
		int result=0;
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
				cs.registerOutParameter(1, OracleTypes.CURSOR);
				cs.registerOutParameter(3, Types.INTEGER);
				cs.setInt(2, no);			
				//Execute the PL/SQL Procedure
				cs.execute();
				//geter the Result from OUT param
				rs = (ResultSet) cs.getObject(1);
			
				//process ResultSet 
				if(rs!=null) {
					if(rs.next())
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4) );
				}//if
				result=cs.getInt(3);
				if(result==0)
					System.out.println("Record not found for delete");
				else 
					System.out.println("Record found and deleted");
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
