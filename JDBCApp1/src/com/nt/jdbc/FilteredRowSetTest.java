package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;

import oracle.jdbc.rowset.OracleFilteredRowSet;

class FilterTest implements Predicate{
	
	private String colName;
	
	public FilterTest(String colName) {
		this.colName=colName;
	}	

	@Override
	public boolean evaluate(RowSet rs) {
		System.out.println("FilterTest.evaluate()");
		try {
			CachedRowSet crs=(CachedRowSet)rs;
			String object = crs.getString(colName);
			if(object!=null && (object.charAt(0)=='A'||object.charAt(0)=='a')) {
				return true;
			} else {
				return false;
			}  
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		return false;
	}

	@Override
	public boolean evaluate(Object value, String columnName) throws SQLException {
		return false;
	}
	
} //FilterTest class

public class FilteredRowSetTest {

	public static void main(String[] args) throws SQLException {
		// create Filtered RowSet
		OracleFilteredRowSet frs=new OracleFilteredRowSet();
		frs.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		frs.setUsername("system");
		frs.setPassword("manager");
		frs.setCommand("select * from emp");
		
		frs.setFilter(new FilterTest("ename"));
		frs.execute();
		while(frs.next()) {
			System.out.println(frs.getInt(1)+" "+frs.getString(2)+" "+frs.getString(3));		
		}
	} //main
} //class
