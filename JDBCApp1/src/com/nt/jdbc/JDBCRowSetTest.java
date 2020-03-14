package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.RowSet;

import oracle.jdbc.rowset.OracleJDBCRowSet;

public class JDBCRowSetTest {

	public static void main(String[] args) {
		try {
			//create RowSet object
			RowSet jrowset=new OracleJDBCRowSet();
			//set JDBC properties
			jrowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			jrowset.setUsername("system");
			jrowset.setPassword("manager");
			jrowset.setCommand("select * from student");
			jrowset.execute();
			System.out.println("command executed");
			//process the RowSet
			while(jrowset.next()) {
				System.out.println(jrowset.getInt(1)+" "+jrowset.getString(2));
			}
			jrowset.close();
		} //try
		catch (SQLException se) {
			se.printStackTrace();
		}
	} //main()
} //classs
