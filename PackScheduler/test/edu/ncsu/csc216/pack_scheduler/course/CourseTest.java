package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.assertAll;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests the Course class.
 * 
 * Note that test methods for all getters have been omitted. They will be tested
 * as we test other methods.
 * 
 * @author Priyanshu Dongre, Suyash Patel
 */
public class CourseTest {

	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	
	/**
	 * Enrollment cap of 20 for testing purpose
	 */
	private static final int ENROLLMENT_CAP = 20;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;

	/**
	 * Tests constructing a Course with meeting days and times.
	 */
	@Test
	public void testCourseWithTimes() {
		// Test a valid construction
		Course c = assertDoesNotThrow(
				() -> new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME),
				"Should not throw exception");

		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));
	}

	/**
	 * Tests constructing an arranged course.
	 */
	@Test
	public void testCourseArranged() {
		// Test a valid construction and make sure values are correct
		Course c = assertDoesNotThrow(() -> new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A"),
				"Should not throw exception");

		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals("A", c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(0, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(0, c.getEndTime(), "incorrect end time"));
	}

	/**
	 * Tests setName(). This can ONLY be done through the Course constructor. The
	 * test only considers valid values.
	 * 
	 * @param courseName valid course name to test
	 */
	@ParameterizedTest
	@ValueSource(strings = { "CSC216", "E115", "MA141", "HESF101", "CSC116" })
	public void testSetNameValid(String courseName) {

		// Testing valid names
		Course course = assertDoesNotThrow(() -> new Course(courseName, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP,
				MEETING_DAYS, START_TIME, END_TIME), "Should not throw exception");
		assertEquals(courseName, course.getName(), "Failed test with valid course name - " + courseName);
	}

//	/**
//	 * Tests setName() with null and empty string values. These should throw
//	 * NullPointerExceptions.
//	 */
//	@Test
//	public void testSetNameNullAndEmpty() {
//		Exception e1 = assertThrows(NullPointerException.class,
//				() -> new Course(null, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME));
//		assertEquals("Course name cannot be null.", e1.getMessage(),
//				"Incorrect exception thrown with null course name.");
//
//		Exception e2 = assertThrows(IllegalArgumentException.class,
//				() -> new Course("", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME, END_TIME));
//		assertEquals("Course name cannot be empty.", e2.getMessage(),
//				"Incorrect exception thrown with empty course name.");
//	}

	/**
	 * Tests setName() with invalid course names (non-null and non-empty values).
	 * These should throw IllegalArgumentExceptions.
	 */
	@Test
	public void testSetNameInvalid() {
		String[] invalidCourseNames = { "ENG 110", "HESFQ1010", "101", "CSC 216", "101ext", "HESFQM101", "HSEF0109",
				"CSC 2167", " CSC 216", "CSC\t216", "C!C 216", "CSC 21!", "HESF101A0" };
		for (String invalidCourseName : invalidCourseNames) {
			Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourseName, TITLE,
					SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));

			assertEquals("Invalid course name.", e1.getMessage(),
					"Incorrect exception thrown with invalid course name - " + invalidCourseName);
		}
	}

	/**
	 * Tests setTitle().
	 */
	@Test
	public void testSetTitleValid() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(ENROLLMENT_CAP, c.getCourseRoll().getEnrollmentCap(), "incorrect enrollment cap"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));

		// Valid set
		c.setTitle("A new title");
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals("A new title", c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));
	}

	/**
	 * Tests setTitle with invalid input.
	 * 
	 * @param invalid invalid input for the test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	public void testSetTitleInvalid(String invalid) {

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> new Course(NAME, invalid, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals("Invalid title.", exception.getMessage(),
				"Incorrect exception thrown with invalid input - " + invalid);
	}

	/**
	 * Tests setSection().
	 */
	@Test
	public void testSetSectionValid() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(ENROLLMENT_CAP, c.getCourseRoll().getEnrollmentCap(), "incorrect enrollment cap"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));

		// Test valid section
		c.setSection("002");
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals("002", c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(ENROLLMENT_CAP, c.getCourseRoll().getEnrollmentCap(), "incorrect enrollment cap"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));
	}

	/**
	 * Tests setSection with invalid input.
	 * 
	 * @param invalid invalid input for the test
	 */
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "00", "0012", "abc" })
	public void testSetSectionInvalid(String invalid) {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> c.setSection(invalid));
		assertEquals("Invalid section.", exception.getMessage(),
				"Incorrect exception thrown with invalid input - " + invalid);
	}

	/**
	 * Tests setCredits().
	 */
	@Test
	public void testSetCreditsValid() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));

		// Test valid credits
		c.setCredits(4);
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(4, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));
	}

	/**
	 * Tests setCredits with invalid input.
	 * 
	 * @param invalid invalid input for the test
	 */
	@ParameterizedTest
	@ValueSource(ints = { 0, 6 })
	public void testSetCreditsInvalid(int invalid) {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> c.setCredits(invalid));
		assertEquals("Invalid credits.", exception.getMessage(),
				"Incorrect exception thrown with invalid input - " + invalid);
	}

	/**
	 * Tests setInstructorId().
	 */
	@Test
	public void testSetInstructorIdValid() {
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));

		// Test valid instructor id
		c.setInstructorId("jctetter");
		assertAll("Course", () -> assertEquals(NAME, c.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c.getCredits(), "incorrect credits"),
				() -> assertEquals("jctetter", c.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c.getEndTime(), "incorrect end time"));
	}

