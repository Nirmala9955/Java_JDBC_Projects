package com.nt.application;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/* 
  CREATE TABLE "SYSTEM"."ALL_STUDENT" 
   ("SNO" NUMBER NOT NULL ENABLE, 
	"COLUMN2" VARCHAR2(20 BYTE), 
	"M1" NUMBER, 
	"M2" NUMBER, 
	"M3" NUMBER, 
	CONSTRAINT "ALL_STUDENT_PK" PRIMARY KEY ("SNO"));
 */

public class GUI_Using_All_StatementObjects extends JFrame implements ActionListener{
	
	private static final String GET_SNO_QUERY = "SELECT SNO FROM ALL_STUDENT";
	private static final String GET_STUDENT_DETAILS = "SELECT SNAME, M1, M2, M3 FROM ALL_STUDENT WHERE SNO=?";
	private static final String FIND_RESULT_QUERY = "{CALL P_FIND_PASS_FAIL(?,?,?,?)}";
	
	private JLabel lno, lname, lm1, lm2, lm3, lresult;
	private JTextField tname, tm1, tm2, tm3, tresult;
	private JButton bdetails, bresult;
	private JComboBox cmbno;
	private Connection con;
	private Statement st;
	private ResultSet rs1, rs2;
	private PreparedStatement ps;
	private CallableStatement cs;
	
	public GUI_Using_All_StatementObjects() {
		System.out.println("GUI_Using_All_StatementObjects.GUI_Using_All_StatementObjects()");
		setTitle("Mini Project");
		setSize(250, 220);
		setLayout(new FlowLayout());
		setBackground(Color.GRAY);
		
		//Add Components
		lno = new JLabel("Student ID ");
		add(lno);
		cmbno = new JComboBox();
		add(cmbno);
		bdetails = new JButton("Details");
		bdetails.addActionListener(this);
		add(bdetails);
		
		lname = new JLabel("Student Name ");
		add(lname);
		tname = new JTextField(10);
		add(tname);
		lm1 = new JLabel("Student Mark1");
		add(lm1);
		tm1 = new JTextField(10);
		add(tm1);		
		lm2 = new JLabel("Student Mark2");
		add(lm2);
		tm2 = new JTextField(10);
		add(tm2);
		lm3 = new JLabel("Student Mark3");
		add(lm3);
		tm3 = new JTextField(10);
		add(tm3);
		bresult = new JButton("Result");
		bresult.addActionListener(this);
		add(bresult);
		
		lresult =  new JLabel();
		add(lresult);
		
		//disable editing of comps
		tname.setEditable(false);
		tm1.setEditable(false);
		tm2.setEditable(false);
		tm3.setEditable(false);
		
		setVisible(true);
		loadItems();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void loadItems() {
		System.out.println("GUI_Using_All_StatementObjects.loadItems()");
		try {
			//register Driver S/W
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			
			//create statement object
			if(con!=null) {
				st=con.createStatement();
			}
			//execute the Query 
			if(st!=null)
				rs1=st.executeQuery(GET_SNO_QUERY);
			//copy resultset sno values to combobox
			if(rs1!=null) {
				while(rs1.next())
					cmbno.addItem(rs1.getInt(1));
			} //while
			
			//Create PreparedStatement obj
			if(con!=null);
			ps=con.prepareStatement(GET_STUDENT_DETAILS);
			
			//Create CallableStatement object
			if(con!=null) {
				cs=con.prepareCall(FIND_RESULT_QUERY);
				cs.registerOutParameter(4, Types.VARCHAR);
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
	} //loadItems()

	public static void main(String[] args) {
		System.out.println("GUI_Using_All_StatementObjects.main()");
		new GUI_Using_All_StatementObjects();

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		int m1=0, m2=0, m3=0;
		String result=null;
		System.out.println("GUI_Using_All_StatementObjects.actionPerformed()");
		if(ae.getSource()==bdetails) {
			System.out.println("Details BTN is clicked");
			try {
				//get the selected value from combox
				int no = (Integer)cmbno.getSelectedItem();
				//set value to Query param
				if(ps!=null) {
					ps.setInt(1, no);
					//execute the Query 
					rs2=ps.executeQuery();
				}
				//set ResultSet object record to textBoxs
				if(rs2!=null) {
					if(rs2.next()) {
						tname.setText(rs2.getString(1));
						tm1.setText(rs2.getString(2));
						tm2.setText(rs2.getString(3));
						tm3.setText(rs2.getString(4));
					} //if
				} //if
			} //try
			catch (SQLException se) {
				se.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		} //if
		else {
			System.out.println("Result btn is cliked");
			try {
				//read text box values (m1, m2, m3)
				m1=Integer.parseInt(tm1.getText());
				m2=Integer.parseInt(tm2.getText());
				m3=Integer.parseInt(tm3.getText());
				
				//set Values to IN params
				if(cs!=null) {
					cs.setInt(1, m1);
					cs.setInt(2, m2);
					cs.setInt(3, m3);
					//execute PL/SQL Procedure 
					cs.execute();
					//gather value from out param 
					result=cs.getString(4);
					//set result to Label
					lresult.setText(result);
				} //if
			} //try
			catch (SQLException se) {
				se.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} //else	
	} //actionPerformed()
	
	private class MyWindowAdapter extends  WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			 System.out.println("GUIFrontEndStudentRegistrationApp1.MyWindowAdapter.windowClosing(-)");
			 try {
				 if(rs1!=null)
					 rs1.close();
			 }
			 catch(SQLException se) {
				 se.printStackTrace();
			 }
			 try {
				 if(rs2!=null)
					 rs2.close();
			 }
			 catch(SQLException se) {
				 se.printStackTrace();
			 }
			 try {
				 if(cs!=null)
					 cs.close();
			 }
			 catch(SQLException se) {
				 se.printStackTrace();
			 }
			 try {
				 if(st!=null)
					 st.close();
			 }
			 catch(SQLException se) {
				 se.printStackTrace();
			 }
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
		
	} //MyWindowAdapter {}
	
} //class 
