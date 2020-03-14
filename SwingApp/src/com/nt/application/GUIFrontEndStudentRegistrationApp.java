package com.nt.application;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIFrontEndStudentRegistrationApp extends JFrame implements ActionListener,WindowListener {
	private static final String  INSERT_STUDENT_QUERY="INSERT INTO STUDENT VALUES(SLNO_SEQ.NEXTVAL,?,?,?)";
	private  JLabel lname,laddrs,lavg,lresult;
	private JTextField  tname,taddrs,tavg;
	private JButton register;
	private Connection con;
	private PreparedStatement ps;
	
	public GUIFrontEndStudentRegistrationApp() {
		System.out.println(" 0-param constructor");
	     setTitle("GUI-Student Registration App");
	     setBackground(Color.gray);
	     setLayout(new FlowLayout());
	     setSize(400,200);
	     //add comps
	     lname=new JLabel("sname");
	     add(lname);
	     tname=new JTextField(10);
	     add(tname);
	     laddrs=new JLabel("address");
	     add(laddrs);
	     taddrs=new JTextField(10);
	     add(taddrs);
	     lavg=new JLabel("Student marks Avg");
	     add(lavg);
	     tavg=new JTextField(10);
	     add(tavg);
	     register=new JButton("register");
	     add(register);
	     register.addActionListener(this);
	     lresult=new JLabel();
         add(lresult);	    		 
	     //add windowListner
         this.addWindowListener(this);
	     //invoke jdbcInitialize() 
	     jdbcInitialize();
	     
	     //set visiblity to ON
	     setVisible(true);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void  jdbcInitialize() {
		System.out.println("jdbcInitialize()");
		try {
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create PrepraedStatement object
			ps=con.prepareStatement(INSERT_STUDENT_QUERY);
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(" start of main()");
		new GUIFrontEndStudentRegistrationApp();
		System.out.println(" end of main()");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerformed(-)");
		String name=null,addrs=null;
		float avg=0.0f;
		int result=0;
		try {
			//read text box values
			name=tname.getText();
			addrs=taddrs.getText();
			avg=Float.parseFloat(tavg.getText());
			//set these values as query param values
			ps.setString(1,name);
			ps.setString(2,addrs);
			ps.setFloat(3,avg);
			//execute the Query
			result=ps.executeUpdate();
			//process Result
			if(result==1) {
				lresult.setForeground(Color.GREEN);
				lresult.setText("Registration Sucessfull");
			}
			else {
				lresult.setForeground(Color.RED);
			
			}
		}//try
		catch(SQLException se) {
			lresult.setText("Registration failed");
			se.printStackTrace();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			//emtpty text boxes and lresult
			tname.setText(""); tavg.setText("");
			taddrs.setText(""); 
		}
	}//actionPerformed(-)

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("GUIFrontEndStudentRegistrationApp.windowClosing(-)");
		//close jdbc objs
		try {
			if(ps!=null)
				ps.close();
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
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}//class
