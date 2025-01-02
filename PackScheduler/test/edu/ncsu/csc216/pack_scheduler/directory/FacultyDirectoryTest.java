package edu.ncsu.csc216.pack_scheduler.directory;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * The FacultyDirectoryTest class contains unit tests for the 
 * FacultyDirectory class.
 * It tests the core functionalities such as adding, removing, 
 * loading, saving faculties, and validating the overall behavior 
 * of the FacultyDirectory.
 * 
 * @author Priyanshu Dongre
 */
public class FacultyDirectoryTest {

	 /** File path for a valid test faculty records file */
    private static final String VALID_TEST_FILE = "test-files/faculty_records.txt";
    
    /** File path for an invalid/non-existent test file */
    private static final String INVALID_TEST_FILE = "abcd.txt";
    
    /** Sample faculty's first name */
    private static final String FIRST_NAME = "Ansh";
    
    /** Sample faculty's last name */
    private static final String LAST_NAME = "Gonzalvis";
    
    /** Sample faculty's ID */
    private static final String ID = "AGonza";
    
    /** Sample faculty's email */
    private static final String EMAIL = "AGonza@ncsu.edu";
    
    /** Sample valid password for faculty */
    private static final String PASSWORD = "AGonza1234";
    
    /** Sample incorrect password for faculty */
    private static final String WRONG_PASSWORD = "AGonza123";
    
    /** Sample maximum number of courses for faculty */
    private static final int MAX_COURSES = 3;
	

    /**
     * Sets up the testing environment by resetting the faculty_records.txt 
     * to its expected state before running each test.
     */
	@Before
	public void setUp() {		
		//Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	
	/**
     * Tests the constructor of FacultyDirectory. It verifies that a 
     * newly created FacultyDirectory is empty.
     */
	@Test
	public void testFacultyDirectory() {
		//Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
     * Tests the newFacultyDirectory() method. Ensures that all 
     * faculty records are removed from the directory after calling 
     * this method.
     */
	@Test
	public void testNewFacultyDirectory() {
		//Test that if there are students in the directory, they 
		//are removed after calling newFacultyDirectory().
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(VALID_TEST_FILE);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		fd.newFacultyDirectory();
		assertEquals(0, fd.getFacultyDirectory().length);
	}
	
	/**
     * Tests the loadFacultyFromFile() method. Verifies that a valid file 
     * is loaded correctly and that an invalid file throws an exception.
     */
	@Test
	public void testLoadFacultiesFromFile() {
		FacultyDirectory fd = new FacultyDirectory();
		FacultyDirectory ifd = new FacultyDirectory();
				
		//Test valid file
		fd.loadFacultyFromFile(VALID_TEST_FILE);
		assertEquals(8, fd.getFacultyDirectory().length);
		
		//test file not found
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			ifd.loadFacultyFromFile(INVALID_TEST_FILE);
		});
		assertEquals("Unable to read file " + INVALID_TEST_FILE, e.getMessage());
		
	}
	
	/**
     * Tests the addFaculty() method. Ensures a faculty can be added 
     * successfully and that invalid cases such as password mismatches 
     * or null passwords are handled correctly.
     */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String[][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_COURSES);
		});
		
		assertEquals("Invalid password", e.getMessage());
		IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, WRONG_PASSWORD, MAX_COURSES);
		});
		assertEquals("Passwords do not match", e1.getMessage());
		//make sure student was not added
		assertEquals(1, facultyDirectory.length);
		//test duplicate student
		assertFalse(fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		//make sure student was not added
		assertEquals(1, facultyDirectory.length);
	}
	
	/**
     * Tests the removeFaculty() method. Verifies that a faculty member 
     * can be removed by their ID and checks the resulting size of the directory.
     */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.loadFacultyFromFile(VALID_TEST_FILE);
		assertEquals(8, fd.getFacultyDirectory().length);
		assertTrue(fd.removeFaculty("bbrewer"));
		
		String[][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
	}
	
	 /**
     * Tests the getFacultyById() method. Verifies that the correct 
     * faculty is returned when searched by ID, and that null is returned 
     * if the ID does not exist.
     */
	@Test
	public void testGetFacultyById() {
		FacultyDirectory fd = new FacultyDirectory();
		fd.loadFacultyFromFile(VALID_TEST_FILE);
		
		Faculty f = fd.getFacultyById("bbrewer");
		
		Faculty test = new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 1);
		assertEquals(test, f);
		
		assertNull(fd.getFacultyById("Ansh"));
	}
	
	/**
     * Tests the saveFacultyDirectory() method. Ensures that the faculty 
     * directory is saved correctly to a file and matches the expected output.
     */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		assertEquals(1, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/testFacultyDirectorySaveDirectoryFile.txt");
		checkFiles("test-files/expectedOutputFileFromFacultyDirectorySaveDirectory.txt", "test-files/testFacultyDirectorySaveDirectoryFile.txt");
		
		
	}
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}
