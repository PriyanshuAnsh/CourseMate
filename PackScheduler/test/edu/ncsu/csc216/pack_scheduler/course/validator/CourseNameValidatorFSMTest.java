package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the CourseNameValidatorFSM class, which checks the validity of course names
 * based on a specific format. It validates if the course names follow the correct
 * format and throws InvalidTransitionException for invalid names.
 * 
 * This class includes two tests:
 * 1. testIsValid - verifies valid course names.
 * 2. testInvalid - verifies that invalid course names throw appropriate exceptions.
 * 
 * @author Priyanshu Dongre
 */
public class CourseNameValidatorFSMTest {

	
	/**
     * Tests valid course names to ensure they follow the correct format.
     * Verifies that the CourseNameValidatorFSM correctly identifies valid course names.
     * 
     * @throws InvalidTransitionException if the course name is invalid
     */
	@Test
	public void testIsValid() throws InvalidTransitionException {
		
		CourseNameValidatorFSM c = new CourseNameValidatorFSM();
		
		assertTrue(c.isValid("C112"));
		assertTrue(c.isValid("CS113"));
		assertTrue(c.isValid("CSC113"));
		assertTrue(c.isValid("MIER201"));
		
		assertFalse(c.isValid("E1"));
		
		
	}
	
	
	 /**
     * Tests invalid course names to ensure they throw the correct InvalidTransitionException
     * with the appropriate error messages.
     */
	
	@Test
	public void testInvalid() {
		
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid("091"));
		assertEquals("Course name must start with a letter.", e1.getMessage());
		
		Exception e2 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid(".901"));
		assertEquals("Course name can only contain letters and digits.", e2.getMessage());
		
		Exception e3 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid("MIEWP901"));
		assertEquals("Course name cannot start with more than 4 letters.", e3.getMessage());
		
		Exception e4 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid("MIE9O"));
		assertEquals("Course name must have 3 digits.", e4.getMessage());
		
		
		Exception e5 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid("MIE90O"));
		assertEquals("Course name must have 3 digits.", e5.getMessage());
		
		Exception e6 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid("MAE9000P"));
		assertEquals("Course name can only have 3 digits.", e6.getMessage());
		
		Exception e7 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid("CSC279A0"));
		assertEquals("Course name cannot contain digits after the suffix.", e7.getMessage());
		
		Exception e8 = assertThrows(InvalidTransitionException.class, () -> new CourseNameValidatorFSM().isValid("CSC279AB"));
		assertEquals("Course name can only have a 1 letter suffix.", e8.getMessage());
	}

}
