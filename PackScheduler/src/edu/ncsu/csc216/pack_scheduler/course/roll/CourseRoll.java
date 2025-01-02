package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * the CourseRoll class
 * 
 * @author Suyash Patel
 */
public class CourseRoll {

	/** Minimum enrollment capacity */
	private static final int MIN_ENROLLMENT = 10;

	/** Maximum enrollment capacity */
	private static final int MAX_ENROLLMENT = 250;

	/** Waitlist capacity */
	private static final int WAITLIST_CAPACITY = 10;

	/** Enrollment capacity for the roll */
	private int enrollmentCap;

	/** List of students in the course */
	private LinkedAbstractList<Student> roll;

	/** Queue of students */
	private LinkedQueue<Student> waitlist;

	/** Private Course course */
	private Course course;

	/**
	 * Constructs a CourseRoll for the specified course with a given enrollment capacity.
	 * Initializes the roll list and the waitlist with the provided enrollment capacity.
	 * 
	 * @param course the course associated with this course roll
	 * @param enrollmentCap the maximum number of students that can be enrolled in the course
	 * @throws IllegalArgumentException if the course is null
	 */
	public CourseRoll(Course course, int enrollmentCap) {
		if (course == null) {
			throw new IllegalArgumentException("Course is null");
		}
		this.course = course;
		roll = new LinkedAbstractList<>(enrollmentCap);
		//waitlist = new ArrayQueue<>(WAITLIST_CAPACITY);
		
		waitlist = new LinkedQueue<>(WAITLIST_CAPACITY);
		setEnrollmentCap(enrollmentCap);
	}

	/**
	 * Returns the enrollment capacity of the roll.
	 * 
	 * @return the enrollment capacity
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets the enrollment capacity of the roll.
	 * 
	 * @param enrollmentCap the capacity to set
	 * @throws IllegalArgumentException if the enrollment capacity is less than
	 *                                  MIN_ENROLLMENT, greater than MAX_ENROLLMENT,
	 *                                  or less than the current number of enrolled
	 *                                  students
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT || enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Invalid enrollment capacity.");
		}
		this.enrollmentCap = enrollmentCap;
		roll.setCapacity(enrollmentCap);
	}

	/**
	 * Enrolls the given student in the roll if there is room.
	 * 
	 * @param student the student to enroll
	 * @throws IllegalArgumentException if the student is null, there is no room in
	 *                                  the class, or the student is already
	 *                                  enrolled
	 */
	public void enroll(Student student) {
//		if (student == null) {
//			throw new IllegalArgumentException("Cannot enroll null student.");
//		}
//		if (!canEnroll(student)) {
//			throw new IllegalArgumentException("Cannot enroll student.");
//		}
//		try {
//			roll.add(roll.size(), student);
//		} catch (IllegalArgumentException e) {
//			throw new IllegalArgumentException("Error enrolling student.", e);
//		}
		if (student == null) {
			throw new IllegalArgumentException("Cannot enroll null student.");
		}
		if (roll.size() < enrollmentCap) {
			roll.add(roll.size(), student);
		} else if (waitlist.size() < WAITLIST_CAPACITY) {
			waitlist.enqueue(student);
		} else {
			throw new IllegalArgumentException("Cannot enroll student; roll and waitlist are full.");
		}
	}

	/**
	 * Drops the given student from the roll.
	 * 
	 * @param student the student to drop
	 * @throws IllegalArgumentException if the student is null or not enrolled
	 */
	public void drop(Student student) {
//		int index = -1;
//		for (int i = 0; i < roll.size(); i++) {
//			if (roll.get(i).equals(student)) {
//				index = i;
//				break;
//			}
//		}
//		if (index != -1) {
//			roll.remove(index);
//			if (!waitlist.isEmpty()) {
//				Student nextStudent = waitlist.dequeue();
//				roll.add(roll.size(), nextStudent);
//			}
//		} else {
//			ArrayQueue<Student> tempQueue = new ArrayQueue<>(WAITLIST_CAPACITY);
//			boolean found = false;
//
//			while (!waitlist.isEmpty()) {
//				Student s = waitlist.dequeue();
//				if (!s.equals(student) || found) {
//					tempQueue.enqueue(s);
//				} else {
//					found = true;
//				}
//			}
//
//			waitlist = tempQueue;
//
//			if (!found) {
//				throw new IllegalArgumentException("Student not found in the waitlist.");
//			}
//		}
		
		
		if(student == null) {
			throw new IllegalArgumentException();
		}
		
		int index = -1;
		
		//Checks if the student is in the roll or no, by iterating through the roll. If the student is found,
		//Variable index will be the index of that student in roll, else the student doesn't exist in the roll.
		for(int i = 0; i < roll.size(); i++) {
			
			if(roll.get(i).equals(student)) {
				index = i;
				break;
			}
		}
		
		if(index != -1) {
			roll.remove(index);
			if(!waitlist.isEmpty()) {
				Student waitlistedStudent = waitlist.dequeue();
				roll.add(waitlistedStudent);
				waitlistedStudent.getSchedule().addCourseToSchedule(course);
				
			}
			return;
		}
		
		LinkedQueue<Student> tempQueue = new LinkedQueue<>(WAITLIST_CAPACITY);
//		int waitlistSize = waitlist.size();
//		boolean stxudentDoesnotExist = false;
		if(index == -1) {
			while(!waitlist.isEmpty()) {
				Student waitlistStudent = waitlist.dequeue();
				if(!waitlistStudent.equals(student)) {
					tempQueue.enqueue(waitlistStudent);
				}
			}
			
//			if(waitlistSize == tempQueue.size()) {
//				studentDoesnotExist = true;
//			}
			
			while(!tempQueue.isEmpty()) {
				Student tempListStudent = tempQueue.dequeue();
				waitlist.enqueue(tempListStudent);
			}
			
		}
		
//		if(studentDoesnotExist) {
//			throw new IllegalArgumentException();
//		}
		
	}

	/**
	 * Returns the number of open seats in the roll.
	 * 
	 * @return the number of open seats
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Checks if the given student can enroll in the course.
	 * 
	 * @param student the student to check
	 * @return true if the student can enroll, false otherwise
	 */
	public boolean canEnroll(Student student) {
		if (student == null) {
			return false;
		}

		for (int i = 0; i < roll.size(); i++) {
			if (roll.get(i).equals(student)) {
				return false;
			}
		}

		for (int i = 0; i < waitlist.size(); i++) {
			Student s = waitlist.dequeue();
			waitlist.enqueue(s);
			if (s.equals(student)) {
				return false;
			}
		}

		return getOpenSeats() > 0 || waitlist.size() < WAITLIST_CAPACITY;
	}

	/**
	 * Returns the number of elements currently on the waitlist.
	 * 
	 * @return the size of the waitlist.
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

}
