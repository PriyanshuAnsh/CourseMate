package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Creates a user interface for a registrar to assign/remove faculty to/from
 * courses.
 * 
 * @author Sarah Heckman
 */
public class InstructorAssignmentPanel  extends JPanel implements ActionListener {
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Button for adding the selected faculty to the selected course as instructor */
	private JButton btnAssignFacultyToCourse;
	/** Button for removing the selected faculty from the selected course */
	private JButton btnRemoveFacultyFromCourse;
	/** Button for resetting the faculty schedule */
	private JButton btnReset;
	/** JTable for displaying the catalog of Courses */
	private JTable tableCatalog;
	/** JTable for displaying the directory of Faculty */
	private JTable tableFaculty;
	/** TableModel for catalog */
	private CourseCatalogTableModel catalogTableModel;
	/** TableModel for schedule */
	private FacultyDirectoryTableModel facultyTableModel;
	/** Panel for displaying Course Details */
	private JPanel pnlCourseDetails;
	/** Label for Course Details name title */
	private JLabel lblNameTitle = new JLabel("Name: ");
	/** Label for Course Details section title */
	private JLabel lblSectionTitle = new JLabel("Section: ");
	/** Label for Course Details title title */
	private JLabel lblTitleTitle = new JLabel("Title: ");
	/** Label for Course Details instructor title */
	private JLabel lblInstructorTitle = new JLabel("Instructor: ");
	/** Label for Course Details credit hours title */
	private JLabel lblCreditsTitle = new JLabel("Credits: ");
	/** Label for Course Details meeting title */
	private JLabel lblMeetingTitle = new JLabel("Meeting: ");
	/** Label for Course Details enrollment cap title */
	private JLabel lblEnrollmentCapTitle = new JLabel("Enrollment Cap: ");
	/** Label for Course Details open seats title */
	private JLabel lblOpenSeatsTitle = new JLabel("Open Seats: ");
	/** Label for Course Details name */
	private JLabel lblName = new JLabel("");
	/** Label for Course Details section */
	private JLabel lblSection = new JLabel("");
	/** Label for Course Details title */
	private JLabel lblTitle = new JLabel("");
	/** Label for Course Details instructor */
	private JLabel lblInstructor = new JLabel("");
	/** Label for Course Details credit hours */
	private JLabel lblCredits = new JLabel("");
	/** Label for Course Details meeting */
	private JLabel lblMeeting = new JLabel("");
	/** Label for Course Details enrollment cap */
	private JLabel lblEnrollmentCap = new JLabel("");
	/** Label for Course Details open seats */
	private JLabel lblOpenSeats = new JLabel("");
	/** Panel for displaying Faculty Details */
	private JPanel pnlFacultyDetails;
	/** Label for Faculty Details first name title */
	private JLabel lblFirstNameTitle = new JLabel("First Name: ");
	/** Label for Faculty Details last name title */
	private JLabel lblLastNameTitle = new JLabel("Last Name: ");
	/** Label for Faculty Details id title */
	private JLabel lblIdTitle = new JLabel("Id: ");
	/** Label for Faculty Details email title */
	private JLabel lblEmailTitle = new JLabel("Email: ");
	/** Label for Faculty Details max courses title*/
	private JLabel lblMaxCourseTitle = new JLabel("Max Courses: ");
	/** Label for Faculty Details overloaded title */
	private JLabel lblOverloadedTitle = new JLabel("Overloaded: ");
	/** Label for Faculty Details first name*/
	private JLabel lblFirstName = new JLabel("");
	/** Label for Faculty Details last name */
	private JLabel lblLastName = new JLabel("");
	/** Label for Faculty Details id */
	private JLabel lblId = new JLabel("");
	/** Label for Faculty Details email */
	private JLabel lblEmail = new JLabel("");
	/** Label for Faculty Details max courses */
	private JLabel lblMaxCourse = new JLabel("");
	/** Label for Faculty Details overloaded */
	private JLabel lblOverloaded = new JLabel("");
	/** Course catalog */
	private CourseCatalog catalog;
	/** Faculty directory */
	private FacultyDirectory facultyDirectory;
	
	
	/**
	 * Creates the instructor assignment panel.
	 */
	public InstructorAssignmentPanel() {
		super(new GridBagLayout());
		
		RegistrationManager manager = RegistrationManager.getInstance();
		catalog = manager.getCourseCatalog();
		facultyDirectory = manager.getFacultyDirectory();
		
		//Set up the JPanel that will hold action buttons		
		btnAssignFacultyToCourse = new JButton("Assign Faculty to Course");
		btnAssignFacultyToCourse.addActionListener(this);
		btnRemoveFacultyFromCourse = new JButton("Remove Faculty from Course");
		btnRemoveFacultyFromCourse.addActionListener(this);
		btnReset = new JButton("Reset Faculty Schedule");
		btnReset.addActionListener(this);
		
		JPanel pnlActions = new JPanel();
		pnlActions.setLayout(new GridLayout(1, 3));
		pnlActions.add(btnAssignFacultyToCourse);
		pnlActions.add(btnRemoveFacultyFromCourse);
		pnlActions.add(btnReset);
		
		Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder borderActions = BorderFactory.createTitledBorder(lowerEtched, "Actions");
		pnlActions.setBorder(borderActions);
		pnlActions.setToolTipText("Scheduler Actions");
					
		//Set up Catalog table
		catalogTableModel = new CourseCatalogTableModel();
		tableCatalog = new JTable(catalogTableModel) {
			private static final long serialVersionUID = 1L;
			
			/**
			 * Set custom tool tips for cells
			 * @param e MouseEvent that causes the tool tip
			 * @return tool tip text
			 */
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);
				
				if (rowIndex != -1 && realColumnIndex != -1) {
					return (String)catalogTableModel.getValueAt(rowIndex, realColumnIndex);
				} else {
					return "";
				}
			}
		};
		tableCatalog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCatalog.setFillsViewportHeight(true);
		tableCatalog.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String name = tableCatalog.getValueAt(tableCatalog.getSelectedRow(), 0).toString();
				String section = tableCatalog.getValueAt(tableCatalog.getSelectedRow(), 1).toString();
				Course c = catalog.getCourseFromCatalog(name, section);
				updateCourseDetails(c);
			}
			
		});
		
		JScrollPane scrollCatalog = new JScrollPane(tableCatalog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		TitledBorder borderCatalog = BorderFactory.createTitledBorder(lowerEtched, "Course Catalog");
		scrollCatalog.setBorder(borderCatalog);
		scrollCatalog.setToolTipText("Course Catalog");
		
		//Set up Faculty Directory table
		facultyTableModel = new FacultyDirectoryTableModel();
		tableFaculty = new JTable(facultyTableModel) {
			private static final long serialVersionUID = 1L;
			
			/**
			 * Set custom tool tips for cells
			 * @param e MouseEvent that causes the tool tip
			 * @return tool tip text
			 */
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);
				
				if (rowIndex != -1 && realColumnIndex != -1) {
					return (String)catalogTableModel.getValueAt(rowIndex, realColumnIndex);
				} else {
					return "";
				}
			}
		};
		tableFaculty.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFaculty.setFillsViewportHeight(true);
		tableFaculty.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String id = tableFaculty.getValueAt(tableFaculty.getSelectedRow(), 2).toString();
				Faculty f = facultyDirectory.getFacultyById(id);
				updateFacultyDetails(f);
			}
			
		});
		
		JScrollPane scrollFaculty = new JScrollPane(tableFaculty, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
		TitledBorder borderFaculty = BorderFactory.createTitledBorder(lowerEtched, "Faculty Directory");
		scrollFaculty.setBorder(borderFaculty);
		scrollFaculty.setToolTipText("Faculty Directory");
		
		
		updateTables();
		
		//Set up the course details panel
		pnlCourseDetails = new JPanel();
		pnlCourseDetails.setLayout(new GridLayout(5, 1));
		JPanel pnlName = new JPanel(new GridLayout(1, 4));
		pnlName.add(lblNameTitle);
		pnlName.add(lblName);
		pnlName.add(lblSectionTitle);
		pnlName.add(lblSection);
		
		JPanel pnlTitle = new JPanel(new GridLayout(1, 1));
		pnlTitle.add(lblTitleTitle);
		pnlTitle.add(lblTitle);
		
		JPanel pnlInstructor = new JPanel(new GridLayout(1, 4));
		pnlInstructor.add(lblInstructorTitle);
		pnlInstructor.add(lblInstructor);
		pnlInstructor.add(lblCreditsTitle);
		pnlInstructor.add(lblCredits);
		
		JPanel pnlMeeting = new JPanel(new GridLayout(1, 1));
		pnlMeeting.add(lblMeetingTitle);
		pnlMeeting.add(lblMeeting);
		
		JPanel pnlEnrollment = new JPanel(new GridLayout(1, 4));
		pnlEnrollment.add(lblEnrollmentCapTitle);
		pnlEnrollment.add(lblEnrollmentCap);
		pnlEnrollment.add(lblOpenSeatsTitle);
		pnlEnrollment.add(lblOpenSeats);
		
		pnlCourseDetails.add(pnlName);
		pnlCourseDetails.add(pnlTitle);
		pnlCourseDetails.add(pnlInstructor);
		pnlCourseDetails.add(pnlMeeting);
		pnlCourseDetails.add(pnlEnrollment);
		
		TitledBorder borderCourseDetails = BorderFactory.createTitledBorder(lowerEtched, "Course Details");
		pnlCourseDetails.setBorder(borderCourseDetails);
		pnlCourseDetails.setToolTipText("Course Details");
		
		//Set up faculty details panel
		pnlFacultyDetails = new JPanel();
		pnlFacultyDetails.setLayout(new GridLayout(6, 2));
		pnlFacultyDetails.add(lblFirstNameTitle);
		pnlFacultyDetails.add(lblFirstName);
		pnlFacultyDetails.add(lblLastNameTitle);
		pnlFacultyDetails.add(lblLastName);
		pnlFacultyDetails.add(lblIdTitle);
		pnlFacultyDetails.add(lblId);
		pnlFacultyDetails.add(lblEmailTitle);
		pnlFacultyDetails.add(lblEmail);
		pnlFacultyDetails.add(lblMaxCourseTitle);
		pnlFacultyDetails.add(lblMaxCourse);
		pnlFacultyDetails.add(lblOverloadedTitle);
		pnlFacultyDetails.add(lblOverloaded);
		
		TitledBorder borderFacultyDetails = BorderFactory.createTitledBorder(lowerEtched, "Faculty Details");
		pnlFacultyDetails.setBorder(borderFacultyDetails);
		pnlFacultyDetails.setToolTipText("Faculty Details");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		add(scrollCatalog, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		add(scrollFaculty, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		add(pnlActions, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		add(pnlCourseDetails, c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		add(pnlFacultyDetails, c);
	}

	/**
	 * Performs an action based on the given ActionEvent.
	 * @param e user event that triggers an action.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAssignFacultyToCourse) {
			int catalogRow = tableCatalog.getSelectedRow();
			int facultyRow = tableFaculty.getSelectedRow();
			if (catalogRow == -1) {
				JOptionPane.showMessageDialog(this, "No course selected in the catalog.");
			} else if (facultyRow == -1) {
				JOptionPane.showMessageDialog(this, "No faculty selected in the directory.");
			} else {
				try {
					Course c = catalog.getCourseFromCatalog(tableCatalog.getValueAt(catalogRow, 0).toString(), tableCatalog.getValueAt(catalogRow, 1).toString());
					Faculty f = facultyDirectory.getFacultyById(tableFaculty.getValueAt(facultyRow, 2).toString());
					
					if (!RegistrationManager.getInstance().addFacultyToCourse(c, f)) {
						JOptionPane.showMessageDialog(this, "Course cannot be added to faculty's schedule.");
					}
					updateCourseDetails(c);
					updateFacultyDetails(f);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(this, iae.getMessage());
				}
			}
			updateTables();
		} else if (e.getSource() == btnRemoveFacultyFromCourse) {
			int catalogRow = tableCatalog.getSelectedRow();
			int facultyRow = tableFaculty.getSelectedRow();
			if (catalogRow == -1) {
				JOptionPane.showMessageDialog(this, "No course selected in the catalog.");
			} else if (facultyRow == -1) {
				JOptionPane.showMessageDialog(this, "No faculty selected in the directory.");
			} else {
				Course c = catalog.getCourseFromCatalog(tableCatalog.getValueAt(catalogRow, 0).toString(), tableCatalog.getValueAt(catalogRow, 1).toString());
				Faculty f = facultyDirectory.getFacultyById(tableFaculty.getValueAt(facultyRow, 2).toString());
				
				if (!RegistrationManager.getInstance().removeFacultyFromCourse(c, f)) {
					JOptionPane.showMessageDialog(this, "Course cannot be removed from faculty's schedule.");
				}
				updateCourseDetails(c);
				updateFacultyDetails(f);
			}
			updateTables();
		} else if (e.getSource() == btnReset) {
			int facultyRow = tableFaculty.getSelectedRow();
			if (facultyRow == -1) {
				JOptionPane.showMessageDialog(this, "No faculty selected in the directory.");
			} else {
				Faculty f = facultyDirectory.getFacultyById(tableFaculty.getValueAt(facultyRow, 2).toString());
				RegistrationManager.getInstance().resetFacultySchedule(f);
				updateTables();
			}
		} 
		
		this.repaint();
		this.validate();
	}
	
	/**
	 * Updates the catalog and schedule tables.
	 */
	public void updateTables() {
		catalogTableModel.updateData();
		facultyTableModel.updateData();
	}
	
	/**
	 * Updates the pnlCourseDetails with full information about the most
	 * recently selected course.
	 * @param c most recently selected course
	 */
	private void updateCourseDetails(Course c) {
		if (c != null) {
			lblName.setText(c.getName());
			lblSection.setText(c.getSection());
			lblTitle.setText(c.getTitle());
			lblInstructor.setText(c.getInstructorId());
			lblCredits.setText("" + c.getCredits());
			lblMeeting.setText(c.getMeetingString());
			lblEnrollmentCap.setText("" + c.getCourseRoll().getEnrollmentCap());
			lblOpenSeats.setText("" + c.getCourseRoll().getOpenSeats());
		}
	}
	
	/**
	 * Updates the pnlFacultyDetails with full information about the most
	 * recently selected faculty.
	 * @param f most recently selected faculty
	 */
	private void updateFacultyDetails(Faculty f) {
		if (f != null) {
			lblFirstName.setText(f.getFirstName());
			lblLastName.setText(f.getLastName());
			lblId.setText(f.getId());
			lblEmail.setText(f.getEmail());
			lblMaxCourse.setText("" + f.getMaxCourses());
			lblOverloaded.setText("" + f.isOverloaded());
		}
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
	
	/**
	 * FacultyDirectoryTableModel is the object underlying the JTable object that displays
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
		 * Constructs the FacultyDirectoryTableModel by requesting the latest information
		 * from the RequirementTrackerModel.
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
		 * Updates the given model with Faculty information from the FacultyDirectory.
		 */
		public void updateData() {
			data = facultyDirectory.getFacultyDirectory();
		}
	}

}
