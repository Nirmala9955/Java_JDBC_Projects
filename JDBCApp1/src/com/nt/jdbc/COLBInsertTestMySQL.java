//PsInsertTest.java

package com.nt.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*
   CREATE TABLE `nsaj413`.`jobportal_info` (
  `jsid` INT NOT NULL AUTO_INCREMENT,
  `jsname` VARCHAR(20) NULL,
  `address` VARCHAR(10) NULL,
  `qualification` VARCHAR(10) NULL,
  `resume` MEDIUMTEXT NULL,
  PRIMARY KEY (`jsid`));
	 
 */

public class COLBInsertTestMySQL {
	
	private static final String CLOB_INSERT_QUERY = "INSERT INTO JOBPORTAL_INFO (JSNAME, ADDRESS, QUALIFICATION, RESUME) VALUES (?,?,?,?)"; 

	public static void main(String[] args) {
		
		//Declare all the local variable
		Scanner sc = null;
		String name = null, address=null, qlfy=null, resumePath=null;
		Connection con = null;
		PreparedStatement ps = null;
		File file = null;
		Reader reader = null;
		int result = 0;
		
		try {
			//Read inputs
			sc = new Scanner(System.in);
			if (sc!=null) {
				System.out.print("Enter JobSeeker name : ");
				name = sc.next();
				System.out.print("Enter JobSeeker address : ");
				address = sc.next();		
				System.out.print("Enter JobSeeker qlfy : ");
				qlfy = sc.next();
				System.out.print("Enter JobSeeker Resume Path : ");
				resumePath = sc.next();
			} //if(-)
			
			//register JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///nsaj413", "root","root");
			//Create statement object
			if (con!=null) {
				ps = con.prepareStatement(CLOB_INSERT_QUERY);
			} //if(-)
			//Create reader object hold resume(CLOB value)
			file = new File(resumePath);
			//Read the file
			reader = new FileReader(file);
			//Set values to query param
			ps.setString(1, name);
			ps.setString(2, address);
			ps.setString(3, qlfy);
			ps.setCharacterStream(4, reader);
			//Execute Query
			result = ps.executeUpdate();
			if(result==0) {
				System.out.println("Record is not inserted");
			}
			else {
				System.out.println("Record is inserted");
			}
			System.out.println();
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
			try {
				if (reader!=null)
					reader.close();
			}
			catch (IOException ioe){
				ioe.printStackTrace();
			}
		} //finally

	} //main(-)

} //class {}
