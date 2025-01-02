package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * The Faculty Class
 * 
 * @author Suyash Patel
 */
public class Faculty extends User {

	/** The maximum number of courses a faculty member can teach */
	private int maxCourses;

	/** The minimum number of courses a faculty member can teach */
	public static final int MIN_COURSES = 1;

	/** The maximum number of courses a faculty member can teach */
	public static final int MAX_COURSES = 3;

	/** The faculty schedule associated with the faculty member */
	private FacultySchedule schedule;

	/**
	 * Faculty constructor that initializes the fields for first name, last name,
	 * id, email, password, and maxCourses.
	 * 
	 * @param firstName  the faculty's first name
	 * @param lastName   the faculty's last name
	 * @param id         the faculty's ID
	 * @param email      the faculty's email
	 * @param password   the faculty's password
	 * @param maxCourses the maximum number of courses the faculty member can teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		schedule = new FacultySchedule(id);
		setMaxCourses(maxCourses);
	}

	/**
	 * Sets the maximum number of courses this faculty member can teach.
	 * 
	 * @param maxCourses the number of courses to set
	 * @throws IllegalArgumentException if maxCourses is less than MIN_COURSES or
	 *                                  greater than MAX_COURSES
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Returns the maximum number of courses the faculty member can teach.
	 * 
	 * @return the maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Overrides the hashCode method for Faculty.
	 * 
	 * @return hash code value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getEmail(), getFirstName(), getId(), getLastName(), maxCourses, getPassword());
	}

	/**
	 * Compares this Faculty object with another.
	 * 
	 * @param obj the object to compare with
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Faculty)) {
			return false;
		}
		Faculty other = (Faculty) obj;
		return Objects.equals(getEmail(), other.getEmail()) && Objects.equals(getFirstName(), other.getFirstName())
				&& Objects.equals(getId(), other.getId()) && Objects.equals(getLastName(), other.getLastName())
				&& maxCourses == other.maxCourses && Objects.equals(getPassword(), other.getPassword());
	}

	/**
	 * Returns a string representation of this Faculty.
	 * 
	 * @return string representation of Faculty
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCourses;
	}

	/**
	 * Retrieves the faculty member's schedule.
	 * 
	 * @return the FacultySchedule object associated with this faculty member.
	 */
	public FacultySchedule getSchedule() {
	    return schedule;
	}

	/**
	 * Checks if the faculty member's schedule is overloaded.
	 * A schedule is considered overloaded if the number of scheduled courses exceeds the maximum allowable courses.
	 * 
	 * @return true if the faculty member is overloaded, false otherwise.
	 */

	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}

}