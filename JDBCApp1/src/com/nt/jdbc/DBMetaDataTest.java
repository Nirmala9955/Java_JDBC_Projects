package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMetaDataTest {

	public static void main(String[] args) {
		
		Connection con = null;
		DatabaseMetaData dbmd = null;
		
		try {
			
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			
			//get metadata
			if(con!=null)
				dbmd=con.getMetaData();
			
			//The Database details are
			if(dbmd!=null) {
				System.out.println("Database Name : "+dbmd.getDatabaseProductName());
				System.out.println("Database Version : "+dbmd.getDatabaseProductVersion());
				System.out.println("Database Major Version : "+dbmd.getDatabaseMajorVersion());
				System.out.println("Database Minor Version : "+dbmd.getDatabaseMinorVersion());	
				System.out.println();
				System.out.println("JDBC Major Version : "+dbmd.getJDBCMajorVersion());
				System.out.println("JDBC Minor Version : "+dbmd.getJDBCMinorVersion());
				System.out.println("JDBC Driver Major Version : "+dbmd.getDriverMajorVersion());
				System.out.println("JDBC Driver Minor Version : "+dbmd.getDriverMinorVersion());
				System.out.println();
				System.out.println("All SQL Keywords : "+dbmd.getSQLKeywords());
				System.out.println("All numeric functions : "+dbmd.getNumericFunctions());
				System.out.println("All system functions : "+dbmd.getSystemFunctions());
				System.out.println("All String functions : "+dbmd.getStringFunctions());
				System.out.println();
				System.out.println("Max Table Name length : "+dbmd.getMaxTableNameLength());
				System.out.println("Max column Name length : "+dbmd.getMaxColumnNameLength());
				System.out.println("Max Row size : "+dbmd.getMaxRowSize());
				System.out.println("Max Columns in select Query : "+dbmd.getMaxColumnsInSelect());
				System.out.println("Max Columns in Database table : "+dbmd.getMaxColumnsInTable());
				System.out.println("Max Connection in Database : "+dbmd.getMaxConnections());		
			}
			
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
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		} //finally
	} //main
} //class
