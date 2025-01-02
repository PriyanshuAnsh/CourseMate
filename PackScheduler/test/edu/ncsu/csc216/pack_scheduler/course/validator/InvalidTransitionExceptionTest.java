package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Tests the InvalidTransitionException class.
 * Ensures that both the default and custom exception messages are handled correctly.
 * 
 * This class includes two tests:
 * 1. testInvalidTransitionException - tests the default exception message.
 * 2. testInvalidTransitionExceptionString - tests a custom exception message.
 * 
 * @author Priyanshu Dongre
 */
public class InvalidTransitionExceptionTest {

	/**
     * Tests the default InvalidTransitionException message.
     */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException ite = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ite.getMessage());
	}
	
	/**
     * Tests the InvalidTransitionException with a custom message.
     */
	
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ite = new InvalidTransitionException("Invalid State");
		assertEquals("Invalid State", ite.getMessage());
		
	}
}
