package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;



/**
* Tests StudentRecordIO.
* Referenced CourseRecordIOTest by Dr. Sarah Heckman
* Some guidance and code provided by the Lab 02 CSC217 Instructions
* @author Priyanshu Dongre, Suyash Patel
*/
public class StudentRecordIOTest {

/** Valid student records */
private final String validTestFile = "test-files/student_records.txt";

/** Invalid student records */
private final String invalidTestFile = "test-files/invalid_student_records.txt";

/** Nonexistent file */
private final String nonexistentFile = " .txt";

/** Expected results for student records from line 1 of student_records.txt */
private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";

/** Expected results for student records from line 2 of student_records.txt */
private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";

/** Expected results for student records from line 3 of student_records.txt */
private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";

/** Expected results for student records from line 4 of student_records.txt */
private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";

/** Expected results for student records from line 5 of student_records.txt */
private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";

/** Expected results for student records from line 6 of student_records.txt */
private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";

/** Expected results for student records from line 7 of student_records.txt */
private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";

/** Expected results for student records from line 8 of student_records.txt */
private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";

/** Expected results for student records from line 9 of student_records.txt */
private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";

/** Expected results for student records from line 10 of student_records.txt */
private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

/** Expected number of students from valid test file */
private int expectedValidStudentsSize = 10; 

/** Array of expected results from student_records.txt */
private String [] validStudents = {validStudent0, validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
        validStudent6, validStudent7, validStudent8, validStudent9};

/** Array of ordered students from student_records.txt */
private String[] orderedStudents = {validStudent3, validStudent6, validStudent4, validStudent5, validStudent2, validStudent8,
		validStudent0, validStudent9, validStudent1, validStudent7};


/** Hashed password */
private String hashPW;

/** Algorithm to hash password */
private static final String HASH_ALGORITHM = "SHA-256";

/** 
 * Hashes password before each test runs - for security purposes.
 * Provided by Lab 02 CSC 217 Instructions
 * 
 */

@BeforeEach
public void setUp() {
    try {
        String password = "pw";
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        digest.update(password.getBytes());
        hashPW = Base64.getEncoder().encodeToString(digest.digest());
       
        for (int i = 0; i < validStudents.length; i++) {
            orderedStudents[i] = orderedStudents[i].replace(",pw,", "," + hashPW + ",");
        }
    } catch (NoSuchAlgorithmException e) {
        fail("Unable to create hash during setup");
    }
}

/**
 * Tests readStudentRecords with a file of valid students.
 */
@Test
public void testReadValidStudentRecords() {
    try {
        // Call StudentRecordIO.readStudentRecords to read the valid test file.
         //Store the ArrayList that it returns.
        SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);

        // Check that "students" and "validStudents" contain the same number of Students.
        assertEquals(expectedValidStudentsSize, students.size(), 
            "Tests that StudentRecordIO creates 10 Students from student_records.txt.");

         //Check that the Students are listed in the proper order with the correct state.
        for (int i = 0; i < validStudents.length; i++) {
            //The previous commented-out version is from Lab 2
        	//assertEquals(validStudents[i], students.get(i).toString(), 
        	//New version below from Lab 3
        	assertEquals(orderedStudents[i], students.get(i).toString(), 
                "Tests that StudentRecordIO creates Student from line " + (i + 1) + " with proper state.");
        }
    } catch (FileNotFoundException e) {
         //Catch exception if student_records.txt not found
        fail("Unexpected error reading " + validTestFile);
    }
}

/**
 * Tests readStudentRecords with a file of invalid students.
 */
@Test
public void testReadInvalidStudentRecords() {
    try {

        SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFile);
        
        assertEquals(0, students.size(), "The file is invalid. Student's shouldn't be read.");
        
    } catch (FileNotFoundException e) {
         //Catch exception if student_records.txt not found
        fail("Unexpected error reading " + invalidTestFile);
    }
    catch(IllegalArgumentException ie) {
    	// Expected exception, so no action needed.
    }
}

/**
 * Tests that readStudentRecords throws a FileNotFoundException if passed a nonexistent file.
 */
@Test
public void testNonexistentFile() {
     //Assert that StudentRecordsIO throws a FileNotFoundException
    assertThrows(FileNotFoundException.class, () -> StudentRecordIO.readStudentRecords(nonexistentFile));
}

/**
 * Tests writeStudentRecords for one Student.
 */
@Test
public void testWriteStudentRecordsOneStudent() {
    SortedList<Student> oneStudent = new SortedList<Student>();
    oneStudent.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

    try {
        StudentRecordIO.writeStudentRecords("test-files/test_output_testWriteStudentRecordsOneStudent.txt", oneStudent);
    } catch (IOException e) {
			fail("Cannot write to course records file");
		}

    checkFiles("test-files/expected_student_records.txt", "test-files/test_output_testWriteStudentRecordsOneStudent.txt");
}

// This test method below may be optional unless needed for coverage.
///**
// * Tests writeStudentRecords for ten Students.
// */
//@Test
//public void testWriteStudentRecordsTenStudents() {
//
//    try {
//        StudentRecordIO.writeStudentRecords("test-files/test_output_testWriteStudentRecordsTenStudents.txt", validStudents);
//    } catch (IOException e) {
//			fail("Cannot write to course records file");
//		}
//
//    checkFiles("expected_full_student_records.txt", "test-files/test_output_testWriteStudentRecordsTenStudents.txt");
//}

/**
 * Tests that writeStudentRecords throws an IOException when lacking permission to write to the given file directory.
 * This test will fail locally but should succeed on Jenkins.
 * Provided by Lab 02 CSC 217 Instructions
 */
@Test
public void testWriteStudentRecordsNoPermissions() {
    SortedList<Student> students = new SortedList<Student>();
    students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
   
    
    String filePath = "/home/sesmith5/actual_student_records.txt";
    Exception exception = assertThrows(IOException.class, 
            () -> StudentRecordIO.writeStudentRecords(filePath, students));
    assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
}

/**
 * Helper method to compare two files for the same contents
 * Provided by Lab 02 CSC 217 Instructions
 * @param expFile expected output
 * @param actFile actual output
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
