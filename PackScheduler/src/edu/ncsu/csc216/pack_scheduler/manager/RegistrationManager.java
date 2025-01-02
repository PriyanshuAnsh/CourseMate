package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Manages the registration system for students and the registrar.
 * Handles course catalog, student directory, and user authentication.
 * Uses the singleton design pattern.
 * @author Priyanshu Dongre, Suyash Patel
 */
public class RegistrationManager {

	 /** Singleton instance of RegistrationManager */
    private static RegistrationManager instance;
    /** Manages course catalog */
    private CourseCatalog courseCatalog;
    /** Manages student directory */
    private StudentDirectory studentDirectory;
    /** Manages faculty directory */
    private FacultyDirectory facultyDirectory;
    /** Registrar user for the system */
    private User registrar;
    /** Currently logged-in user */
    private User currentUser = null;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/** Properties file for registrar credentials */
	private static final String PROP_FILE = "registrar.properties";

	
	/**
     * Private constructor for the singleton RegistrationManager.
     * Initializes the course catalog and student directory, and creates the registrar.
     */
	private RegistrationManager() {
	    courseCatalog = new CourseCatalog();
	    studentDirectory = new StudentDirectory();
	    facultyDirectory = new FacultyDirectory();
	    createRegistrar();
	}

	
	/**
     * Loads registrar information from a properties file and creates the registrar user.
     * Hashes the registrar's password before storing it.
     */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
     * Hashes a password using SHA-256 encryption.
     * @param pw the plain text password
     * @return the hashed password as a Base64 encoded string
     * @throws IllegalArgumentException if the hashing algorithm is not found
     */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	
	/**
     * Returns the singleton instance of the RegistrationManager.
     * @return the RegistrationManager instance
     */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	
	/**
     * Gets the course catalog managed by the RegistrationManager.
     * @return the course catalog
     */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	
	/**
     * Gets the student directory managed by the RegistrationManager.
     * @return the student directory
     */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
     * Gets the faculty directory managed by the RegistrationManager.
     * @return the faculty directory
     */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	
	/**
     * Logs in a user by verifying their ID and password. Supports both students and registrar.
     * @param id the user's ID
     * @param password the user's plain text password
     * @return true if login is successful, false otherwise
     */
	public boolean login(String id, String password) {
		
		//User cannot login, if somebody is already logged in.
		if(currentUser != null) {
			return false;
		}
		
		Student s = studentDirectory.getStudentById(id);
		
		Faculty f = facultyDirectory.getFacultyById(id);

		String localHashPW = hashPW(password);
		
		if(s != null) {
			if(s.getPassword().equals(localHashPW)) {
				currentUser = s;
				return true;	
			} else {
				return false;
			}
		} else if(f != null) {
			if(f.getPassword().equals(localHashPW)) {
				currentUser = f;
				return true;	
			} else {
				return false;
			}
		} else {
			if(registrar.getId().equals(id)) {
				if(registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				}
				return false;
			}
		}
		
		throw new IllegalArgumentException("User doesn't exist.");		
	}

	
	/**
     * Logs out the current user by resetting the currentUser to the registrar.
     */
	public void logout() {
		currentUser = null;
	}

	/**
     * Gets the currently logged-in user.
     * @return the current user, or null if no user is logged in
     */
	public User getCurrentUser() {
		return currentUser;
	}
	
	
	/**
     * Clears the course catalog and student directory data.
     */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        
	        
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}

	/**
     * Adds a course to a faculty member's schedule.
     * 
     * @param course  the course to be added to the faculty's schedule
     * @param faculty the faculty to whom the course is being assigned
     * @return true if the course is successfully added
     * @throws IllegalArgumentException if the current user is not the registrar
     */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		
		if (currentUser == null || !(currentUser.equals(registrar))) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
		
			faculty.getSchedule().addCourseToSchedule(course);
			return true;
			
		
	}
	
	/**
     * Removes a course from a faculty member's schedule.
     * 
     * @param course  the course to be removed from the faculty's schedule
     * @param faculty the faculty from whose schedule the course is being removed
     * @return true if the course is successfully removed
     * @throws IllegalArgumentException if the current user is not the registrar
     */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		
		if (currentUser == null || !(currentUser.equals(registrar))) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
		
		faculty.getSchedule().removeCourseFromSchedule(course);
		
		return true;	
	}
	
	/**
     * Resets a faculty member's schedule, clearing all assigned courses.
     * 
     * @param faculty the faculty whose schedule is to be reset
     * @throws IllegalArgumentException if the current user is not the registrar
     */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser == null || !(currentUser.equals(registrar))) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
		
			faculty.getSchedule().resetSchedule();
		
		
	}
	/**
     * Nested class representing the Registrar user.
     */
	private static class Registrar extends User {
		 /**
         * Constructs a registrar user with the given details.
         * @param firstName the registrar's first name
         * @param lastName the registrar's last name
         * @param id the registrar's ID
         * @param email the registrar's email
         * @param hashPW the registrar's hashed password
         */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}