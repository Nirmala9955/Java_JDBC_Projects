package com.nt.jdbc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.sql.rowset.WebRowSet;

import oracle.jdbc.rowset.OracleWebRowSet;

public class WebRowSetTest {

	public static void main(String[] args) {
		
		WebRowSet webRS=null;
		
		try {
			//create RowSet object
			webRS=new OracleWebRowSet();
			//set Cached properties
			webRS.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			webRS.setUsername("system");
			webRS.setPassword("manager");
			webRS.setCommand("select * from student");
			//execute query
			webRS.execute();
			
			//create or locate file
			File myfile= new File("E:\\JAVA\\02AdvancedJava\\02JDBC\\WorkSpace\\Downloads\\student.xml");
			FileWriter fw=new FileWriter(myfile);
			System.out.println("Writing Database data to file "+myfile.getAbsolutePath());
			webRS.writeXml(fw);  //writes the records of Rowset to file
			
			//convert xml to a String object for display purpose
			StringWriter sw = new StringWriter();
			System.out.println(sw.toString());
			fw.flush();
			fw.close();
			
		} //try
		catch (SQLException se) {
			se.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} //main()
} //class
