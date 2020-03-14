package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class SavePointTest {

	public static void main(String[] args) {
			
		Connection con=null;
		Statement st=null;
		Savepoint sp=null;
		
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the Connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//Create Statement object
			if(con!=null)
				st=con.createStatement();
			//begin Tx
			con.setAutoCommit(false);
			if(st!=null) {
				//execute query1
				st.executeUpdate("insert into student values(555,'sibaa', 'BAM', 76.8)");
				//create savepoint(named)
				sp=con.setSavepoint("mysp");
				//exeute query2
				st.executeUpdate("update student set address='lathi' where slno=222");
				//rollback upto save point
				con.rollback(sp);
				con.commit();
			} //if
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
		finally {
			//close JDBC objects
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
		}

	}

}
