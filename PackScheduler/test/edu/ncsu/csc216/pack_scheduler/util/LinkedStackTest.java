package edu.ncsu.csc216.pack_scheduler.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;

/**
 * Unit tests for the LinkedStack class.
 * 
 * This class contains test cases to validate the functionality of the LinkedStack class, 
 * including push and pop operations, capacity management, and edge cases such as 
 * attempting to push beyond capacity or pop from an empty stack.
 * 
 * Author: Suyash Patel
 */
public class LinkedStackTest {

	/** The stack instance to be tested */
	private LinkedStack<Integer> stack;

	/**
	 * Sets up the test environment before each test case.
	 * Initializes a new LinkedStack with a capacity of 5.
	 */
	@BeforeEach
	public void setUp() {
		stack = new LinkedStack<>(5); // Initial capacity of 5 for testing
	}

	/**
	 * Tests the constructor for valid and invalid capacity values.
	 * Verifies that a stack can be created with a capacity of 0, but not with a negative capacity.
	 */
	@Test
	public void testConstructorValid() {
		assertDoesNotThrow(() -> new LinkedStack<>(0)); // Valid case with 0 capacity
		assertThrows(IllegalArgumentException.class, () -> new LinkedStack<>(-1)); // Invalid case with negative capacity
	}

	/**
	 * Tests the push operation by adding valid elements to the stack.
	 * Verifies that the size increases after each push.
	 */
	@Test
	public void testPushValidElement() {
		stack.push(10);
		stack.push(20);
		assertEquals(2, stack.size());
	}

	/**
	 * Tests the push operation with a null element.
	 * Verifies that an IllegalArgumentException is thrown when attempting to push a null element onto the stack.
	 */
	@Test
	public void testPushNullElement() {
		assertThrows(IllegalArgumentException.class, () -> stack.push(null)); // Pushing null should throw an exception
	}

	/**
	 * Tests the push operation when the stack reaches its capacity.
	 * Verifies that an IllegalArgumentException is thrown when attempting to push beyond the stack's capacity.
	 */
	@Test
	public void testPushCapacityReached() {
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		assertThrows(IllegalArgumentException.class, () -> stack.push(6)); // Should throw exception as capacity is reached
	}

	/**
	 * Tests the pop operation by removing the most recently pushed element.
	 * Verifies that the correct element is returned and the stack size decreases.
	 */
	@Test
	public void testPopValid() {
		stack.push(100);
		stack.push(200);
		assertEquals(200, stack.pop()); // Should return the last pushed element
		assertEquals(1, stack.size()); // Stack size should decrease
	}

	/**
	 * Tests the pop operation on an empty stack.
	 * Verifies that an EmptyStackException is thrown when attempting to pop from an empty stack.
	 */
	@Test
	public void testPopEmptyStack() {
		assertThrows(EmptyStackException.class, () -> stack.pop()); // Popping from an empty stack should throw exception
	}

	/**
	 * Tests the isEmpty method.
	 * Verifies that the method returns true for an empty stack and false after an element is pushed.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(stack.isEmpty()); // Stack should be empty initially
		stack.push(15);
		assertFalse(stack.isEmpty()); // Stack should not be empty after pushing an element
	}

	/**
	 * Tests the size method.
	 * Verifies that the size of the stack is updated correctly after each push operation.
	 */
	@Test
	public void testSize() {
		assertEquals(0, stack.size()); // Stack should have size 0 initially
		stack.push(30);
		assertEquals(1, stack.size()); // Stack size should increase after pushing an element
		stack.push(60);
		assertEquals(2, stack.size()); // Stack size should increase with another element
	}

	/**
	 * Tests the setCapacity method with a valid capacity.
	 * Verifies that the capacity can be increased, and it does not throw an exception when reducing capacity within limits.
	 */
	@Test
	public void testSetCapacityValid() {
		stack.push(5);
		stack.push(10);
		stack.setCapacity(4); // Set new capacity to 4
		assertDoesNotThrow(() -> stack.setCapacity(2)); // Should not throw if reducing within limits
	}

	/**
	 * Tests the setCapacity method with invalid capacities.
	 * Verifies that an IllegalArgumentException is thrown when attempting to set a capacity less than the current stack size 
	 * or setting a negative capacity.
	 */
	@Test
	public void testSetCapacityInvalid() {
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(2)); // Capacity less than current size should throw exception
		assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(-1)); // Negative capacity should throw exception
	}
}
