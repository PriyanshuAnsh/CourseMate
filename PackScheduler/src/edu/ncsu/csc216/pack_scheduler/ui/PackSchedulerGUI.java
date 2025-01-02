package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Main GUI for the PackScheduler project.  It controls authentication
 * and manages the panels for each user.
 * @author Sarah Heckman
 */
public class PackSchedulerGUI {
	
	/** JFrame for the GUI */
	private static JFrame gui;
	/** WolfSchedulerGUI title */
	private static final String APP_TITLE = "PackScheduler";
	/** Constant to identify LoginPanel */
	private static final String LOGIN_PANEL = "LoginPanel";
	/** Constant to identify StudentDirectoryPanel */
	private static final String STUDENT_PANEL = "StudentPanel";
	/** Constant to identify FacultyPanel */
	private static final String FACULTY_PANEL = "FacultyPanel";
	/** Constant to identify CourseCatalogPanel */
	private static final String REGISTRAR_PANEL = "RegistrarPanel";
	/** LoginPanel */
	private LoginPanel pnlLogin;
	/** RegistrarPanel */
	private RegistrarPanel pnlRegistrar;
	/** StudentPanel */
	private StudentPanel pnlStudent;
	/** FacultyPanel */
	private FacultyPanel pnlFaculty;
	/** CardLayout for GUI */
	private CardLayout cardLayout;
	/** Panel that will contain all of the application views */
	private JPanel panel;
	
	/**
	 * Constructs the PackSchedulerGUI and creates the menus and panels.
	 */
	public PackSchedulerGUI() {
		gui = new JFrame();
		gui.setSize(800, 800);
		gui.setLocation(50, 50);
		gui.setTitle(APP_TITLE);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pnlLogin = new LoginPanel();
		pnlRegistrar = new RegistrarPanel();
		pnlStudent = new StudentPanel();
		pnlFaculty = new FacultyPanel();
		
		panel = new JPanel();
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);
		panel.add(pnlLogin, LOGIN_PANEL);
		panel.add(pnlRegistrar, REGISTRAR_PANEL);
		panel.add(pnlStudent, STUDENT_PANEL);
		panel.add(pnlFaculty, FACULTY_PANEL);
		cardLayout.show(panel, LOGIN_PANEL);
		
		Container c = gui.getContentPane();
		c.add(panel, BorderLayout.CENTER);
			