//	/**
//	 * Tests setInstructorId with invalid input.
//	 * 
//	 * @param invalid invalid input for the test
//	 */
//	@ParameterizedTest
//	@NullAndEmptySource
//	public void testSetInstructorIdInvalid(String invalid) {
//		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
//
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> c.setInstructorId(invalid));
//		assertEquals("Invalid instructor id.", exception.getMessage(),
//				"Incorrect exception thrown with invalid input - " + invalid);
//	}

	/**
	 * Tests setMeetingDaysAndTime().
	 * 
	 * @param meetingString     valid meeting string
	 * @param startTime         valid start time
	 * @param endTime           valid end time
	 * @param expectedStartTime expected start time from the first three arguments
	 * @param expectedEndTime   expected end time from the first three arguments
	 */
	@ParameterizedTest(name = "{index} => meetingString={0}, startTime={1}, endTime={2}, expectedStartTime={3}, expectedEndTime={4}")
	@CsvSource({ "TH,1300,1445,1300,1445", "MF,1300,1445,1300,1445", "MF,1015,1445,1015,1445", "MF,1015,1130,1015,1130",
			"W,830,945,830,945", "H,1130,1245,1130,1245", "A,0,0,0,0" })
	public void testSetMeetingDaysAndTimesValid(String meetingString, int startTime, int endTime, int expectedStartTime,
			int expectedEndTime) {
		// The code below is commented out until you make some changes to Course.
		// Once those are made, remove the line of code fail() and uncomment the
		// provided tests.
		// fail();

		// Test valid course with meeting times (not arranged)
		Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertAll("Course", () -> assertEquals(NAME, c1.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c1.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c1.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c1.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c1.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c1.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c1.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c1.getEndTime(), "incorrect end time"));

		// Test valid course with arranged
		Course c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A");
		assertAll("Course", () -> assertEquals(NAME, c2.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c2.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c2.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c2.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c2.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals("A", c2.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(0, c2.getStartTime(), "incorrect start time"),
				() -> assertEquals(0, c2.getEndTime(), "incorrect end time"));

		c1.setMeetingDaysAndTime(meetingString, startTime, endTime);
		assertEquals(meetingString, c1.getMeetingDays());
		assertEquals(expectedStartTime, c1.getStartTime());
		assertEquals(expectedEndTime, c1.getEndTime());

		c2.setMeetingDaysAndTime(meetingString, startTime, endTime);
		assertEquals(meetingString, c2.getMeetingDays());
		assertEquals(expectedStartTime, c2.getStartTime());
		assertEquals(expectedEndTime, c2.getEndTime());
	}

	/**
	 * Tests invalid meeting days and times
	 * 
	 * @param meetingString valid meeting string
	 * @param startTime     valid start time
	 * @param endTime       valid end time
	 */
	@ParameterizedTest(name = "{index} => meetingString={0}, startTime={1}, endTime={2}")
	@CsvSource({ "AM,1330,1445", "XYZ,1330,1445", "m,1330,1445", "MTWS,1330,1445", "MWM,1330,1445", "TMT,1330,1445",
			"WFW,1330,1445", "MHH,1330,1445", "MWFTF,1330,1445", "MW,-1,1445", "MW,1330,-1", "MW,2400,1445",
			"MW,1330,2400", "MW,1360,1445", "MW,1330,1360", "MW,2300,1445", "MW,1330,1200", "A,1300,1445", "A,300,0",
			"A,0,1300" })
	public void testSetMeetingDaysAndTimesInvalid(String meetingString, int startTime, int endTime) {
		// The code below is commented out until you make some changes to Course.
		// Once those are made, remove the line of code fail() and uncomment the
		// provided tests.
//		fail();

		Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertAll("Course", () -> assertEquals(NAME, c1.getName(), "incorrect name"),
				() -> assertEquals(TITLE, c1.getTitle(), "incorrect title"),
				() -> assertEquals(SECTION, c1.getSection(), "incorrect section"),
				() -> assertEquals(CREDITS, c1.getCredits(), "incorrect credits"),
				() -> assertEquals(INSTRUCTOR_ID, c1.getInstructorId(), "incorrect instructor id"),
				() -> assertEquals(MEETING_DAYS, c1.getMeetingDays(), "incorrect meeting days"),
				() -> assertEquals(START_TIME, c1.getStartTime(), "incorrect start time"),
				() -> assertEquals(END_TIME, c1.getEndTime(), "incorrect end time"));

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> c1.setMeetingDaysAndTime(meetingString, startTime, endTime));
		assertEquals("Invalid meeting days and times.", exception.getMessage(),
				"Incorrect exception thrown with invalid input.");
		assertEquals(MEETING_DAYS, c1.getMeetingDays(), "incorrect meeting days");
		assertEquals(START_TIME, c1.getStartTime(), "incorrect start time");
		assertEquals(END_TIME, c1.getEndTime(), "incorrect end time");

	}

	/**
	 * Tests getMeetingString().
	 */
	@Test
	public void testGetMeetingString() {
		// The code below is commented out until you make some changes to Course.
		// Once those are made, remove the line of code fail() and uncomment the
		// provided tests.
//		fail();

		Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals("MW 1:30PM-2:45PM", c1.getMeetingString());
		Course c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 900, 1035);
		assertEquals("MW 9:00AM-10:35AM", c2.getMeetingString());
		Course c3 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A");
		assertEquals("Arranged", c3.getMeetingString());
		Course c4 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "TH", 1145, 1425);
		assertEquals("TH 11:45AM-2:25PM", c4.getMeetingString());
		Course c5 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "TH", 1200, 1300);
		assertEquals("TH 12:00PM-1:00PM", c5.getMeetingString());
	}

	/**
	 * Tests that the equals method works for all Course fields.
	 */
	@Test
	public void testEqualsObject() {
		Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c3 = new Course(NAME, "Different", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c4 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c5 = new Course(NAME, TITLE, SECTION, 5, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c6 = new Course(NAME, TITLE, SECTION, CREDITS, "Different", ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c7 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "TH", START_TIME, END_TIME);
		Course c8 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 830, END_TIME);
		Course c9 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, 1400);
		Course c10 = new Course("CSC217", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);

		// Test for equality in both directions
		assertEquals(c1, c2);
		assertEquals(c2, c1);

		// Test for each of the fields
		assertNotEquals(c1, c3);
		assertNotEquals(c1, c4);
		assertNotEquals(c1, c5);
		assertNotEquals(c1, c6);
		assertNotEquals(c1, c7);
		assertNotEquals(c1, c8);
		assertNotEquals(c1, c9);
		assertNotEquals(c1, c10);
	}

	/**
	 * Test for the getLongDisplayArray() method.
	 */
	@Test
	public void testGetLongDisplayArray() {
		// Create a Course object with known values
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);

		// Call the getLongDisplayArray method
		String[] longDisplay = c.getLongDisplayArray();

		// Check that the array has the expected values
		assertNotNull(longDisplay, "Array should not be null.");
		assertEquals(7, longDisplay.length, "Array length should be 7.");

		// Validate each field in the returned array
		assertEquals("CSC216", longDisplay[0], "Course name is incorrect.");
		assertEquals("001", longDisplay[1], "Section is incorrect.");
		assertEquals("Software Development Fundamentals", longDisplay[2], "Title is incorrect.");
		assertEquals("3", longDisplay[3], "Credits are incorrect.");
		assertEquals("sesmith5", longDisplay[4], "Instructor ID is incorrect.");
		assertEquals("MW 1:30PM-2:45PM", longDisplay[5], "Meeting string is incorrect.");
		assertEquals("", longDisplay[6], "Last field should be an empty string.");
	}

	/**
	 * Test for getLongDisplayArray() for an arranged course (meetingDays "A").
	 */
	@Test
	public void testGetLongDisplayArrayArranged() {
		// Create an arranged course
		Course c = new Course("CSC217", "Software Development Lab", "002", 1, "mbaker", 10, "A");

		// Call the getLongDisplayArray method
		String[] longDisplay = c.getLongDisplayArray();

		// Check that the array has the expected values
		assertNotNull(longDisplay, "Array should not be null.");
		assertEquals(7, longDisplay.length, "Array length should be 7.");

		// Validate each field in the returned array
		assertEquals("CSC217", longDisplay[0], "Course name is incorrect.");
		assertEquals("002", longDisplay[1], "Section is incorrect.");
		assertEquals("Software Development Lab", longDisplay[2], "Title is incorrect.");
		assertEquals("1", longDisplay[3], "Credits are incorrect.");
		assertEquals("mbaker", longDisplay[4], "Instructor ID is incorrect.");
		assertEquals("Arranged", longDisplay[5], "Meeting string for arranged course is incorrect.");
		assertEquals("", longDisplay[6], "Last field should be an empty string.");
	}

	/**
	 * Tests that hashCode works correctly.
	 */
	@Test
	public void testHashCode() {
		Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c3 = new Course(NAME, "Different", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c4 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c5 = new Course(NAME, TITLE, SECTION, 5, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c6 = new Course(NAME, TITLE, SECTION, CREDITS, "Different", ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		Course c7 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "TH", START_TIME, END_TIME);
		Course c8 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 830, END_TIME);
		Course c9 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, 1400);
		Course c10 = new Course("CSC217", TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);

		// Test for the same hash code for the same values
		assertEquals(c1.hashCode(), c2.hashCode());

		// Test for each of the fields
		assertNotEquals(c1.hashCode(), c3.hashCode());
		assertNotEquals(c1.hashCode(), c4.hashCode());
		assertNotEquals(c1.hashCode(), c5.hashCode());
		assertNotEquals(c1.hashCode(), c6.hashCode());
		assertNotEquals(c1.hashCode(), c7.hashCode());
		assertNotEquals(c1.hashCode(), c8.hashCode());
		assertNotEquals(c1.hashCode(), c9.hashCode());
		assertNotEquals(c1.hashCode(), c10.hashCode());
	}

	/**
	 * Tests that toString returns the correct comma-separated value.
	 */
	@Test
	public void testToString() {
		Course c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		String s1 = "CSC216,Software Development Fundamentals,001,3,sesmith5,20,MW,1330,1445";
		assertEquals(s1, c1.toString());

		Course c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A");
		String s2 = "CSC216,Software Development Fundamentals,001,3,sesmith5,20,A";
		assertEquals(s2, c2.toString());
	}
	
	/**
	 * Tests the behavior of the `Course` class when the instructor ID is set to `null`. 
	 * Verifies that no exceptions are thrown during object creation, that the instructor ID is 
	 * correctly set to `null`, and that the `toString` method generates the correct output.
	 */
	@Test
	public void testSetInstructorIdNull() {
		assertDoesNotThrow(() -> new Course("CSC216", "Software Development", "002", 3, null, 10, "MWF", 1145, 1300));
		assertNull(new Course("CSC216", "Software Development", "002", 3, null, 10, "MWF", 1145, 1300).getInstructorId());
		String result = "CSC216,Software Development,002,3,null,10,MWF,1145,1300";
		assertEquals(result, new Course("CSC216", "Software Development", "002", 3, null, 10, "MWF", 1145, 1300).toString());
	}

}