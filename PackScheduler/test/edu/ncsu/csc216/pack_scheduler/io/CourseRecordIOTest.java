
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests CouresRecordIO.
 * 
 * @author Priyanshu Dongre, Suyash Patel
 */
public class CourseRecordIOTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";

	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";

	/** Faculty Records */
	private final String facultyTestFile = "test-files/faculty_records_extended.txt";

	/** Expected results for valid courses in course_records.txt - line 1 */
	private final String validCourse1 = "CSC116,Intro to Programming - Java,001,3,null,10,MW,910,1100";
	/** Expected results for valid courses in course_records.txt - line 2 */
	private final String validCourse2 = "CSC116,Intro to Programming - Java,002,3,null,10,MW,1120,1310";
	/** Expected results for valid courses in course_records.txt - line 3 */
	private final String validCourse3 = "CSC116,Intro to Programming - Java,003,3,null,10,TH,1120,1310";
	/**
	 * Expected results for valid courses in course_records.txt - line 5 (line 4 is
	 * a duplicate of line 2 b/c of how we defined Course.equals().)
	 */
	private final String validCourse4 = "CSC216,Software Development Fundamentals,001,3,null,10,TH,1330,1445";
	/** Expected results for valid courses in course_records.txt - line 6 */
	private final String validCourse5 = "CSC216,Software Development Fundamentals,002,3,null,10,MW,1330,1445";
	/** Expected results for valid courses in course_records.txt - line 7 */
	private final String validCourse6 = "CSC216,Software Development Fundamentals,601,3,null,10,A";
	/** Expected results for valid courses in course_records.txt - line 8 */
	private final String validCourse7 = "CSC217,Software Development Fundamentals Lab,202,1,null,10,M,1040,1230";
	/** Expected results for valid courses in course_records.txt - line 9 */
	private final String validCourse8 = "CSC217,Software Development Fundamentals Lab,211,1,null,10,T,830,1020";
	/** Expected results for valid courses in course_records.txt - line 10 */
	private final String validCourse9 = "CSC217,Software Development Fundamentals Lab,223,1,null,10,W,1500,1650";
	/** Expected results for valid courses in course_records.txt - line 11 */
	private final String validCourse10 = "CSC217,Software Development Fundamentals Lab,601,1,null,10,A";
	/** Expected results for valid courses in course_records.txt - line 12 */
	private final String validCourse11 = "CSC226,Discrete Mathematics for Computer Scientists,001,3,null,10,MWF,935,1025";
	/** Expected results for valid courses in course_records.txt - line 13 */
	private final String validCourse12 = "CSC230,C and Software Tools,001,3,null,10,MW,1145,1300";
	/** Expected results for valid courses in course_records.txt - line 14 */
	private final String validCourse13 = "CSC316,Data Structures and Algorithms,001,3,null,10,MW,830,945";

	/** Array to hold expected results */
	private final String[] validCourses = { validCourse1, validCourse2, validCourse3, validCourse4, validCourse5,
			validCourse6, validCourse7, validCourse8, validCourse9, validCourse10, validCourse11, validCourse12,
			validCourse13 };

	/** 
	 * A static `LinkedListRecursive` instance that stores a list of `Faculty` objects. 
	 * This list is used to manage and maintain faculty information across the application.
	 */
	private static LinkedListRecursive<Faculty> facultyList = new LinkedListRecursive<>();

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if fail to reset the file
	 */
	@Before
	public void setUp() throws Exception {
		// Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests readValidCourseRecords().
	 */
	@Test
	public void testReadValidCourseRecords() {
		try {
			SortedList<Course> courses = CourseRecordIO.readCourseRecords(validTestFile);
			assertEquals(13, courses.size());

			for (int i = 0; i < validCourses.length; i++) {
				assertEquals(validCourses[i], courses.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}

	/**
	 * Tests readInvalidCourseRecords().
	 */
	@Test
	public void testReadInvalidCourseRecords() {
		SortedList<Course> courses;
		try {
			courses = CourseRecordIO.readCourseRecords(invalidTestFile);
			assertEquals(0, courses.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests writing the courseRecord()
	 */
	@Test
	public void testWriteCourseRecord() {
		try {
			SortedList<Course> courses = CourseRecordIO.readCourseRecords(validTestFile);
			assertEquals(13, courses.size());

			CourseRecordIO.writeCourseRecords("test-files/write_courses_student_unit_test.txt", courses);
		} catch (FileNotFoundException e) {
			fail("Unexpected error writing to file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail("Unexpected error writing to file");
		} catch (Exception e) {
			fail("Unexpected error writing to file");
		}
	}

	/**
	 * tests readCourse() with faculty
	 */
	@Test
	public void testReadCourseFaculty() {
		SortedList<Course> courses;
		Faculty f = new Faculty("Sarah", "Heckman", "sesmith5", "sesmith5@ncsu.edu", "pw", 3);
		facultyList.add(f);
		assertEquals(1, facultyList.size());
		try {
			courses = CourseRecordIO.readCourseRecords(facultyTestFile);
			assertEquals(0, courses.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + facultyTestFile);
		}
	}

}
