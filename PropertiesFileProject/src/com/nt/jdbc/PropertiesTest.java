package com.nt.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) {
		
		InputStream is = null;
		Properties props = null;
		try {
			//locate fiel using inputStream
			is=new FileInputStream("src/com/nt/commons/personalDetails.properties");
			//load Properties file data into java.util.properties class object
			props=new Properties();
			props.load(is);
			System.out.println("Properties Data : "+props);
			System.out.println("Name key value : "+props.getProperty("name"));
			System.out.println("Age key value : "+props.getProperty("age"));
			System.out.println("Address key value : "+props.getProperty("address"));
		} //try
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	} //main
} //class
