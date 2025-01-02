package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;

/**
 * Creates a user interface for working with the FacultyDirectory.
 * 
 * @author Sarah Heckman
 */
public class FacultyDirectoryPanel extends JPanel implements ActionListener {
	
	/** ID used for object serialization */
	private static final long serialVersionUID = 1L;

	/** Button for resetting the directory */
	private JButton btnNewFacultyList;
	/** Button for resetting the directory */
	private JButton btnLoadFacultyList;
	/** Button for saving the final directory */
	private JButton btnSaveFacultyList;
	/** JTable for displaying the directory of Faculty */
	private JTable tableFacultyDirectory;
	/** Scroll pane for table */
	private JScrollPane scrollFacultyDirectory;
	/** TableModel for directory of Faculty */
	private FacultyDirectoryTableModel facultyDirectoryTableModel;
	/** JLabel for firstName */
	private JLabel lblFirstName;
	/** JLabel for lastName */
	private JLabel lblLastName;
	/** JLabel for id */
	private JLabel lblId;
	/** JLabel for email */
	private JLabel lblEmail;
	/** JLabel for password */
	private JLabel lblPassword;
	/** JLabel for repeat password */
	private JLabel lblRepeatPassword;
	/** JLabel for maxCourses */
	private JLabel lblMaxCourses;
	/** JTextField for firstName */
	private JTextField txtFirstName;
	/** JTextField for lastName */
	private JTextField txtLastName;
	/** JTextField for id */
	private JTextField txtId;
	/** JTextField for email */
	private JTextField txtEmail;
	/** JPasswordField for password */
	private JPasswordField txtPassword;
	/** JPasswordField for repeat password */
	private JPasswordField txtRepeatPassword;
	/** JTextField for maxCourses */
	private JComboBox<Integer> comboMaxCourses;
	/** Button for adding a new Faculty */
	private JButton btnAddFaculty;
	/** Button for removing the selected Faculty from the directory */
	private JButton btnRemoveFaculty;
	/** Reference to FacultyDirectory */
	private FacultyDirectory facultyDirectory;
	
