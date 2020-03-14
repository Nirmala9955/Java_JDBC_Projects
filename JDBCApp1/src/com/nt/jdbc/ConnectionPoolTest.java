package com.nt.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

public class ConnectionPoolTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		OracleConnectionPoolDataSource ds=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		
		try {
			//Create DataSource object representing empty JDBC connection pool
			ds = new OracleConnectionPoolDataSource();
			if(ds!=null) {
				//set JDBC properties to DataSource object to create con object in the Connection pool
				ds.setDriverType("thin");
				ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
				ds.setUser("system");
				ds.setPassword("manager");
				//get con object from JDBC con pool through DataSource object
				con=ds.getConnection();
			} //if
			//create Statement object
			if(con!=null)
				st=con.createStatement();
			//send and execute SQL Query
			if(st!=null)
				rs=st.executeQuery("select * from student");
			//process the ResultSet
			if(rs!=null) {
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
				} //while
			} //if
		} //try
		catch (SQLException se) {
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//close JDBC objects
			try {
				if(rs!=null)
					rs.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if(ds!=null)
					ds.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

}
