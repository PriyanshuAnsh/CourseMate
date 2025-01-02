package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;

/**
 * Creates a user interface for working with the CourseCatalog.
 * 
 * @author Sarah Heckman
 */
public class CourseCatalogPanel extends JPanel implements ActionListener {
	
	/** ID used for object serialization */
	private static final long serialVersionUID = 1L;

	/** Button for resetting the catalog */
	private JButton btnNewCourseCatalog;
	/** Button for resetting the catalog */
	private JButton btnLoadCourseCatalog;
	/** Button for displaying the final catalog */
	private JButton btnSaveCourseCatalog;
	/** JTable for displaying the catalog of Courses */
	private JTable tableCourseCatalog;
	/** Scroll pane for table */
	private JScrollPane scrollCourseCatalog;
	/** TableModel for catalog of Courses */
	private CourseCatalogTableModel courseCatalogTableModel;
	/** JLabel for name */
	private JLabel lblName;
	/** JLabel for title */
	private JLabel lblTitle;
	/** JLabel for section */
	private JLabel lblSection;
	/** JLabel for credits */
	private JLabel lblCredits;
	/** JLabel for instructorId */
	private JLabel lblInstructorId;
	/** JLabel for enrollmentCap */
	private JLabel lblEnrollmentCap;
	/** Label for meeting days */
	private JLabel lblMeetingDays = new JLabel("Meeting Days: ");
	/** Label for start time */
	private JLabel lblStartTime = new JLabel("Start Time: ");
	/** Label for end time */
	private JLabel lblEndTime = new JLabel("End Time: ");
	/** JTextField for name */
	private JTextField txtName;
	/** JTextField for title */
	private JTextField txtTitle;
	/** JTextField for section */
	private JTextField txtSection;
	/** JTextField for instructorId */
	private JTextField txtInstructorId;
	/** JTextField for enrollmentCap */
	private JTextField txtEnrollmentCap;
	/** Check box for Monday */
	private JCheckBox cbMonday;
	/** Check box for Tuesday */
	private JCheckBox cbTuesday;
	/** Check box for Wednesday */
	private JCheckBox cbWednesday;
	/** Check box for Thursday */
	private JCheckBox cbThursday;
	/** Check box for Friday */
	private JCheckBox cbFriday;
	/** Check box for Arranged */
	private JCheckBox cbArranged;
	/** Drop down for start hour */
	private JComboBox<Integer> comboStartHour;
	/** Drop down for start minute */
	private JComboBox<Integer> comboStartMin;
	/** Drop down for start am/pm */
	private JComboBox<String> comboStartPeriod;
	/** Drop down for end hour */
	private JComboBox<Integer> comboEndHour;
	/** Drop down for end minute */
	private JComboBox<Integer> comboEndMin;
	/** Drop down for end am/pm */
	private JComboBox<String> comboEndPeriod;
	/** Drop down for credits */
	private JComboBox<Integer> comboCredits;
	/** Button for adding a course */
	private JButton btnAddCourse;
	/** Button for removing the selected Course from the catalog */
	private JButton btnRemoveCourse;
	/** Reference to CourseCatalog */
	private CourseCatalog catalog;
	
