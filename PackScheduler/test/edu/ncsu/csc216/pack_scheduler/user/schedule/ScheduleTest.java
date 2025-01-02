package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;


/**
 * Test class for Schedule functionality.
 * This class contains unit tests for the Schedule class methods,
 * including adding and removing courses, resetting the schedule,
 * and managing the schedule title.
 * @author Suyash Patel
 */
public class ScheduleTest {

	/** Schedule instance used for testing. */
	private Schedule schedule;

	 /** Course instance used for testing. */
	private Course course1;

	/** Another Course instance used for testing. */
	private Course course2;

	/** Duplicate of course1 for testing duplicate handling. */
	private Course duplicate;

	/** Course with conflicting schedule for testing conflict handling. */
	private Course conflicting;

	/**
     * Set up the test environment before each test method.
     * Initializes a new Schedule and several Course objects for testing.
     */
	@BeforeEach
	public void setUp() {
		schedule = new Schedule();
		course1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		course2 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF",
				1015, 1130);
		duplicate = course1;
		conflicting = new Course("CSC316", "Data Structures and Algorithms", "001", 3, "instructor3", 10, "TH", 1330, 1445);

	}

	/**
     * Test the Schedule constructor.
     * Verifies that a new Schedule is created with the correct default title
     * and an empty course list.
     */
	@Test
	public void testConstructor() {
		assertEquals("My Schedule", schedule.getTitle());
		assertNotNull(schedule.schedule);
		assertEquals(0, schedule.schedule.size());
	}

	/**
     * Test adding courses to the schedule.
     * Verifies successful course addition, handling of duplicate courses,
     * and proper handling of course conflicts.
     */
	@Test
	public void testAddCourseToSchedule() {
		assertTrue(schedule.addCourseToSchedule(course1));
		assertEquals(1, schedule.schedule.size());
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			schedule.addCourseToSchedule(duplicate);
		});
		assertEquals("You are already enrolled in CSC216", exception.getMessage());
		assertThrows(ConflictException.class, () -> {
			conflicting.checkConflict(course1);
		});

		IllegalArgumentException conflictException = assertThrows(IllegalArgumentException.class, () -> {
			schedule.addCourseToSchedule(conflicting);
		});
		assertEquals("Course cannot be added due to a conflict.", conflictException.getMessage());
	}

	 /**
     * Test removing courses from the schedule.
     * Verifies successful course removal and appropriate behavior
     * when attempting to remove a non-existent course.
     */
	@Test
	public void testRemoveCourseFromSchedule() {
		schedule.addCourseToSchedule(course1);
		assertEquals(1, schedule.schedule.size());
		assertTrue(schedule.removeCourseFromSchedule(course1));
		assertEquals(0, schedule.schedule.size());
		assertFalse(schedule.removeCourseFromSchedule(course2));
	}

	/**
     * Test resetting the schedule.
     * Verifies that the schedule is cleared and reset to its initial state.
     */
	@Test
	public void testResetSchedule() {
		schedule.addCourseToSchedule(course1);
		schedule.addCourseToSchedule(course2);
		assertEquals(2, schedule.schedule.size());

		schedule.resetSchedule();
		assertEquals("My Schedule", schedule.getTitle());
		assertEquals(0, schedule.schedule.size());
	}

	/**
     * Test retrieving scheduled courses.
     * Verifies that the correct course information is returned
     * in the expected format.
     */
	@Test
	public void testGetScheduledCourses() {
		schedule.addCourseToSchedule(course1);
		schedule.addCourseToSchedule(course2);

		String[][] courseArray = schedule.getScheduledCourses();
		assertNotNull(courseArray);
		assertEquals(2, courseArray.length);

		assertArrayEquals(new String[] { "CSC216", "001", "Software Development Fundamentals", "TH 1:30PM-2:45PM", "10" },
				courseArray[0]);
		assertArrayEquals(
				new String[] { "CSC226", "001", "Discrete Mathematics for Computer Scientists", "MWF 10:15AM-11:30AM", "10" },
				courseArray[1]);
	}

	/**
     * Test setting the schedule title.
     * Verifies successful title changes and proper handling of invalid inputs.
     */
	@Test
	public void testSetTitle() {
		schedule.setTitle("Fall Schedule");
		assertEquals("Fall Schedule", schedule.getTitle());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			schedule.setTitle(null);
		});
		assertEquals("Title cannot be null.", exception.getMessage());
	}
	
	
	/**
	 * Tests the ability to add courses to the schedule and checks for conflicts.
	 * 
	 * This test verifies that a course can be added to the schedule if there are no conflicts and that the same course
	 * cannot be added more than once. It also checks that attempting to add a null course returns false.
	 */
	@Test
	public void testCanAdd() {
		assertTrue(schedule.canAdd(course1));
		schedule.addCourseToSchedule(course1);
		assertFalse(schedule.canAdd(course1));
		assertFalse(schedule.canAdd(conflicting));
		
		assertFalse(schedule.canAdd(null));
		
	}
	
	
	/**
	 * Tests the calculation of total credits in the schedule.
	 * 
	 * This test verifies that when courses are added to the schedule, the total credits reflect the sum of the credits 
	 * of the added courses. It checks the total credits after adding multiple courses to ensure that the credit 
	 * calculation is accurate.
	 */
	@Test
	public void testGetScheduleCredits() {
		schedule.addCourseToSchedule(course1);
		schedule.addCourseToSchedule(course2);
		assertEquals(6, schedule.getScheduleCredits());
		Course c = new Course("CSC246", "Operating Systems", "001", 4, "Priyanshu", 10, "TH", 1630, 1900);
		schedule.addCourseToSchedule(c);
		assertEquals(10, schedule.getScheduleCredits());
	}
}
