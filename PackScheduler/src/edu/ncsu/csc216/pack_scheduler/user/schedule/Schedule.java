package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * The Schedule class represents a student's course schedule.
 * It allows adding and removing courses, resetting the schedule,
 * and managing the schedule's title.
 * 
 * @author Suyash Patel, Priyanshu Dongre
 */
public class Schedule {
	
	/** The title of the schedule */
	private String title;
	
	/** Custom ArrayList of Courses representing the schedule */
	public ArrayList<Course> schedule;
	
	/**
     * Constructs a new Schedule with default title "My Schedule" and an empty course list.
     */
	public Schedule() {
		this.title = "My Schedule";
		this.schedule = new ArrayList<Course>();
	}
	
	/**
     * Adds a course to the schedule.
     * 
     * @param course the course to add
     * @return true if the course was successfully added, false otherwise
     * @throws IllegalArgumentException if the course cannot be added to the schedule due to a conflict or duplicate
     */
	public boolean addCourseToSchedule(Course course) {
//		if (schedule.contains(course)) {
//			throw new IllegalArgumentException("Cannot add duplicate course.");
//		}
		try {
			for (Course scheduledCourse : schedule) {
				if(scheduledCourse.isDuplicate(course)) {
					throw new IllegalArgumentException("You are already enrolled in " + course.getName());
				}
				course.checkConflict(scheduledCourse);
			}
		} catch (ConflictException e) {
			throw new IllegalArgumentException("Course cannot be added due to a conflict.");
			
		}
		return schedule.add(course);
	}
	
	/**
     * Removes a course from the schedule.
     * 
     * @param course the course to remove
     * @return true if the course was successfully removed, false otherwise
     */
	public boolean removeCourseFromSchedule(Course course) {
		return schedule.remove(course);
	}
	
	/**
     * Resets the schedule to its initial state with default title and empty course list.
     */
	public void resetSchedule() {
		this.title = "My Schedule";
		this.schedule = new ArrayList<Course>();
	}
	
	/**
     * Returns a 2D array representation of the scheduled courses.
     * 
     * @return a 2D String array where each sub-array represents a course's short display information
     */
	public String[][] getScheduledCourses() {
		String[][] courseArray = new String[schedule.size()][];
        for (int i = 0; i < schedule.size(); i++) {
            courseArray[i] = schedule.get(i).getShortDisplayArray();
        }
        return courseArray;
	}
	
	/**
     * Sets the title of the schedule.
     * 
     * @param title the new title for the schedule
     * @throws IllegalArgumentException if the provided title is null
     */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
     * Gets the current title of the schedule.
     * 
     * @return the current title of the schedule
     */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Calculates the total credits of all courses in the schedule.
	 *
	 * @return the total number of credits as an integer.
	 */
	public int getScheduleCredits() {
		int totalCredits = 0;
		for(int i = 0; i < schedule.size(); i++) {
			totalCredits += schedule.get(i).getCredits();
		}
		
		return totalCredits;
	}
	
	/**
	 * Checks if a new course can be added to the schedule.
	 * This method checks for duplicates and conflicts with existing courses.
	 *
	 * @param newCourse the course to be added to the schedule.
	 * @return true if the course can be added, false if it cannot be added due to being a duplicate or causing a conflict.
	 */
	public boolean canAdd(Course newCourse) {
		
		if(newCourse == null) {
			return false;
		}
		
		//This will go through the schedule.
		for(int i = 0; i < schedule.size(); i++) {

			//This will check if the course is already present in the schedule.
			if(newCourse.isDuplicate(schedule.get(i))) {
				return false;
			}
			try {
				newCourse.checkConflict(schedule.get(i));
			} catch(ConflictException ce) {
				return false;
			}
		}
		
//		//If the New Course is neither a duplicate course nor it creates a conflict, then we can add this course.
//		schedule.add(newCourse);
		
		return true;
	}

}
