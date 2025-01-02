/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This interface defines a method for checking schedule conflicts between activities.
 * Implementing classes will use this method to determine if two activities have conflicting schedules.
 * @author Priyanshu Dongre
 * 
 */
public interface Conflict {
	
	
	/**
	 * Checks if there is a conflict between the current activity and another activity.
	 * If the activities overlap in time, a ConflictException is thrown.
	 * 
	 * @param possibleConflictingActivity the activity to check for a conflict
	 * @throws ConflictException if there is a scheduling conflict
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;


}