		gui.setVisible(true);
	}
	
	/**
	 * Starts the Pack Scheduler program.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new PackSchedulerGUI();
	}
	
	/**
	 * Creates a panel for user authentication into the system.
	 * @author SarahHeckman
	 */
	private class LoginPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** JLabel for id */
		private JLabel lblId;
		/** JTextField for id */
		private JTextField txtId;
		
		/** JLabel for password */
		private JLabel lblPassword;
		/** JTextField for password */
		private JPasswordField txtPassword;
		
		/** JButton to Login */
		private JButton btnLogin;
		/** JButton to Clear */
		private JButton btnClear;
		
		/**
		 * Constructs the LoginPanel.
		 */
		public LoginPanel() {
			super(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Create ID components
			lblId = new JLabel("User ID:");
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblId, c);
			
			txtId = new JTextField(20);
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(txtId, c);
			
			//Create Password components
			lblPassword = new JLabel("Password:");
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(lblPassword, c);
			
			txtPassword = new JPasswordField(20);
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(txtPassword, c);
			
			//Create Buttons
			btnClear = new JButton("Clear");
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnClear, c);
			
			btnLogin = new JButton("Login");
			c.gridx = 1;
			c.gridy = 2;
			c.gridwidth = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(btnLogin, c);
						
			//Add ActionListeners
			btnLogin.addActionListener(this);
			btnClear.addActionListener(this);
		}

		/**
		 * Performs actions when any component with an action listener is selected.
		 * @param e ActionEvent representing the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnLogin) {
				String id = txtId.getText();
				String password = new String(txtPassword.getPassword());
				
				RegistrationManager manager = RegistrationManager.getInstance();
				try {
				if (manager.login(id, password)) {
					txtId.setText("");
					txtPassword.setText("");
					if (manager.getCurrentUser() instanceof Student) {
						cardLayout.show(panel, STUDENT_PANEL);
						pnlStudent.updateTables();
					} else if (manager.getCurrentUser() instanceof Faculty) {
						cardLayout.show(panel, FACULTY_PANEL );
					} else {
						cardLayout.show(panel, REGISTRAR_PANEL);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Invalid id and password.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getSource() == btnClear) {
				txtId.setText("");
				txtPassword.setText("");
			}
		}
		
	}
	
	/**
	 * Creates a panel for user authentication into the system.
	 * @author SarahHeckman
	 */
	private class RegistrarPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Constant to identify StudentDirectoryPanel */
		private static final String STUDENT_DIRECTORY_PANEL = "StudentDirectoryPanel";
		/** Constant to identify FacultyDirectoryPanel */
		private static final String FACULTY_DIRECTORY_PANEL = "FacultyDirectoryPanel";
		/** Constant to identify CourseCatalog */
		private static final String COURSE_CATALOG_PANEL = "CourseCatalogPanel";
		/** Constant to identify InstructorAssignmentPanel */
		private static final String INSTRUCTOR_ASSIGNMENT_PANEL = "InstructorAssignmentPanel";
		/** StudentDirectoryPanel */
		private StudentDirectoryPanel pnlStudentDirectory;
		/** FacultyDirectoryPanel */
		private FacultyDirectoryPanel pnlFacultyDirectory;
		/** CourseCatalogPanel */
		private CourseCatalogPanel pnlCatalog;
		/** InstructorAssignmentPanel */
		private InstructorAssignmentPanel pnlInstructorAssignment;
		/** CardLayout for the RegistrarPanel */
		private CardLayout rCardLayout;
		/** Panel for the RegistrarPanel */
		private JPanel rPanel;
		/** Button for the StudentDirectory functionality */
		private JButton btnStudentDirectory;
		/** Button for the FacultyDirectory functionality */
		private JButton btnFacultyDirectory;
		/** Button for the CourseCatalog functionality */
		private JButton btnCourseCatalog;
		/** Button for InstructorAssignment functionality */
		private JButton btnInstructorAssignment;
		/** Button to logout */
		private JButton btnLogout;
		
		public RegistrarPanel() {
			super(new GridBagLayout());
			
			JPanel pnlButtons = new JPanel();
			pnlButtons.setLayout(new GridLayout(1, 4));
			btnStudentDirectory = new JButton("Student Directory");
			btnStudentDirectory.addActionListener(this);
			btnFacultyDirectory = new JButton("Faculty Directory");
			btnFacultyDirectory.addActionListener(this);
			btnCourseCatalog = new JButton("Course Catalog");
			btnCourseCatalog.addActionListener(this);
			btnInstructorAssignment = new JButton("Faculty Assignment");
			btnInstructorAssignment.addActionListener(this);
			btnLogout = new JButton("Logout");
			btnLogout.addActionListener(this);
			pnlButtons.add(btnStudentDirectory);
			pnlButtons.add(btnFacultyDirectory);
			pnlButtons.add(btnCourseCatalog);
			pnlButtons.add(btnInstructorAssignment);
			pnlButtons.add(btnLogout);
			
			rPanel = new JPanel();
			rCardLayout = new CardLayout();
			rPanel.setLayout(rCardLayout);
			pnlStudentDirectory = new StudentDirectoryPanel();
			pnlFacultyDirectory = new FacultyDirectoryPanel();
			pnlCatalog = new CourseCatalogPanel();
			pnlInstructorAssignment = new InstructorAssignmentPanel();
			rPanel.add(pnlStudentDirectory, STUDENT_DIRECTORY_PANEL);
			rPanel.add(pnlFacultyDirectory, FACULTY_DIRECTORY_PANEL);
			rPanel.add(pnlCatalog, COURSE_CATALOG_PANEL);
			rPanel.add(pnlInstructorAssignment, INSTRUCTOR_ASSIGNMENT_PANEL);
			rCardLayout.show(rPanel, STUDENT_DIRECTORY_PANEL);
			
//			scrollRPanel = new JScrollPane(rPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 1;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(pnlButtons, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(rPanel, c);
		}
		
		/**
		 * Performs actions when any component with an action listener is selected.
		 * @param e ActionEvent representing the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnStudentDirectory) {
				rCardLayout.show(rPanel, STUDENT_DIRECTORY_PANEL); 
			} else if (e.getSource() == btnFacultyDirectory) {
				rCardLayout.show(rPanel, FACULTY_DIRECTORY_PANEL);
			} else if (e.getSource() == btnCourseCatalog) {
				rCardLayout.show(rPanel, COURSE_CATALOG_PANEL);
			} else if (e.getSource() == btnInstructorAssignment) {
				rCardLayout.show(rPanel, INSTRUCTOR_ASSIGNMENT_PANEL);
				pnlInstructorAssignment.updateTables();
			} else if (e.getSource() == btnLogout) {
				RegistrationManager.getInstance().logout();
				cardLayout.show(panel, LOGIN_PANEL);
			}
		}
		
	}
	
	/**
	 * Creates a panel for student registration.
	 * @author SarahHeckman
	 */
	private class StudentPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button to logout */
		private JButton btnLogout;
		/** StudentRegistrationPanel */
		private StudentRegistrationPanel studentRegPanel;
		
		public StudentPanel() {
			super(new GridBagLayout());
			
			JPanel pnlButtons = new JPanel();
			pnlButtons.setLayout(new GridLayout(1, 1));
			btnLogout = new JButton("Logout");
			btnLogout.addActionListener(this);
			pnlButtons.add(btnLogout);
			
			studentRegPanel = new StudentRegistrationPanel();
	
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.weightx = 1;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.RELATIVE;
			add(pnlButtons, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(studentRegPanel, c);
		}
		
		/**
		 * Performs actions when any component with an action listener is selected.
		 * @param e ActionEvent representing the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnLogout) {
				RegistrationManager.getInstance().logout();
				cardLayout.show(panel, LOGIN_PANEL);
				updateTables();
				pnlLogin.repaint();
			}
		}
		
		/**
		 * Updates tables
		 */
		public void updateTables() {
			studentRegPanel.updateTables();
		}
		
	}
	
	/**
	 * Creates a panel for faculty to manage their classes
	 * @author Sarah Heckman
	 */
	private class FacultyPanel extends JPanel implements ActionListener {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button to logout */
		private JButton btnLogout;
		
		/**
		 * Temporary class for the FacultyPanel until we implement
		 * that functionality.
		 */
		public FacultyPanel() {
			btnLogout = new JButton("Logout");
			btnLogout.addActionListener(this);
			
			add(btnLogout);
		}
		
		/**
		 * Performs actions when any component with an action listener is selected.
		 * @param e ActionEvent representing the user action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnLogout) {
				RegistrationManager.getInstance().logout();
				cardLayout.show(panel, LOGIN_PANEL);
			}
		}
	}

}