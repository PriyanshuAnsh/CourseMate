package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Creates the Course object and checks that all fields are valid. A Course
 * object contains information about a specific course offering, including the
 * course's name, section, credits, instructor, and meeting times. The class
 * also provides methods for comparison and validation of its fields, ensuring
 * that the name, section, credits, and other attributes follow expected rules.
 * 
 * The Course class also supports comparison via the Comparable interface and
 * equality checking with equals and hashCode methods.
 * 
 * This class extends the Activity class, inheriting its properties like title,
 * meeting days, start time, and end time.
 * 
 * The Course object is immutable after its construction, except for setting the
 * meeting days and times.
 * 
 * @author Priyanshu Dongre
 */
public class Course extends Activity implements Comparable<Course> {
//    /** Min length of name field. */
//    private static final int MIN_NAME_LENGTH = 5;
//    /** Max length of name field. */
//    private static final int MAX_NAME_LENGTH = 8;
//    /** Min letter count in name */
//    private static final int MIN_LETTER_COUNT = 1;
//    /** Max letter count in name */
//    private static final int MAX_LETTER_COUNT = 4;
//    /** Number of digits in name */
//    private static final int DIGIT_COUNT = 3;
	/** Length of section number */
	private static final int SECTION_LENGTH = 3;
	/** Max number of possible credits for a course */
	private static final int MAX_CREDITS = 5;
	/** Min number of possible credits for a course */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;

	/**
	 * Represents the course roll for the course, which contains information about
	 * the current enrollment status, including the number of students enrolled in
	 * the course.
	 * 
	 * This field is an instance of the CourseRoll class, which manages enrollment
	 * details for the course.
	 */
	private CourseRoll roll;

