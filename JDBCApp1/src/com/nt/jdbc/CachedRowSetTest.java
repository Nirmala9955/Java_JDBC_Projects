package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

public class CachedRowSetTest {

	public static void main(String[] args) {
		
		CachedRowSet crowset=null;
		
		try {
			//create RowSet object
			crowset=new OracleCachedRowSet();
			//set Cached properties
			crowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset.setUsername("system");
			crowset.setPassword("manager");
			crowset.setCommand("select * from student");
			//execute query
			crowset.execute();
			//process the RowSet
			System.out.println("before modification");
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+" "+crowset.getString(2)+" "+crowset.getString(3)+" "+crowset.getFloat(4));
			}
			
			Thread.sleep(30000); //stop DB s/w here
			crowset.absolute(3);
			crowset.updateInt(1, 5677);
			crowset.updateString(2, "ramesh");
			crowset.updateString(3, "AP");			
			crowset.updateFloat(4, 89.6f);
			crowset.updateRow();
			System.out.println("Rowset updated in offline mood");
			Thread.sleep(30000); //restart DB s/w here
			crowset.acceptChanges();
			
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+" "+crowset.getString(2)+" "+crowset.getString(3)+" "+crowset.getFloat(4));
			}
			System.out.println("after modification");
			crowset.close();
		} //try
		catch (SQLException | InterruptedException se) {
			se.printStackTrace();
		}
	} //main()
} //class
