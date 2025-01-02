package edu.ncsu.csc216.pack_scheduler.io;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests the FacultyRecordIO class which handles the reading and writing of Faculty records.
 * This test class includes test cases for reading valid and invalid faculty records, 
 * handling nonexistent files, and writing faculty records.
 * 
 * It uses a LinkedList to store Faculty objects and checks their correctness by 
 * comparing against expected output files.
 * 
 * The test file names and expected contents are pre-defined as constants for testing.
 * 
 * @author Priyanshu Dongre
 */
public class FacultyRecordIOTest {

	 /** File containing valid faculty records */
    private static final String VALID_TEST_FILE = "test-files/faculty_records.txt";
    
    /** File containing invalid faculty records */
    private static final String INVALID_TEST_FILE = "test-files/invalid_faculty_records.txt";
    
    /** Expected valid faculty record at index 0 */
    private static final String VALID_FACULTY_0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
    
    /** Expected valid faculty record at index 1 */
    private static final String VALID_FACULTY_1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
    
    /** Expected valid faculty record at index 2 */
    private static final String VALID_FACULTY_2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
    
    /** Expected valid faculty record at index 3 */
    private static final String VALID_FACULTY_3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
    
    /** Expected valid faculty record at index 4 */
    private static final String VALID_FACULTY_4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
    
    /** Expected valid faculty record at index 5 */
    private static final String VALID_FACULTY_5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
    
    /** Expected valid faculty record at index 6 */
    private static final String VALID_FACULTY_6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
    
    /** Expected valid faculty record at index 7 */
    private static final String VALID_FACULTY_7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
    
    /** Array of expected valid faculty records */
	private static final String[] VALID_FACULTIES = { VALID_FACULTY_0, VALID_FACULTY_1, VALID_FACULTY_2, VALID_FACULTY_3, VALID_FACULTY_4, VALID_FACULTY_5, VALID_FACULTY_6, VALID_FACULTY_7 };
	
	
	/**
     * Tests reading valid faculty records from a file.
     * Asserts that the correct number of Faculty objects are created and 
     * their attributes match the expected values.
     */
	@Test
	public void testReadValidStudentRecords() {
	    try {
	        // Call StudentRecordIO.readStudentRecords to read the valid test file.
	         //Store the ArrayList that it returns.
	        LinkedList<Faculty> faculties = FacultyRecordIO.readFacultyRecords(VALID_TEST_FILE);

	        // Check that "students" and "validStudents" contain the same number of Students.
	        assertEquals(VALID_FACULTIES.length, faculties.size(), 
	            "Tests that StudentRecordIO creates 10 Students from student_records.txt.");

	         //Check that the Students are listed in the proper order with the correct state.
	        for (int i = 0; i < VALID_FACULTIES.length; i++) {
	            
	        	assertEquals(VALID_FACULTIES[i], faculties.get(i).toString(), 
	                "Tests that FacultyRecordIO creates Student from line " + (i + 1) + " with proper state.");
	        }
	    } catch (FileNotFoundException e) {
	         //Catch exception if student_records.txt not found
	        fail("Unexpected error reading " + VALID_TEST_FILE);
	    }
	}
	
	/**
     * Tests reading invalid faculty records from a file.
     * Asserts that no Faculty objects are created from a file of invalid records.
     */
	@Test
	public void testReadInvalidStudentRecords() {
	    try {

	        LinkedList<Faculty> faculties = FacultyRecordIO.readFacultyRecords(INVALID_TEST_FILE);
	        
	        assertEquals(0, faculties.size(), "The file is invalid. Student's shouldn't be read.");
	        
	    } catch (FileNotFoundException e) {
	         //Catch exception if student_records.txt not found
	        fail("Unexpected error reading " + INVALID_TEST_FILE);
	    }
	    catch(IllegalArgumentException ie) {
	    	// Expected exception, so no action needed.
	    }
	}
	
	/**
     * Tests that FacultyRecordIO.readFacultyRecords throws a FileNotFoundException 
     * when trying to read from a nonexistent file.
     */
	@Test
	public void testNonexistentFile() {
	     //Assert that StudentRecordsIO throws a FileNotFoundException
	    assertThrows(FileNotFoundException.class, () -> FacultyRecordIO.readFacultyRecords(".txt"));
	}
	
	
	/**
     * Tests writing a single Faculty record to a file.
     * The output file is compared to an expected file to ensure correctness.
     */
	@Test
	public void testWriteStudentRecordsOneStudent() {
	    LinkedList<Faculty> faculty = new LinkedList<Faculty>();
	    faculty.add(new Faculty("Ansh", "Gonzalvis", "AGonza", "orci.Donec@ametmassaQuisque.com", "AGonza1234", 3));

	    try {
	        FacultyRecordIO.writeFacultyRecords("test-files/test_output_testWriteFacultyRecordsOneFaculty.txt", faculty);
	    } catch (IOException e) {
				fail("Cannot write to course records file");
			}

	    checkFiles("test-files/expectedFaculty1Output.txt", "test-files/test_output_testWriteFacultyRecordsOneFaculty.txt");
	}
	
	/**
     * Helper method to compare two files for identical content.
     * Provided by Lab 02 CSC 217 Instructions.
     * 
     * @param expFile expected output file
     * @param actFile actual output file
     */
	private void checkFiles(String expFile, String actFile) 
	{
	    try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
	        Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
	       
	        while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
	            String exp = expScanner.nextLine();
	            String act = actScanner.nextLine();
	            assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
	            //The third argument helps with debugging!
	        }
	        if (expScanner.hasNextLine()) {
	            fail("The expected results expect another line " + expScanner.nextLine());
	        }
	        if (actScanner.hasNextLine()) {
	            fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
	        }
	       
	        expScanner.close();
	        actScanner.close();
	    } catch (IOException e) {
	        fail("Error reading files.");
	    }
	}


}
