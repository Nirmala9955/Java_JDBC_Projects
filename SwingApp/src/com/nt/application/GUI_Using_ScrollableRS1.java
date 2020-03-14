package com.nt.application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class GUI_Using_ScrollableRS1 {
	
	private static final String GET_STUDENT_DETAILS="SELECT SLNO, SNAME, ADDRESS, AVG FROM STUDENT";

	private JFrame frmScrollableNavigation;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Using_ScrollableRS1 window = new GUI_Using_ScrollableRS1();
					window.frmScrollableNavigation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Using_ScrollableRS1() {
		initialize();
		makeConnection();
	}

	private void makeConnection() {
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
		
	} //makeConnection()

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmScrollableNavigation = new JFrame();
		frmScrollableNavigation.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 13));
		frmScrollableNavigation.getContentPane().setBackground(SystemColor.activeCaption);
		frmScrollableNavigation.setResizable(false);
		frmScrollableNavigation.setTitle("Scrollable Navigation");
		frmScrollableNavigation.getContentPane().setName("Scrollable Navigation App");
		frmScrollableNavigation.setBounds(100, 100, 354, 218);
		frmScrollableNavigation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmScrollableNavigation.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(173, 27, 110, 20);
		frmScrollableNavigation.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Student No : ");
		lblNewLabel.setBounds(58, 33, 80, 14);
		frmScrollableNavigation.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Student Name : ");
		lblNewLabel_1.setBounds(58, 58, 91, 14);
		frmScrollableNavigation.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(173, 52, 110, 20);
		frmScrollableNavigation.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Student Address : ");
		lblNewLabel_2.setBounds(58, 83, 105, 14);
		frmScrollableNavigation.getContentPane().add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(173, 77, 110, 20);
		frmScrollableNavigation.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(173, 102, 110, 20);
		frmScrollableNavigation.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Student Average : ");
		lblNewLabel_3.setBounds(58, 108, 105, 14);
		frmScrollableNavigation.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("First");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					rs.first();
					textField.setText(rs.getString(1));
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
				}
				catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 150, 74, 23);
		frmScrollableNavigation.getContentPane().add(btnNewButton);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!rs.isLast())
						rs.next();
						textField.setText(rs.getString(1));
						textField_1.setText(rs.getString(2));
						textField_2.setText(rs.getString(3));
						textField_3.setText(rs.getString(4));
				}
				catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});
		btnNext.setBounds(94, 150, 73, 23);
		frmScrollableNavigation.getContentPane().add(btnNext);
		
		JButton btnPreivous = new JButton("Preivous");
		btnPreivous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!rs.isFirst())
						rs.previous();
						textField.setText(rs.getString(1));
						textField_1.setText(rs.getString(2));
						textField_2.setText(rs.getString(3));
						textField_3.setText(rs.getString(4));
				}
				catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});
		btnPreivous.setBounds(177, 150, 82, 23);
		frmScrollableNavigation.getContentPane().add(btnPreivous);
		
		JButton btnLast = new JButton("Last");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rs.last();
					textField.setText(rs.getString(1));
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
				}
				catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});
		btnLast.setBounds(269, 150, 69, 23);
		frmScrollableNavigation.getContentPane().add(btnLast);
	}
}
