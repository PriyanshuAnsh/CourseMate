package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Unit tests for the CourseRoll class.
 * 
 * This class contains test cases to validate the functionality of the CourseRoll class, 
 * including enrollment, waitlist management, and various methods for manipulating the course roll.
 * The tests cover valid and invalid cases for enrolling students, dropping students, and checking 
 * waitlist behavior.
 * 
 * @author Suyash Patel
 */
public class CourseRollTest {

	/** The CourseRoll instance used for testing */
	private CourseRoll courseRoll;

	/** A sample student instance used for testing purposes. Represents the first student in the sample group. */
	private Student student1;

	/** A sample student instance used for testing purposes. Represents the second student in the sample group. */
	private Student student2;

	/** A sample student instance used for testing purposes. Represents the third student in the sample group. */
	private Student student3;

	/** A sample student instance used for testing purposes. Represents the fourth student in the sample group. */
	private Student student4;

	/** A sample student instance used for testing purposes. Represents the fifth student in the sample group. */
	private Student student5;

	/** A sample student instance used for testing purposes. Represents the sixth student in the sample group. */
	private Student student6;

	/** A sample student instance used for testing purposes. Represents the seventh student in the sample group. */
	private Student student7;

	/** A sample student instance used for testing purposes. Represents the eighth student in the sample group. */
	private Student student8;

	/** A sample student instance used for testing purposes. Represents the ninth student in the sample group. */
	private Student student9;

	/** A sample student instance used for testing purposes. Represents the tenth student in the sample group. */
	private Student student10;

	/** A sample student instance used for testing waitlist functionality. Represents the eleventh student. */
	private Student student11;

