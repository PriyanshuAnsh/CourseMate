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
 * Tests for ArrayStack
 * @author Suyash Patel
 */
public class ArrayStackTest {
	
	/**
	 * A reference variable to ArrayStack to store instances of Integers.
	 */
	private ArrayStack<Integer> stack;
	
	/**
	 * Perform before each test
	 */
	@BeforeEach
	public void setUp() {
		stack = new ArrayStack<>(5);
	}
	
	/**
	 * Tests the valid constructor
	 */
	@Test
    public void testConstructorValid() {
        assertDoesNotThrow(() -> new ArrayStack<>(0));
        assertThrows(IllegalArgumentException.class, () -> new ArrayStack<>(-1));
    }

	/**
	 * Tests pushing a valid element
	 */
    @Test
    public void testPushValidElement() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size());
    }

    /**
     * Tests pushing a null element
     */
    @Test
    public void testPushNullElement() {
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
    }

    /**
     * Tests pushing with capacity is reached
     */
    @Test
    public void testPushCapacityReached() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertThrows(IllegalArgumentException.class, () -> stack.push(6));
    }

    /**
     * Tests popping a valid stack
     */
    @Test
    public void testPopValid() {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
        assertEquals(1, stack.size());
    }

    /**
     * Tets popping in an empty stack
     */
    @Test
    public void testPopEmptyStack() {
        assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    /**
     * Tests if stack is empty
     */
    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(100);
        assertFalse(stack.isEmpty());
    }

    /**
     * Tests the size of the stack
     */
    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.push(15);
        assertEquals(1, stack.size());
        stack.push(30);
        assertEquals(2, stack.size());
    }

    /**
     * Tests setting the capacity
     */
    @Test
    public void testSetCapacityValid() {
        stack.push(1);
        stack.push(2);
        stack.setCapacity(3);
        assertDoesNotThrow(() -> stack.setCapacity(2));
    }

    /**
     * Tests setting an invalid capacity
     */
    @Test
    public void testSetCapacityInvalid() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(2));
        assertThrows(IllegalArgumentException.class, () -> stack.setCapacity(-1));
    }
	

}