	/**
	 * Constructs the CourseCatalogPanel and sets up the GUI 
	 * components.
	 */
	public CourseCatalogPanel() {
		super(new GridBagLayout());
		
		catalog = RegistrationManager.getInstance().getCourseCatalog();
		
		//Set up Catalog buttons
		btnNewCourseCatalog = new JButton("New Course Catalog");
		btnNewCourseCatalog.addActionListener(this);
		btnLoadCourseCatalog = new JButton("Load Course Catalog");
		btnLoadCourseCatalog.addActionListener(this);
		btnSaveCourseCatalog = new JButton("Save Course Catalog");
		btnSaveCourseCatalog.addActionListener(this);
		
		JPanel pnlCatalogButton = new JPanel();
		pnlCatalogButton.setLayout(new GridLayout(1, 3));
		pnlCatalogButton.add(btnNewCourseCatalog);
		pnlCatalogButton.add(btnLoadCourseCatalog);
		pnlCatalogButton.add(btnSaveCourseCatalog);
		
		Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Catalog Buttons");
		pnlCatalogButton.setBorder(border);
		pnlCatalogButton.setToolTipText("Catalog Buttons");
		
		//Set up Catalog table
		courseCatalogTableModel = new CourseCatalogTableModel();
		tableCourseCatalog = new JTable(courseCatalogTableModel);
		tableCourseCatalog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCourseCatalog.setPreferredScrollableViewportSize(new Dimension(500, 500));
		tableCourseCatalog.setFillsViewportHeight(true);
		
		scrollCourseCatalog = new JScrollPane(tableCourseCatalog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		border = BorderFactory.createTitledBorder(lowerEtched, "Course Catalog");
		scrollCourseCatalog.setBorder(border);
		scrollCourseCatalog.setToolTipText("Course Catalog");
		
		//Set up Course buttons
		btnAddCourse = new JButton("Add Course");
		btnAddCourse.addActionListener(this);
		btnRemoveCourse = new JButton("Remove Course");
		btnRemoveCourse.addActionListener(this);
		
		JPanel pnlCourseButtons = new JPanel();
		pnlCourseButtons.setLayout(new GridLayout(1, 2));
		pnlCourseButtons.add(btnAddCourse);
		pnlCourseButtons.add(btnRemoveCourse);
		
		border = BorderFactory.createTitledBorder(lowerEtched, "Course Controls");
		pnlCourseButtons.setBorder(border);
		pnlCourseButtons.setToolTipText("Course Controls");
		
		//Set up Course form
		lblName = new JLabel("Course Name");
		lblTitle = new JLabel("Course Title");
		lblSection = new JLabel("Section");
		lblCredits = new JLabel("Credits");
		lblInstructorId = new JLabel("Instructor Id");
		lblEnrollmentCap = new JLabel("Enrollment Cap");
		txtName = new JTextField(20);
		txtTitle = new JTextField(20);
		txtSection = new JTextField(20);
		txtInstructorId = new JTextField(20);
		txtEnrollmentCap = new JTextField(20);
		comboCredits = new JComboBox<Integer>();
		comboCredits.addItem(Integer.valueOf(1));
		comboCredits.addItem(Integer.valueOf(2));
		comboCredits.addItem(Integer.valueOf(3));
		comboCredits.addItem(Integer.valueOf(4));
		comboCredits.addItem(Integer.valueOf(5));
		
		JPanel pnlDays = new JPanel(new GridLayout(1, 15));
		pnlDays.add(new JLabel("Mon"));
		cbMonday = new JCheckBox();
		pnlDays.add(cbMonday);
		pnlDays.add(new JLabel("Tue"));
		cbTuesday = new JCheckBox();
		pnlDays.add(cbTuesday);
		pnlDays.add(new JLabel("Wed"));
		cbWednesday = new JCheckBox();
		pnlDays.add(cbWednesday);
		pnlDays.add(new JLabel("Thu"));
		cbThursday = new JCheckBox();
		pnlDays.add(cbThursday);
		pnlDays.add(new JLabel("Fri"));
		cbFriday = new JCheckBox();
		pnlDays.add(cbFriday);
		pnlDays.add(new JLabel("Arg"));
		cbArranged = new JCheckBox();
		cbArranged.addItemListener(new ItemListener() {
			/**
			 * If the selected time is arranged, then the 
			 * times are set to zero.
			 */
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbArranged.isSelected()) {
					comboStartHour.setSelectedIndex(0);
					comboStartMin.setSelectedIndex(0);
					comboEndHour.setSelectedIndex(0);
					comboEndMin.setSelectedIndex(0);
					
					cbMonday.setSelected(false);
					cbTuesday.setSelected(false);
					cbWednesday.setSelected(false);
					cbThursday.setSelected(false);
					cbFriday.setSelected(false);
				}
			}
			
		});
		pnlDays.add(cbArranged);
		
		
		JPanel pnlTime = new JPanel(new GridLayout(1, 2));
		JPanel pnlStartTime = new JPanel(new GridLayout(1, 4));
		JPanel pnlEndTime = new JPanel(new GridLayout(1, 4));
		