	/**
	 * Constructs the FacultyDirectoryPanel and sets up the GUI 
	 * components.
	 */
	public FacultyDirectoryPanel() {
		super(new GridBagLayout());
		
		facultyDirectory = RegistrationManager.getInstance().getFacultyDirectory();
		
		//Set up Directory buttons
		btnNewFacultyList = new JButton("New Faculty Directory");
		btnNewFacultyList.addActionListener(this);
		btnLoadFacultyList = new JButton("Load Faculty Directory");
		btnLoadFacultyList.addActionListener(this);
		btnSaveFacultyList = new JButton("Save Faculty Directory");
		btnSaveFacultyList.addActionListener(this);
		
		JPanel pnlDirectoryButton = new JPanel();
		pnlDirectoryButton.setLayout(new GridLayout(1, 3));
		pnlDirectoryButton.add(btnNewFacultyList);
		pnlDirectoryButton.add(btnLoadFacultyList);
		pnlDirectoryButton.add(btnSaveFacultyList);
		
		Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder boarder = BorderFactory.createTitledBorder(lowerEtched, "Directory Buttons");
		pnlDirectoryButton.setBorder(boarder);
		pnlDirectoryButton.setToolTipText("Directory Buttons");
		
		//Set up Directory table
		facultyDirectoryTableModel = new FacultyDirectoryTableModel();
		tableFacultyDirectory = new JTable(facultyDirectoryTableModel);
		tableFacultyDirectory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFacultyDirectory.setPreferredScrollableViewportSize(new Dimension(500, 500));
		tableFacultyDirectory.setFillsViewportHeight(true);
		
		scrollFacultyDirectory = new JScrollPane(tableFacultyDirectory, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		boarder = BorderFactory.createTitledBorder(lowerEtched, "Faculty Directory");
		scrollFacultyDirectory.setBorder(boarder);
		scrollFacultyDirectory.setToolTipText("Faculty Directory");
		
		//Set up Faculty buttons
		btnAddFaculty = new JButton("Add Faculty");
		btnAddFaculty.addActionListener(this);
		btnRemoveFaculty = new JButton("Remove Faculty");
		btnRemoveFaculty.addActionListener(this);
		
		JPanel pnlFacultyButtons = new JPanel();
		pnlFacultyButtons.setLayout(new GridLayout(1, 2));
		pnlFacultyButtons.add(btnAddFaculty);
		pnlFacultyButtons.add(btnRemoveFaculty);
		
		boarder = BorderFactory.createTitledBorder(lowerEtched, "Faculty Controls");
		pnlFacultyButtons.setBorder(boarder);
		pnlFacultyButtons.setToolTipText("Faculty Controls");
		
		//Set up Faculty form
		lblFirstName = new JLabel("First Name");
		lblLastName = new JLabel("Last Name");
		lblId = new JLabel("ID");
		lblEmail = new JLabel("Email");
		lblPassword = new JLabel("Password");
		lblRepeatPassword = new JLabel("Repeat Password");
		lblMaxCourses = new JLabel("Max Courses");
		txtFirstName = new JTextField(20);
		txtLastName = new JTextField(20);
		txtId = new JTextField(20);
		txtEmail = new JTextField(20);
		txtPassword = new JPasswordField(20);
		txtRepeatPassword = new JPasswordField(20);
		comboMaxCourses = new JComboBox<Integer>();
		comboMaxCourses.addItem(1);
		comboMaxCourses.addItem(2);
		comboMaxCourses.addItem(3);
		comboMaxCourses.setEditable(false);
		
		JPanel pnlFacultyForm = new JPanel();
		pnlFacultyForm.setLayout(new GridLayout(7, 2));
		pnlFacultyForm.add(lblFirstName);
		pnlFacultyForm.add(txtFirstName);
		pnlFacultyForm.add(lblLastName);
		pnlFacultyForm.add(txtLastName);
		pnlFacultyForm.add(lblId);
		pnlFacultyForm.add(txtId);
		pnlFacultyForm.add(lblEmail);
		pnlFacultyForm.add(txtEmail);
		pnlFacultyForm.add(lblPassword);
		pnlFacultyForm.add(txtPassword);
		pnlFacultyForm.add(lblRepeatPassword);
		pnlFacultyForm.add(txtRepeatPassword);
		pnlFacultyForm.add(lblMaxCourses);
		pnlFacultyForm.add(comboMaxCourses);
		
		boarder = BorderFactory.createTitledBorder(lowerEtched, "Faculty Information");
		pnlFacultyForm.setBorder(boarder);
		pnlFacultyForm.setToolTipText("Faculty Information");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = .2;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlDirectoryButton, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollFacultyDirectory, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = .5;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlFacultyButtons, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlFacultyForm, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLoadFacultyList) {
			String fileName = getFileName(true);
			try {
				facultyDirectory.loadFacultyFromFile(fileName);
				facultyDirectoryTableModel.updateData();
				scrollFacultyDirectory.revalidate();
				scrollFacultyDirectory.repaint();
				facultyDirectoryTableModel.fireTableDataChanged();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
		} else if (e.getSource() == btnSaveFacultyList) {
			String fileName = getFileName(false);
			try {
				facultyDirectory.saveFacultyDirectory(fileName);
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
		} else if (e.getSource() == btnNewFacultyList) {
			facultyDirectory.newFacultyDirectory();
			facultyDirectoryTableModel.updateData();
			scrollFacultyDirectory.revalidate();
			scrollFacultyDirectory.repaint();
			facultyDirectoryTableModel.fireTableDataChanged();
		} else if (e.getSource() == btnAddFaculty) {
			String firstName = txtFirstName.getText();
			String lastName = txtLastName.getText();
			String id = txtId.getText();
			String email = txtEmail.getText();
			char[] password = txtPassword.getPassword();
			char[] repeatPassword = txtRepeatPassword.getPassword();
			int maxCourses = 0;
			try {
				maxCourses = comboMaxCourses.getItemAt(comboMaxCourses.getSelectedIndex());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Max courses must be a positive number between 1 and 3.");
				return;
			}
			
			String pwString = "";
			for (int i = 0; i < password.length; i++) {
				pwString += password[i];
			}
			
			String repeatPWString = "";
			for (int i = 0; i < repeatPassword.length; i++) {
				repeatPWString += repeatPassword[i];
			}
			
			try {
				if (facultyDirectory.addFaculty(firstName, lastName, id, email, pwString, repeatPWString, maxCourses)) {
					txtFirstName.setText("");
					txtLastName.setText("");
					txtId.setText("");
					txtEmail.setText("");
					txtPassword.setText("");
					txtRepeatPassword.setText("");
					comboMaxCourses.setSelectedIndex(0);
				} else {
					JOptionPane.showMessageDialog(this, "Faculty already in system.");
				}
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
			facultyDirectoryTableModel.updateData();
		} else if (e.getSource() == btnRemoveFaculty) {
			int row = tableFacultyDirectory.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "No faculty selected.");
			} else {
				try {
					facultyDirectory.removeFaculty(tableFacultyDirectory.getValueAt(row, 2).toString());
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					JOptionPane.showMessageDialog(this, "No faculty selected.");
				}
			}
			facultyDirectoryTableModel.updateData();
		}
		
		this.validate();
		this.repaint();
	}
	
	/**
	 * Returns a file name generated through interactions with a {@link JFileChooser}
	 * object.
	 * @param chooserType true if open, false if save
	 * @return the file name selected through {@link JFileChooser}
	 */
	private String getFileName(boolean chooserType) {
		JFileChooser fc = new JFileChooser("./");  //Open JFileChoose to current working directory
		fc.setApproveButtonText("Select");
		int returnVal = Integer.MIN_VALUE;
		if (chooserType) {
			fc.setDialogTitle("Load Faculty Directory");
			returnVal = fc.showOpenDialog(this);
		} else {
			fc.setDialogTitle("Save Faculty Directory");
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File catalogFile = fc.getSelectedFile();
		return catalogFile.getAbsolutePath();
	}
	
	/**
	 * {@link FacultyDirectoryTableModel} is the object underlying the {@link JTable} object that displays
	 * the list of Faculty to the system.
	 * @author Sarah Heckman
	 */
	private class FacultyDirectoryTableModel extends AbstractTableModel {
		
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names for the table */
		private String [] columnNames = {"First Name", "Last Name", "Faculty ID"};
		/** Data stored in the table */
		private Object [][] data;
		
		/**
		 * Constructs the {@link FacultyDirectoryTableModel} by requesting the latest information
		 * from the {@link RequirementTrackerModel}.
		 */
		public FacultyDirectoryTableModel() {
			updateData();
		}

		/**
		 * Returns the number of columns in the table.
		 * @return the number of columns in the table.
		 */
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns the number of rows in the table.
		 * @return the number of rows in the table.
		 */
		public int getRowCount() {
			if (data == null) 
				return 0;
			return data.length;
		}
		
		/**
		 * Returns the column name at the given index.
		 * @param col location of the column
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given {row, col} index.
		 * @param row location to get the data
		 * @param col location to get the data
		 * @return the data at the given location.
		 */
		public Object getValueAt(int row, int col) {
			if (data == null)
				return null;
			return data[row][col];
		}
		
		/**
		 * Sets the given value to the given {row, col} location.
		 * @param value Object to modify in the data.
		 * @param row location to modify the data.
		 * @param col location to modify the data.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
		
		/**
		 * Updates the given model with {@link Faculty} information from the {@link FacultyDirectory}.
		 */
		public void updateData() {
			data = facultyDirectory.getFacultyDirectory();
		}
	}

}