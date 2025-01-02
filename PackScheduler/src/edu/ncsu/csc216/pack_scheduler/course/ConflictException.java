/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This class represents an exception that is thrown when a scheduling conflict occurs
 * between two activities. It extends the {@link Exception} class.
 * @author Priyanshu Dongre
 * 
 */
public class ConflictException extends Exception {
	
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructs a ConflictException with the default message "Schedule conflict."
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
	
	
	/**
	 * Constructs a ConflictException with the specified detail message.
	 * 
	 * @param message the detail message for the exception
	 */
	public ConflictException(String message) {
		
		super(message);
		
	}
	
	
}