		comboStartHour = new JComboBox<Integer>();
		comboStartHour.addItem(Integer.valueOf(0));
		comboStartHour.addItem(Integer.valueOf(1));
		comboStartHour.addItem(Integer.valueOf(2));
		comboStartHour.addItem(Integer.valueOf(3));
		comboStartHour.addItem(Integer.valueOf(4));
		comboStartHour.addItem(Integer.valueOf(5));
		comboStartHour.addItem(Integer.valueOf(6));
		comboStartHour.addItem(Integer.valueOf(7));
		comboStartHour.addItem(Integer.valueOf(8));
		comboStartHour.addItem(Integer.valueOf(9));
		comboStartHour.addItem(Integer.valueOf(10));
		comboStartHour.addItem(Integer.valueOf(11));
		comboStartHour.addItem(Integer.valueOf(12));
		comboStartMin = new JComboBox<Integer>();
		comboStartMin.addItem(Integer.valueOf(0));
		comboStartMin.addItem(Integer.valueOf(5));
		comboStartMin.addItem(Integer.valueOf(10));
		comboStartMin.addItem(Integer.valueOf(15));
		comboStartMin.addItem(Integer.valueOf(20));
		comboStartMin.addItem(Integer.valueOf(25));
		comboStartMin.addItem(Integer.valueOf(30));
		comboStartMin.addItem(Integer.valueOf(35));
		comboStartMin.addItem(Integer.valueOf(40));
		comboStartMin.addItem(Integer.valueOf(45));
		comboStartMin.addItem(Integer.valueOf(50));
		comboStartMin.addItem(Integer.valueOf(55));
		comboStartPeriod = new JComboBox<String>();
		comboStartPeriod.addItem("AM");
		comboStartPeriod.addItem("PM");
		
		pnlStartTime.add(lblStartTime);
		pnlStartTime.add(comboStartHour);
		pnlStartTime.add(comboStartMin);
		pnlStartTime.add(comboStartPeriod);
		
		comboEndHour = new JComboBox<Integer>();
		comboEndHour.addItem(Integer.valueOf(0));
		comboEndHour.addItem(Integer.valueOf(1));
		comboEndHour.addItem(Integer.valueOf(2));
		comboEndHour.addItem(Integer.valueOf(3));
		comboEndHour.addItem(Integer.valueOf(4));
		comboEndHour.addItem(Integer.valueOf(5));
		comboEndHour.addItem(Integer.valueOf(6));
		comboEndHour.addItem(Integer.valueOf(7));
		comboEndHour.addItem(Integer.valueOf(8));
		comboEndHour.addItem(Integer.valueOf(9));
		comboEndHour.addItem(Integer.valueOf(10));
		comboEndHour.addItem(Integer.valueOf(11));
		comboEndHour.addItem(Integer.valueOf(12));
		comboEndMin = new JComboBox<Integer>();
		comboEndMin.addItem(Integer.valueOf(0));
		comboEndMin.addItem(Integer.valueOf(5));
		comboEndMin.addItem(Integer.valueOf(10));
		comboEndMin.addItem(Integer.valueOf(15));
		comboEndMin.addItem(Integer.valueOf(20));
		comboEndMin.addItem(Integer.valueOf(25));
		comboEndMin.addItem(Integer.valueOf(30));
		comboEndMin.addItem(Integer.valueOf(35));
		comboEndMin.addItem(Integer.valueOf(40));
		comboEndMin.addItem(Integer.valueOf(45));
		comboEndMin.addItem(Integer.valueOf(50));
		comboEndMin.addItem(Integer.valueOf(55));
		comboEndPeriod = new JComboBox<String>();
		comboEndPeriod.addItem("AM");
		comboEndPeriod.addItem("PM");
		
