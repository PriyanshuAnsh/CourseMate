package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This class represents an exception that is thrown when an invalid transition occurs
 * in the course name validation process. It is used to indicate that the course name
 * does not follow the expected format during validation.
 * 
 * The exception can be thrown with a custom message or with a default message
 * indicating the issue.
 * 
 * @author Priyanshu Dongre
 */
public class InvalidTransitionException extends Exception {
	

	private static final long serialVersionUID = 1L;
	
	/** Default message for invalid FSM transition */
	private final static String DEFAULT_MESSAGE = "Invalid FSM Transition.";
	
	/**
	 * Constructs an InvalidTransitionException with a custom error message.
	 * 
	 * @param message the detailed error message explaining the reason for the exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Constructs an InvalidTransitionException with the default message.
	 * The default message is "Invalid FSM Transition."
	 */
	
	public InvalidTransitionException() {
		this(DEFAULT_MESSAGE);
	}
}