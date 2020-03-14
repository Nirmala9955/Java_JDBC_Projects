//PsInsertTest.java

package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterMetaDataTest {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ParameterMetaData pmd = null;
		int count = 0;
		
		try {
			//Register JDBC driver software
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//Create statement object
			if (con!=null) {
				ps = con.prepareStatement("INSERT INTO STUDENT VALUES(?,?,?,?)");
			} //if(-)
			//create ParameterMetaData object 
			if(ps!=null)
				pmd=ps.getParameterMetaData();
			//Display various details about param
			if(pmd!=null) {
				count=pmd.getParameterCount();
				System.out.println("Parameter count : "+count);
				for(int i=0; i<=count; i++) {
					System.out.println("Parameter number : "+i);
					System.out.println("Parameter mode : "+pmd.getParameterMode(i));
					System.out.println("Parameter type name : "+pmd.getParameterTypeName(i));
					System.out.println("Parameter is signed : "+pmd.isSigned(i));
					System.out.println("Parameter is Nullable : "+pmd.isNullable(i));
					System.out.println("Parameter Scale area : "+pmd.getScale(i));					
					System.out.println("Parameter precision area : "+pmd.getPrecision(i));					
				} //for
			} //if
		} //try
		catch(SQLException se) {	//known Exception
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {	//known Exception
			cnf.printStackTrace();
		}
		catch(Exception e) {	//known Exception
			e.printStackTrace();
		}

		finally {
			//close objects
			
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
		} //finally

	} //main(-)

} //DeleteStudentDetails_From_cityName {}
