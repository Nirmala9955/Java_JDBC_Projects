package com.nt.application;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI_Using_ScrollableRS extends JFrame implements ActionListener{

	private static final String GET_STUDENT_DETAILS = "SELECT SLNO, SNAME, ADDRESS FROM STUDENT";
	
	private JLabel lno, lname, laddress;
	private JTextField tno, tname, taddress;
	private JButton bfist, blast, bnext, bprevious;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	//Constructor
	public GUI_Using_ScrollableRS() {
		System.out.println("GUI_Using_ScrollableRS.GUI_Using_ScrollableRS()");
		setTitle("ScrollFrame Navigation App");
		setSize(300, 150);
		setLayout(new FlowLayout());
		
		//add comps
		lno=new JLabel("Student No : ");
		add(lno);
		tno=new JTextField(10);
		add(tno);
		lname=new JLabel("Student Name : ");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		laddress=new JLabel("Student Address : ");
		add(laddress);
		taddress=new JTextField(10);
		add(taddress);
		bfist=new JButton("First");
		bfist.addActionListener(this);
		add(bfist);
		blast=new JButton("Last");
		blast.addActionListener(this);
		add(blast);
		bnext=new JButton("Next");
		bnext.addActionListener(this);
		add(bnext);
		bprevious=new JButton("Previous");
		bprevious.addActionListener(this);
		add(bprevious);
		
		setVisible(true);
		makeConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new MyWindowAdapter());
		
	}
	
	private void makeConnection() {
		System.out.println("GUI_Using_ScrollableRS.makeConnection()");
		try {
			//Register JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//Create PreparedStatement Object
			if(con!=null)
				ps=con.prepareStatement(GET_STUDENT_DETAILS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//create Scrollable ResultSet object
			if(ps!=null)
				rs=ps.executeQuery();
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
		
	} //makeConnection();

	public static void main(String[] args) {
		System.out.println("GUI_Using_ScrollableRS.main()");
		new GUI_Using_ScrollableRS();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println(ae.getActionCommand());
		System.out.println("GUI_Using_ScrollableRS.actionPerformed()");
		boolean flag=false;
		if(rs!=null)
			try {
				if(ae.getSource()==bfist) {
					System.out.println("first button");
					rs.first();
					flag=true;
				}
				else if (ae.getSource()==blast) {
					System.out.println("last button");
					rs.last();
					flag=true;
				}
				else if (ae.getSource()==bnext) {
					System.out.println("next button");
					if(!rs.isLast())
						rs.next();
						flag=true;
				}
				else {
					System.out.println("previous button");
					if(!rs.isFirst())
						rs.previous();
						flag=true;
				} //else
				//set record value to text box
				if(flag=true) {
					tno.setText(rs.getString(1));
					tname.setText(rs.getString(2));
					taddress.setText(rs.getString(3));
				}
		
			} //try
			catch (SQLException se) {
				se.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
	} //actionPerformed()
	
	private class MyWindowAdapter extends WindowAdapter{
		
		@Override
		public void windowClosing(WindowEvent e) {
		
		} //windowClosing(-)

	} //MyWindowAdapter {}
	
} //class