package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;
import oracle.jdbc.rowset.OracleJoinRowSet;

public class JoinRowSetTest {

	public static void main(String[] args) {
		
		CachedRowSet crs1=null;
		CachedRowSet crs2=null;
		
		try {
			//create RowSet object
			crs1=new OracleCachedRowSet();
			//set Cached properties
			crs1.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crs1.setUsername("scott");
			crs1.setPassword("tiger");
			crs1.setCommand("select empno, ename, deptno from emp");
			crs1.setMatchColumn(3);
			//execute query
			crs1.execute();
			
			//create RowSet object
			crs2=new OracleCachedRowSet();
			//set Cached properties
			crs2.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crs2.setUsername("scott");
			crs2.setPassword("tiger");
			crs2.setCommand("select deptno, dname, loc from dept");
			crs2.setMatchColumn(1);
			//execute query
			crs2.execute();
			
			//joinRowSet
			OracleJoinRowSet joinRS=new OracleJoinRowSet();
			joinRS.addRowSet(crs2);
			joinRS.addRowSet(crs1);
			
			
			//process the joinRowSet
			while(joinRS.next()) {
				System.out.println(joinRS.getString(1)+" "+joinRS.getString(2)+" "+joinRS.getString(3)+" "+joinRS.getString(4)+" "+joinRS.getString(5));
			}
			crs1.close();
			crs2.close();
			joinRS.close();
		} //try
		catch (SQLException se) {
			se.printStackTrace();
		}
	} //main()
} //class
