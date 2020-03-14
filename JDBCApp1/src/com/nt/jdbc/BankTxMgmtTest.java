package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BankTxMgmtTest {

	public static void main(String[] args) {
		
		Scanner sc=null;
		int srcNo=0, destNo=0, amt=0;
		Connection con = null;
		Statement st = null;
		int result[]=null;
		int rowEffect=0;
		boolean flag=false;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Source Account No : ");
				srcNo=sc.nextInt();
				System.out.println("Enter Dest Account No : ");
				destNo=sc.nextInt();
				System.out.println("Enter Amount to Transfer : ");
				amt=sc.nextInt();
			} //if
			//Register JDBC driver Software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//Begin Transaction
			if(con!=null) { 
				con.setAutoCommit(false);
				//create statement object
				st=con.createStatement();
			}
			//add queries to the batch
			if(st!=null) {
				//add queries to 
				
				//execute batch
				result=st.executeBatch();
			}
			//Process the Result
			if(result!=null) {
				for(int i=0;i<result.length;++i) {
					if(result[i]==0) {
						flag=true;
						break;
					} //if
				} //for
				if(flag==true) {
					con.rollback();
					System.out.println("Tx Rolledback");
				} else {
					con.commit();
					System.out.println("Tx Committed");
				}
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
