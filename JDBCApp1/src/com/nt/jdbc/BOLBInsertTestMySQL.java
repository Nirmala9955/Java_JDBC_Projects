//PsInsertTest.java

package com.nt.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BOLBInsertTestMySQL {
	
	private static final String INSERT_QUERY = "INSERT INTO MATRIMONY_PROFILE (APPNAME, AGE, GENDER, PHOTO) VALUES(?,?,?,?)"; 

	public static void main(String[] args) {
		
		//Declare all the local variable
		Scanner sc = null;
		String name = null;
		int age = 0;
		String gender=null, photoPath=null;
		Connection con = null;
		PreparedStatement ps = null;
		File file = null;
		InputStream is = null;
		long length = 0;
		int result = 0;
		
		try {
			//Read inputs
			sc = new Scanner(System.in);
			if (sc!=null) {
				System.out.print("Enter Applicant Name : ");
				name = sc.next();
				System.out.print("Enter Applicant Age : ");
				age = sc.nextInt();		
				System.out.print("Enter Applicant Gender : ");
				gender = sc.next();
				System.out.print("Enter Applicant Photo Path : ");
				photoPath = sc.next();
				
			} //if(-)
			//Create file object
			file = new File(photoPath);
			length = file.length();
			//Cerate input stream object
			is = new FileInputStream(file);
			
			//register JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///nsaj413", "root","root");
			//Create statement object	
			if (con!=null) {
				ps = con.prepareStatement(INSERT_QUERY);
			} //if(-)
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
		//	ps.setBinaryStream(4, is);
		//	ps.setBinaryStream(4, is, length);
		//	ps.setBlob(4, is, length);
			ps.setBlob(4, is, length);
			
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
		} //finally

	} //main(-)

} //class {}
