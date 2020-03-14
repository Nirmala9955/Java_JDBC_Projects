package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BatchProcessTest {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement st = null;
		int result[]=null;
		int rowEffect=0;
		try {
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create statement object
			if(con!=null) 
				st=con.createStatement();
			//add queries to the batch
			if(st!=null) {
				st.addBatch("insert into student values(1213,'hari','hyd',89.0)");
				st.addBatch("update student set address='hyd'where slno>500");
				st.addBatch("delete from student where slno>500");
				
				//execute batch
				result=st.executeBatch();
			}
			//Process the Result
			if(result!=null) {
				for(int i=0;i<result.length;++i) {
					rowEffect=rowEffect+result[i];
				} //for
				System.out.println("No. of records are effected : "+rowEffect);
			} //if
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
				if(st!=null)
					st.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
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
