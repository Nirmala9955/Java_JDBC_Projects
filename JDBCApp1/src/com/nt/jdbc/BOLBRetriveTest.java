package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BOLBRetriveTest {
	
	private static final String GET_DETAILS_QUERY ="SELECT APPID, APPNAME, AGE, GENDER, PHOTO FROM MATRIMONY_PROFILE WHERE APPID=?";
	
	public static void main(String[] args) {
		Scanner sc = null;
		int id = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = null;
		int bytesRead = 0;
		try {
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Applicant id : ");
				id = sc.nextInt();
			}
			//Register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			
//			//register JDBC Driver
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			//Establish the connection
//			con = DriverManager.getConnection("jdbc:mysql:///nsaj413", "root","root");

			
			//Create PrepareStatement object
			if(con!=null)
				ps = con.prepareStatement(GET_DETAILS_QUERY);
			//Set param value
			ps.setInt(1, id);
			//execute the SQL Query
			if(ps!=null)
				rs = ps.executeQuery();
			//process the resultset (read BLOB value)
			if(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(3));
				is = rs.getBinaryStream(4);
			} 
			else {
				System.out.println("Record not found");
				return;
			}
			//Create outputStream for Dest file
			os = new FileOutputStream("E:\\JAVA\\02AdvancedJava\\02JDBC\\WorkSpace\\Downloads\\image.jpg");
			//write buffer based logic to copy file content 
			buffer = new byte[4096];
			while((bytesRead = is.read(buffer))!=-1) {
				os.write(buffer,0,bytesRead);
			} //while
			System.out.println("Image retrive successfully");
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
				if (rs!=null)
					rs.close();
			}
			catch (SQLException se){
				se.printStackTrace();
			}
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
