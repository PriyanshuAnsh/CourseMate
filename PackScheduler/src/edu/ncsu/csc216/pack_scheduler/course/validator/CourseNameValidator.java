package edu.ncsu.csc216.pack_scheduler.course.validator;


/**
 * A class that validates course names based on specific rules. The course name must:
 * - Start with 1 to 4 letters
 * - Be followed by exactly 3 digits
 * - Optionally have a 1 letter suffix
 * 
 * This class uses a Finite State Machine (FSM) approach to validate course names.
 * It defines several states (InitialState, LetterState, NumberState, SuffixState),
 * and transitions between these states as each character of the course name is processed.
 * 
 * InvalidTransitionException is thrown when the course name violates the rules.
 * 
 * The valid course name length should be between 4 and 8 characters.
 * 
 * @author Suyash Patel
 */
public class CourseNameValidator {
	
//	/** Minimum length for a valid course name */
//	private static final int MIN_COURSE_NAME_LENGTH = 4;
//	
//	/** Maximum length for a valid course name */
//	private static final int MAX_COURSE_NAME_LENGTH = 8;

	/** Indicates if the final state is a valid end state */
	private boolean validEndState;

	/** Count of letters in the course name */
	private int letterCount;

	/** Count of digits in the course name */
	private int digitCount;

	/** The current state in the FSM */
	private State currentState;

	/**
     * Constructs a new CourseNameValidator with the initial state.
     */
	public CourseNameValidator() {
		currentState = new InitialState();
	}

	/**
     * Checks if the provided course name is valid based on the FSM rules.
     * 
     * @param courseName the name of the course to validate
     * @return true if the course name is valid, false otherwise
     * @throws InvalidTransitionException if the course name violates the rules
     * @throws IllegalArgumentException if the course name is null or has an invalid length
     */
	public boolean isValid(String courseName) throws InvalidTransitionException {

		if (courseName == null || "".equals(courseName)) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
//		if(courseName.length() < MIN_COURSE_NAME_LENGTH || courseName.length() > MAX_COURSE_NAME_LENGTH) {
//			throw new IllegalArgumentException("Invalid course name.");
//		}
		
		letterCount = 0;
		digitCount = 0;
		validEndState = false;
		currentState = new InitialState();

		for (int i = 0; i < courseName.length(); i++) {
			char c = courseName.charAt(i);
			if (Character.isLetter(c)) {
				currentState.onLetter();
			} else if (Character.isDigit(c)) {
				currentState.onDigit();
			} else {
				currentState.onOther();
			}
		}
		
		if(digitCount != 3) {
			throw new InvalidTransitionException();
		}

		return validEndState;
	}

	/**
     * Represents the initial state where the course name must start with a letter.
     */
	public class InitialState extends State {

		 /**
         * Handles a letter input in the initial state.
         */
		@Override
		public void onLetter() {
			currentState = new LetterState();
			letterCount++;
		}

		 /**
         * Handles a digit input in the initial state.
         * Throws an exception because the course name must start with a letter.
         * 
         * @throws InvalidTransitionException when a digit is the first character
         */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}

	}

	/**
     * Represents the state where letters are being processed.
     */
	public class LetterState extends State {
		
		/** Maximum number of letters allowed at the start of the course name */
		public static final int MAX_PREFIX_LETTERS = 4;

		/**
         * Handles a letter input when in the LetterState.
         * 
         * @throws InvalidTransitionException if there are more than 4 letters at the start
         */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount >= MAX_PREFIX_LETTERS) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
			letterCount++;
		}

		/**
         * Handles a digit input, transitioning to the NumberState.
         */
		@Override
		public void onDigit() throws InvalidTransitionException {
			
			currentState = new NumberState();
			digitCount++;
		}
	}

	/**
     * Represents the state where digits are being processed.
     */
	public class NumberState extends State {

		/** The exact number of digits required in a valid course name */
		private static final int COURSE_NUMBER_LENGTH = 3;

		/**
         * Handles a letter input, transitioning to the SuffixState if the correct number
         * of digits have been processed.
         * 
         * @throws InvalidTransitionException if the course name doesn't have exactly 3 digits
         */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				currentState = new SuffixState();
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}

		
		 /**
         * Handles a digit input when in the NumberState.
         * 
         * @throws InvalidTransitionException if there are more than 3 digits
         */
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount >= COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
			digitCount++;
			if (digitCount == COURSE_NUMBER_LENGTH) {
				validEndState = true;
			}

		}

	}

	/**
     * Represents the state where the optional suffix (a single letter) is being processed.
     */
	public class SuffixState extends State {

		
		/**
         * Handles a letter input, which is invalid in this state because only one letter is allowed.
         * 
         * @throws InvalidTransitionException if more than one letter suffix is present
         */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");

		}

		/**
         * Handles a digit input, which is invalid after the suffix.
         * 
         * @throws InvalidTransitionException if a digit is found after the suffix
         */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}

	}

	/**
     * An abstract class that defines the basic structure of a state in the FSM.
     * Each state must handle letter and digit inputs, and may throw exceptions for invalid transitions.
     */
	public abstract class State {

		/**
         * Handles a letter input.
         * 
         * @throws InvalidTransitionException if the transition is not valid
         */
		public abstract void onLetter() throws InvalidTransitionException;

		/**
         * Handles a digit input.
         * 
         * @throws InvalidTransitionException if the transition is not valid
         */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
         * Handles any non-letter or non-digit input, which is always invalid.
         * 
         * @throws InvalidTransitionException when an invalid character is found
         */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}

	}

}