	/**
	 * Sets up the test fixture before each test method.
	 * 
	 * This method is called before each test to set up the environment with necessary
	 * instances of Course and Student to be used in the tests.
	 */
	@BeforeEach
	public void setUp() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		courseRoll = c.getCourseRoll();
		student1 = new Student("Gus", "Fringo", "gfringo", "gfringo@gmail.com", "password");
		student2 = new Student("Alice", "Marks", "amarks", "amarks@gmail.com", "password");
		student3 = new Student("Bryson", "Brown", "bbrown", "bbrown@gmail.com", "password");
		student4 = new Student("Charlie", "Smith", "csmith", "csmith@gmail.com", "password");
		student5 = new Student("Diana", "Jones", "djones", "djones@gmail.com", "password");
		student6 = new Student("Ella", "Rivers", "erivers", "erivers@gmail.com", "password");
		student7 = new Student("Frank", "Castle", "fcastle", "fcastle@gmail.com", "password");
		student8 = new Student("Gina", "Torres", "gtorres", "gtorres@gmail.com", "password");
		student9 = new Student("Hank", "Pym", "hpym", "hpym@gmail.com", "password");
		student10 = new Student("Ivy", "Bell", "ibell", "ibell@gmail.com", "password");
		student11 = new Student("Jack", "Knight", "jknight", "jknight@gmail.com", "password"); // For waitlist testing
	}

	/**
	 * Tests the constructor of the CourseRoll class for valid and invalid cases.
	 * 
	 * This test ensures that the constructor works correctly for valid cases and throws the
	 * appropriate exceptions for invalid cases, such as when the course is null or the enrollment
	 * capacity is outside the valid range.
	 */
	@Test
	public void testConstructor() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");

		// Valid case
		assertDoesNotThrow(() -> new CourseRoll(c, 20));

		// Invalid case: null course
		assertThrows(IllegalArgumentException.class, () -> new CourseRoll(null, 20));

		// Invalid case: capacity below MIN_ENROLLMENT
		assertThrows(IllegalArgumentException.class, () -> new CourseRoll(c, 5));

		// Invalid case: capacity above MAX_ENROLLMENT
		assertThrows(IllegalArgumentException.class, () -> new CourseRoll(c, 300));
	}

	/**
	 * Tests the enroll method with full capacity and waitlist.
	 * 
	 * This test ensures that once the course is full, additional students are added to the waitlist.
	 * It checks that the waitlist does not exceed the maximum allowed number of students.
	 */
	@Test
	public void testEnrollFullCapacityAndWaitlist() {
		// Fill the main roll
		for (int i = 0; i < courseRoll.getEnrollmentCap(); i++) {
			Student student = new Student("First" + i, "Last" + i, "id" + i, "email" + i + "@example.com", "password");
			courseRoll.enroll(student);
		}

		// Ensure the roll is full
		assertEquals(0, courseRoll.getOpenSeats());

		// Fill the waitlist
		for (int i = 0; i < 10; i++) {
			Student waitlistedStudent = new Student("WaitlistFirst" + i, "WaitlistLast" + i, "waitlistId" + i,
					"waitlistEmail" + i + "@example.com", "password");
			courseRoll.enroll(waitlistedStudent);
		}

		// Ensure the waitlist is full
		assertEquals(10, courseRoll.getNumberOnWaitlist());

		// Try to enroll another student beyond the waitlist capacity
		assertFalse(courseRoll.canEnroll(student11)); // This should be false as the waitlist and roll are full
	}

	/**
	 * Tests dropping students from the waitlist.
	 * 
	 * This test ensures that when a student is dropped from the course, they can be re-enrolled.
	 */
	@Test
	public void testDropFromWaitlist() {
		courseRoll.enroll(student1);
		courseRoll.enroll(student2);

		courseRoll.drop(student1);

		assertTrue(courseRoll.canEnroll(student1));
		assertFalse(courseRoll.canEnroll(student2));
	}

	/**
	 * Tests dropping a student from the waitlist and verifies the update in the waitlist count.
	 * 
	 * This test simulates enrolling a series of students in a course and adding them to the waitlist
	 * once the course reaches its enrollment capacity. It then verifies that the number of students 
	 * on the waitlist is correct after dropping a student from the waitlist.
	 */
	@Test
	public void testDropStudentFromWaitlist() {
		for (int i = 0; i < courseRoll.getEnrollmentCap(); i++) {
			Student s = new Student("First" + i, "Last" + i, "id" + i, "email" + i + "@example.com", "password");
			courseRoll.enroll(s);
		}
		courseRoll.enroll(student1);
		courseRoll.enroll(student2);
		courseRoll.enroll(student3);
		courseRoll.enroll(student4);
		courseRoll.enroll(student5);
		courseRoll.enroll(student6);
		courseRoll.enroll(student7);
		courseRoll.enroll(student8);
		courseRoll.enroll(student9);
		courseRoll.enroll(student10);
		
		assertEquals(10, courseRoll.getNumberOnWaitlist());
		courseRoll.drop(student10);
		assertEquals(9, courseRoll.getNumberOnWaitlist());
	}

	/**
	 * Tests the canEnroll method with a full waitlist.
	 * 
	 * This test ensures that when the course and waitlist are full, additional students cannot enroll.
	 */
	@Test
	public void testCanEnrollWithFullWaitlist() {
		// Fill up the course and the waitlist
		for (int i = 1; i <= 10; i++) {
			Student student = new Student("Student" + i, "Test", "student" + i, "student" + i + "@gmail.com",
					"password");
			courseRoll.enroll(student);
		}

		for (int i = 11; i <= 20; i++) {
			Student student = new Student("Student" + i, "Test", "student" + i, "student" + i + "@gmail.com",
					"password");
			courseRoll.enroll(student);
		}

		Student extraStudent = new Student("Extra", "Student", "extra", "extra@gmail.com", "password");
		assertFalse(courseRoll.canEnroll(extraStudent));
	}
	
	/**
	 * Tests the enroll method and verifies waitlist functionality.
	 * 
	 * This test ensures that when a student is enrolled in the course beyond the capacity, 
	 * they are placed on the waitlist. It also verifies that dropping a student
	 */
	@Test
	public void testEnroll() {
		Student lastStudentInRoll = null;
		for (int i = 1; i <= 10; i++) {
			Student student = new Student("Student" + i, "Test", "student" + i, "student" + i + "@gmail.com",
					"password");
			courseRoll.enroll(student);
			lastStudentInRoll = student;
		}
		assertEquals(0, courseRoll.getNumberOnWaitlist());
		Student newStudent = new Student("Student", "Test", "student", "student@gmail.com",
				"password");
		courseRoll.enroll(newStudent);
		assertEquals(1, courseRoll.getNumberOnWaitlist());
		
		courseRoll.drop(lastStudentInRoll);
		assertEquals(0, courseRoll.getNumberOnWaitlist());
		
		assertEquals(4, newStudent.getSchedule().getScheduleCredits());
	}

}