		pnlEndTime.add(lblEndTime);
		pnlEndTime.add(comboEndHour);
		pnlEndTime.add(comboEndMin);
		pnlEndTime.add(comboEndPeriod);
		
		pnlTime.add(pnlStartTime);
		pnlTime.add(pnlEndTime);
		
		JPanel pnlCourseForm = new JPanel();
		pnlCourseForm.setLayout(new GridLayout(8, 2));
		pnlCourseForm.add(lblName);
		pnlCourseForm.add(txtName);
		pnlCourseForm.add(lblTitle);
		pnlCourseForm.add(txtTitle);
		pnlCourseForm.add(lblSection);
		pnlCourseForm.add(txtSection);
		pnlCourseForm.add(lblCredits);
		pnlCourseForm.add(comboCredits);
		pnlCourseForm.add(lblInstructorId);
		pnlCourseForm.add(txtInstructorId);
		pnlCourseForm.add(lblEnrollmentCap);
		pnlCourseForm.add(txtEnrollmentCap);
		pnlCourseForm.add(pnlStartTime);
		pnlCourseForm.add(pnlEndTime);
		pnlCourseForm.add(lblMeetingDays);
		pnlCourseForm.add(pnlDays);
		
		border = BorderFactory.createTitledBorder(lowerEtched, "Course Information");
		pnlCourseForm.setBorder(border);
		pnlCourseForm.setToolTipText("Course Information");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = .2;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlCatalogButton, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollCourseCatalog, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = .5;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlCourseButtons, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(pnlCourseForm, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLoadCourseCatalog) {
			try {
				String fileName = getFileName(true);
				catalog.loadCoursesFromFile(fileName);
				courseCatalogTableModel.updateData();
				scrollCourseCatalog.revalidate();
				scrollCourseCatalog.repaint();
				courseCatalogTableModel.fireTableDataChanged();
			} catch (IllegalArgumentException | IllegalStateException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
		} else if (e.getSource() == btnSaveCourseCatalog) {
			try {
				String fileName = getFileName(false);
				catalog.saveCourseCatalog(fileName);
			} catch (IllegalArgumentException | IllegalStateException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
		} else if (e.getSource() == btnNewCourseCatalog) {
			catalog.newCourseCatalog();
			courseCatalogTableModel.updateData();
			scrollCourseCatalog.revalidate();
			scrollCourseCatalog.repaint();
			courseCatalogTableModel.fireTableDataChanged();
		} else if (e.getSource() == btnAddCourse) {
			String name = txtName.getText();
			String title = txtTitle.getText();
			String section = txtSection.getText();
			String instructorId = txtInstructorId.getText();
			int enrollmentCap = 0;
			try {
				enrollmentCap = Integer.parseInt(txtEnrollmentCap.getText());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Enrollment capacity must be a number between 10 and 250.");
			}
			int credits = 0;
			int creditsIdx = comboCredits.getSelectedIndex();
			if (creditsIdx == -1) {
				JOptionPane.showMessageDialog(this, "The weekly repeat is invalid.");
				return;
			}
			credits = comboCredits.getItemAt(creditsIdx);
			String meetingDays = "";
			if (cbMonday.isSelected()) {
				meetingDays += "M";
			}
			if (cbTuesday.isSelected()) {
				meetingDays += "T";
			}
			if (cbWednesday.isSelected()) {
				meetingDays += "W";
			}
			if (cbThursday.isSelected()) {
				meetingDays += "H";
			}
			if (cbFriday.isSelected()) {
				meetingDays += "F";
			}
			if (cbArranged.isSelected()) {
				meetingDays += "A";
			}
			
			int startTime = 0;
			int hourIdx = comboStartHour.getSelectedIndex();
			if (hourIdx == -1) {
				JOptionPane.showMessageDialog(this, "The times are invalid.");
				return;
			}
			startTime = comboStartHour.getItemAt(hourIdx) * 100;
			int minIdx = comboStartMin.getSelectedIndex();
			if (minIdx == -1) {
				JOptionPane.showMessageDialog(this, "The times are invalid.");
				return;
			}
			startTime += comboStartMin.getItemAt(minIdx);
			int periodIdx = comboStartPeriod.getSelectedIndex();
			if (periodIdx == -1) {
				JOptionPane.showMessageDialog(this, "The times are invalid.");
				return;
			}
			if (comboStartPeriod.getItemAt(periodIdx).equals("PM") && startTime < 1200) {
				startTime += 1200;
			}
			
			int endTime = 0;
			hourIdx = comboEndHour.getSelectedIndex();
			if (hourIdx == -1) {
				JOptionPane.showMessageDialog(this, "The times are invalid.");
				return;
			}
			endTime = comboEndHour.getItemAt(hourIdx) * 100;
			minIdx = comboEndMin.getSelectedIndex();
			if (minIdx == -1) {
				JOptionPane.showMessageDialog(this, "The times are invalid.");
				return;
			}
			endTime += comboEndMin.getItemAt(minIdx);
			periodIdx = comboEndPeriod.getSelectedIndex();
			if (periodIdx == -1) {
				JOptionPane.showMessageDialog(this, "The times are invalid.");
				return;
			}
			if (comboEndPeriod.getItemAt(periodIdx).equals("PM") && endTime < 1200) {
				endTime += 1200;
			}
			
			try {
				if (catalog.addCourseToCatalog(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime)) {
					txtName.setText("");
					txtTitle.setText("");
					txtSection.setText("");
					txtInstructorId.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "Course already in system.");
				}
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
			courseCatalogTableModel.updateData();
		} else if (e.getSource() == btnRemoveCourse) {
			int row = tableCourseCatalog.getSelectedRow();
			if (row == -1 || row == courseCatalogTableModel.getRowCount()) {
				JOptionPane.showMessageDialog(this, "No course selected.");
			} else {
				try {
					catalog.removeCourseFromCatalog(courseCatalogTableModel.getValueAt(row, 0).toString(), courseCatalogTableModel.getValueAt(row, 1).toString());
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					JOptionPane.showMessageDialog(this, "No course selected.");
				}
			}
			courseCatalogTableModel.updateData();
		}
		
		this.validate();
		this.repaint();
	}
	
	/**
	 * Returns a file name generated through interactions with a JFileChooser
	 * object.
	 * @param chooserType true if open, false if save
	 * @return the file name selected through JFileChooser
	 */
	private String getFileName(boolean chooserType) {
		JFileChooser fc = new JFileChooser("./");  //Open JFileChoose to current working directory
		fc.setApproveButtonText("Select");
		int returnVal = Integer.MIN_VALUE;
		if (chooserType) {
			fc.setDialogTitle("Load Course Catalog");
			returnVal = fc.showOpenDialog(this);
		} else {
			fc.setDialogTitle("Save Schedule");
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException("Invalid selection.");
		}
		File catalogFile = fc.getSelectedFile();
		return catalogFile.getAbsolutePath();
	}
	
	/**
	 * CourseCatalogTableModel is the object underlying the JTable object that displays
	 * the list of Courses to the user.
	 * @author Sarah Heckman
	 */
	private class CourseCatalogTableModel extends AbstractTableModel {
		
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names for the table */
		private String [] columnNames = {"Name", "Section", "Title", "Meeting Information", "Open Seats"};
		/** Data stored in the table */
		private Object [][] data;
		
		/**
		 * Constructs the CourseCatalogTableModel by requesting the latest information
		 * from the RequirementTrackerModel.
		 */
		public CourseCatalogTableModel() {
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
		 * @param col column index
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given {row, col} index.
		 * @param row row index
		 * @param col column index
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
		 * Updates the given model with Course information from the CourseCatalog.
		 */
		public void updateData() {
			data = catalog.getCourseCatalog();
		}
	}

}