	/**
	 * Constructs a Course object with values for all fields.
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param enrollmentCap Maximum number of students that could be enrolled in the
	 *                      course.
	 * @param meetingDays   meeting days for Course as series of chars
	 * @param startTime     start time for Course
	 * @param endTime       end time for Course
	 *
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		setCourseRoll(enrollmentCap);

	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId,
	 * enrollmentCap and meetingDays for courses that are arranged.
	 * 
	 * @param name          name of Course
	 * @param title         title of Course
	 * @param section       section of Course
	 * @param credits       credit hours for Course
	 * @param instructorId  instructor's unity id
	 * @param meetingDays   meeting days for Course as series of chars
	 * @param enrollmentCap Maximum number of students that could enroll in the
	 *                      course.
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;

	}

	/**
	 * Sets the Course's name, validating the input format. The course name must
	 * follow the pattern "L[LLL] NNN". If the name is null, empty, or does not meet
	 * the criteria, an IllegalArgumentException is thrown.
	 * 
	 * @param name The name to set for the Course.
	 * @throws IllegalArgumentException if the name is invalid.
	 */
	private void setName(String name) {

		CourseNameValidator validator = new CourseNameValidator();

		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		if ("".equals(name)) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		try {
			if (validator.isValid(name)) {
				this.name = name;
			} else {
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}

	}

	/**
	 * Returns the Course's section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section, ensuring it is a 3-digit numeric string.
	 * 
	 * @param section The section to set for the Course.
	 * @throws IllegalArgumentException if the section is invalid.
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the Course's credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credit hours, ensuring it falls within the allowed range
	 * (1-5).
	 * 
	 * @param credits The credit hours to set.
	 * @throws IllegalArgumentException if the number of credits is invalid.
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}

		this.credits = credits;
	}

	/**
	 * Returns the Course's instructor id
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructor ID, validating that it is not null or empty.
	 * 
	 * @param instructorId The instructor ID to set.
	 * @throws IllegalArgumentException if the instructor ID is invalid.
	 */
	public void setInstructorId(String instructorId) {
		if ("".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}

		this.instructorId = instructorId;
	}

	/**
	 * Sets the course roll for the course with the specified enrollment.
	 *
	 * This method initializes the course roll by creating a new instance of
	 * CourseRoll with the provided enrollment number. The course roll represents
	 * the current enrollment status of the course.
	 *
	 * @param enrollment the number of students that could be enrolled in the course
	 */

	public void setCourseRoll(int enrollment) {
		roll = new CourseRoll(this, enrollment);
	}

	/**
	 * Retrieves the current course roll for the course.
	 *
	 * This method returns the CourseRoll object associated with this course, which
	 * contains information about the current enrollment status.
	 *
	 * @return the CourseRoll object representing the current enrollment status
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	/**
	 * Sets the meeting days and time for this Course. This method validates that
	 * the provided meetingDays, startTime, and endTime are valid and throws an
	 * IllegalArgumentException if the input is invalid.
	 * 
	 * Valid meeting days are represented by the characters 'M', 'T', 'W', 'H', and
	 * 'F' (Monday to Friday).
	 * 
	 * @param meetingDays a String representing the days of the week the course will
	 *                    meet
	 * @param startTime   the start time of the course in military time (e.g., 1330
	 *                    for 1:30 PM)
	 * @param endTime     the end time of the course in military time (e.g., 1530
	 *                    for 3:30 PM)
	 * @throws IllegalArgumentException if the input is invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		if ("A".equals(meetingDays) && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Arranged courses cannot have start or end times.");
		}

		if (!"A".equals(meetingDays)) {
			if (meetingDays.length() > 5) {
				throw new IllegalArgumentException("Invalid meeting days for a Course.");
			}
			for (int i = 0; i < meetingDays.length(); i++) {
				char day = meetingDays.charAt(i);
				if (day != 'M' && day != 'T' && day != 'W' && day != 'H' && day != 'F') {
					throw new IllegalArgumentException("Invalid meeting day: " + day);
				}
			}
		}
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(meetingDays)) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap()
				+ "," + meetingDays + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Returns a short version of the display array for this `Course`. This method
	 * overrides the `getShortDisplayArray` method and provides key details about
	 * the course including the course name, section, title, and meeting times.
	 * 
	 * @return StringArray containing a concise representation of the course's
	 *         details
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] display = new String[5];

		display[0] = this.name;
		display[1] = this.section;
		display[2] = getTitle();
		display[3] = getMeetingString();
		display[4] = "" + roll.getOpenSeats();
		return display;
	}

	/**
	 * Returns a detailed version of the display array for this `Course`. This
	 * method overrides the `getLongDisplayArray` method and provides more detailed
	 * information about the course, including the course name, section, title,
	 * credits, instructor ID, and meeting times.
	 * 
	 * @return StringArray containing an expanded representation of the course's
	 *         details
	 */

	@Override
	public String[] getLongDisplayArray() {
		String[] display = new String[7];

		display[0] = this.name;
		display[1] = this.section;
		display[2] = getTitle();
		display[3] = "" + this.credits;
		display[4] = this.instructorId;
		display[5] = getMeetingString();
		display[6] = "";

		return display;
	}

	/**
	 * Generates a hash code for this Course object based on its credits,
	 * instructorId, name, and section. This method overrides the default
	 * implementation and uses a prime number and Objects.hash to generate the hash.
	 * 
	 * @return an integer hash code value derived from the Course's fields
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(credits, instructorId, name, section);
		return result;
	}

	/**
	 * Determines whether two Course objects are equal. This method first checks if
	 * the given object is a reference to this Course object. If not, it checks if
	 * the given object is an instance of Course and compares the relevant fields:
	 * course name, section, credits, and instructor ID.
	 * 
	 * @param obj the object to be compared for equality with this Course
	 * @return true if the given object represents a Course with the same name,
	 *         section, credits, and instructor ID as this Course, false otherwise
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		return credits == other.credits && Objects.equals(instructorId, other.instructorId)
				&& Objects.equals(name, other.name) && Objects.equals(section, other.section);
	}

	/**
	 * Compares this Course object with another object for equality. Two Course
	 * objects are considered equal if they have the same credits, instructorId,
	 * name, and section. This method overrides the equals method from the Object
	 * class.
	 * 
	 * @param obj activity the object to compare to this Course object
	 * @return true if the specified object is equal to this Course, false otherwise
	 */
	@Override
	public boolean isDuplicate(Activity obj) {
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		return this.name.equals(other.getName());
	}

	/**
	 * Method to compare two Course objects based on name and section.
	 * 
	 * @param other a Course object to compare.
	 * @return 0 if both Courses have the same name and section, -1 if this Course
	 *         should precede other in a SortedList, 1 if this Course should follow
	 *         other in a SortedList.
	 */
	@Override
	public int compareTo(Course other) {

		if (this.name.compareTo(other.getName()) < 0) {
			return -1;
		} else if (this.name.compareTo(other.getName()) > 0) {
			return 1;
		} else if (this.name.compareTo(other.getName()) == 0) {
			if (this.section.compareTo(other.getSection()) < 0) {
				return -1;
			} else if (this.section.compareTo(other.getSection()) > 0) {
				return 1;
			}
		}
		return 0;
	}

}