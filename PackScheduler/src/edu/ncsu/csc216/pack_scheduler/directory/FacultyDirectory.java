package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;


/**
 * The FacultyDirectory class manages a list of Faculty objects. It provides 
 * methods to load faculty records from a file, add new faculty, remove 
 * faculty by their ID, retrieve a specific faculty by their ID, and save 
 * the faculty directory to a file. This class also includes a method for 
 * hashing passwords using the SHA-256 algorithm.
 * 
 * @author Priyanshu Dongre
 */
public class FacultyDirectory {

	/** The list of Faculty members in the directory */
    private LinkedList<Faculty> facultyDirectory;
    
    /** The hash algorithm used for password encryption */
    private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash 
	 * @return the encoded digest of the hash algorithm in base64
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Creates an empty faculty directory.
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}
	
	/**
	 * Creates an empty faculty directory.  All faculty in the previous
	 * list are list unless saved by the user.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/**
	 * Constructs the faculty directory by reading in student information
	 * from the given file.  Throws an IllegalArgumentException if the 
	 * file cannot be found.
	 * @param fileName file containing list of students
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	
	/**
	 * Adds a Faculty to the directory.  Returns true if the faculty is added and false if
	 * the faculty is unable to be added because their id matches another faculty's id.
	 * 
	 * This method also hashes the faculty's password for internal storage.
	 * 
	 * @param firstName faculty's first name
	 * @param lastName faculty's last name
	 * @param id faculty's id
	 * @param email faculty's email
	 * @param password faculty's password
	 * @param repeatPassword faculty's repeated password
	 * @param maxCourse faculty's max courses.
	 * @return true if added
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourse) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		
		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);
		
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		//If an IllegalArgumentException is thrown, it's passed up from Student
		//to the GUI
		Faculty faculty = null;
		
		faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourse);

		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty s = facultyDirectory.get(i);
			if (s.getId().equals(faculty.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
	}
	
	/**
	 * Removes the faculty with the given id from the list of faculty with the given id.
	 * Returns true if the faculty is removed and false if the faculty is not in the list.
	 * @param facultyId faculty's id
	 * @return true if removed
	 */
	public boolean removeFaculty(String facultyId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty f = facultyDirectory.get(i);
			if (f.getId().equals(facultyId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Returns all faculty in the directory with a column for first name, last name, and id.
	 * @return String array containing faculties first name, last name, and id.
	 */
	public String[][] getFacultyDirectory() {
		String [][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty f = facultyDirectory.get(i);
			directory[i][0] = f.getFirstName();
			directory[i][1] = f.getLastName();
			directory[i][2] = f.getId();
		}
		return directory;
	}
	
	/**
	 * Retrieves a Faculty from the faculty directory by their ID.
	 * Iterates through the faculty directory to find a student whose ID matches the provided ID.
	 * If a match is found, the faculty is returned. If no match is found, null is returned.
	 * 
	 * @param id the ID of the student to be retrieved
	 * @return the Faculty object with the matching ID, or null if no match is found
	 */
	
	public Faculty getFacultyById(String id) {
		for(int i = 0; i < facultyDirectory.size(); i++) {
			Faculty currentFaculty = facultyDirectory.get(i);
			if(currentFaculty.getId().equals(id)) {
				return currentFaculty;
			}
		}
		return null;
	}
	
	/**
	 * Saves all faculties in the directory to a file.
	 * @param fileName name of file to save faculties to.
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
}
