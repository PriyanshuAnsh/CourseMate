package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the CourseNameValidator class, which validates course names based on a specific format.
 * This class includes tests for both valid and invalid course names, ensuring that proper
 * exceptions are thrown and valid names are accepted.
 * 
 * This class includes three tests:
 * 1. testIsValid - checks valid course names.
 * 2. testInvalid - checks invalid course names and the corresponding error messages.
 * 3. testValidSuffix - verifies that course names with a valid suffix are correctly identified.
 * 
 * The valid course name format is as follows:
 * - Starts with 1 to 4 letters.
 * - Followed by exactly 3 digits.
 * - Optional: a single letter suffix may follow the digits.
 * 
 * @author Priyanshu Dongre
 */
public class CourseNameValidatorTest {

	
	/**
     * Tests valid course names to ensure they are recognized by the validator.
     * 
     * @throws InvalidTransitionException if the course name does not follow the correct format
     */
	@Test
	public void testIsValid() throws InvalidTransitionException {

		CourseNameValidator c = new CourseNameValidator();

		assertTrue(c.isValid("C112"));
		assertTrue(c.isValid("C110A"));
		assertTrue(c.isValid("CS113"));
		assertTrue(c.isValid("CSC113"));
		assertTrue(c.isValid("MIER201"));
	}

	
	/**
     * Tests invalid course names to ensure they throw appropriate exceptions.
     * Verifies that the exception messages match the expected error message for each case.
     */
	@Test
	public void testInvalid() {
		CourseNameValidator c = new CourseNameValidator();

		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("C 112"));
		assertEquals("Course name can only contain letters and digits.", e1.getMessage());

		Exception e2 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CS 113"));
		assertEquals("Course name can only contain letters and digits.", e2.getMessage());

		Exception e3 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC 113"));
		assertEquals("Course name can only contain letters and digits.", e3.getMessage());

		Exception e4 = assertThrows(InvalidTransitionException.class, () -> c.isValid("MIER 201"));
		assertEquals("Course name can only contain letters and digits.", e4.getMessage());

		Exception e5 = assertThrows(InvalidTransitionException.class, () -> c.isValid("E 1"));
		assertEquals("Course name can only contain letters and digits.", e5.getMessage());

		
		Exception e7 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC116A0"));
		assertEquals("Course name cannot contain digits after the suffix.", e7.getMessage());
		
		Exception e8 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC116AB"));
		assertEquals("Course name can only have a 1 letter suffix.", e8.getMessage());
		
		Exception e9 = assertThrows(InvalidTransitionException.class, () -> c.isValid("C110A0"));
		assertEquals("Course name cannot contain digits after the suffix.", e9.getMessage());
		
		Exception e10 = assertThrows(InvalidTransitionException.class, () -> c.isValid("C10"));
		assertEquals("Invalid FSM Transition.", e10.getMessage());
		
		Exception e11 = assertThrows(InvalidTransitionException.class, () -> c.isValid("DasaniBottle101"));
		assertEquals("Course name cannot start with more than 4 letters.", e11.getMessage());
		
		Exception e12 = assertThrows(InvalidTransitionException.class, () -> c.isValid("9000"));
		assertEquals("Course name must start with a letter.", e12.getMessage()); 

	    try {
	        c.isValid("1234");
	        fail("Expected InvalidTransitionException was not thrown.");
	    } catch (InvalidTransitionException e13) {
	        assertEquals("Course name must start with a letter.", e13.getMessage());
	    }
	    
	    Exception e14 = assertThrows(InvalidTransitionException.class, () -> c.isValid("MIERT800"));
		assertEquals("Course name cannot start with more than 4 letters.", e14.getMessage()); 
		
	}
	
	 /**
     * Tests that a valid course name with a single letter suffix is recognized as valid.
     * 
     * @throws InvalidTransitionException if the course name does not follow the correct format
     */
	
	@Test
	public void testValidSuffix() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();
		
		assertTrue(c.isValid("CSC116A"));
	}

}
