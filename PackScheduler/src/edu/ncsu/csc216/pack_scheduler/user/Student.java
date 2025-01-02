package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Student class outlines an object that contains a first and last name, id,
 * email, password, and maximum allowed credits.
 * 
 * @author Priyanshu Dongre, Suyash Patel
 */
public class Student extends User implements Comparable<Student> {

	/** The maximum credits for this particular student */
	private int maxCredits;

	/** The maximum possible credits for any student */
	public static final int MAX_CREDITS = 18;

//	/** The minimum possible credits for any student */
//	public static final int MIN_CREDITS = 3;

	/** The schedule for the student */
	private Schedule schedule;

	/**
	 * Student constructs a Student object that contains a first and last name, id,
	 * email, password, and maximum allowed credits
	 * 
	 * @param firstName  student's first name
	 * @param lastName   student's last name
	 * @param id         student's id
	 * @param email      student's email
	 * @param password   student's password
	 * @param maxCredits student's maximum allowed number of credits
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {

		super(firstName, lastName, id, email, password);

		setMaxCredits(maxCredits);

		this.schedule = new Schedule();
	}

	/**
	 * Student constructs a Student object that contains a first and last name, id,
	 * email, password, and sets max credits to the default value of 18
	 * 
	 * @param firstName student's first name
	 * @param lastName  student's last name
	 * @param id        student's id
	 * @param email     student's email
	 * @param password  student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {

		super(firstName, lastName, id, email, password);

		setMaxCredits(MAX_CREDITS);

		this.schedule = new Schedule();

	}

	/**
	 * gets the max credits allowed for a Student object
	 * 
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * sets the max allowed credits of the student object to the given parameter.
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if max credits are negative, less than 3, or
	 *                                  greater than 18.
	 */
	public void setMaxCredits(int maxCredits) {

		if (maxCredits == -1 * MAX_CREDITS) {
			throw new IllegalArgumentException("Cannot have negative max credits.");
		}

		if (maxCredits < MAX_CREDITS / 6 || maxCredits > MAX_CREDITS) {

			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Returns a hash code value for this object.
	 * 
	 * @return hash code value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getEmail(), getFirstName(), getId(), getLastName(), maxCredits, getPassword());
	}

	/**
	 * Checks if this object is equal to another object.
	 * 
	 * @param obj the object to compare with
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Student)) {
			return false;
		}
		Student other = (Student) obj;
		return Objects.equals(getEmail(), other.getEmail()) && Objects.equals(getFirstName(), other.getFirstName())
				&& Objects.equals(getId(), other.getId()) && Objects.equals(getLastName(), other.getLastName())
				&& maxCredits == other.maxCredits && Objects.equals(getPassword(), other.getPassword());
	}

	/**
	 * Returns a string representation of this Student.
	 * 
	 * @return a string representation of this student
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + ","
				+ maxCredits;
	}

	/**
	 * Method to compare two student objects.
	 * 
	 * @param other -> Other Student object.
	 * @return 0 if both the objects are same, -1 if this object is lesser than
	 *         other, 1 if this object is greater than other.
	 */
	public int compareTo(Student other) {

		if (this.getLastName().compareTo(other.getLastName()) < 0) {
			return -1;
		} else if (this.getLastName().compareTo(other.getLastName()) > 0) {
			return 1;
		} else if (this.getLastName().compareTo(other.getLastName()) == 0) {
			if (this.getFirstName().compareTo(other.getFirstName()) < 0) {
				return -1;
			} else if (this.getFirstName().compareTo(other.getFirstName()) > 0) {
				return 1;
			} else if (this.getFirstName().compareTo(other.getFirstName()) == 0) {
				if (this.getId().compareTo(other.getId()) < 0) {
					return -1;
				} else if (this.getId().compareTo(other.getId()) > 0) {
					return 1;
				}
			}
		}
		return 0;
	}
	
	/**
	 * gets the schedule for the student
	 * @return the schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	/**
	 * method that checks if the user can add the course
	 * @param newCourse the course to add
	 * @return true if can add, false if can't
	 */
	public boolean canAdd(Course newCourse) {
		boolean canAdd = false;
		if(schedule.canAdd(newCourse)) {
			int currentCredits = schedule.getScheduleCredits();
			if(currentCredits + newCourse.getCredits() > maxCredits) {
				canAdd = false;
			} else {
				canAdd = true;
			}
			
		}
//		} else {
//			canAdd = false;
//		}
		return canAdd;
	}

}