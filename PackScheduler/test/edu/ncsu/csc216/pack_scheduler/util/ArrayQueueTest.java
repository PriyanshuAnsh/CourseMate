package edu.ncsu.csc216.pack_scheduler.util;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests the functionality of the ArrayQueue class.
 * This class contains unit tests to verify the correct behavior of
 * the ArrayQueue implementation for enqueueing, dequeueing,
 * and managing the capacity of the queue.
 * @author Priyanshu Dongre
 */
class ArrayQueueTest {

	/** The queue instance to be tested */
	ArrayQueue<String> queue;
	
	 /** An array of strings for testing purposes */
	String[] testingString = {"Ansh", "Kapil", "Eli", "Rahul", "Tony", "Honey", "Jif", "Marie", "Nestle", "Jimmy" };
	
	
	/**
     * Sets up the test environment before each test case.
     * Initializes a new ArrayQueue with a capacity of 10.
     */
	@BeforeEach
	public void setUp() {
		queue = new ArrayQueue<>(10);
	}
	
	/**
     * Tests the insertion of elements into the queue.
     * Verifies that elements can be enqueued until the queue is full,
     * and that an exception is thrown when trying to enqueue beyond the capacity.
     */
	@Test
	public void testInsertElementToQueue() {
		assertTrue(queue.isEmpty());

		
		for(String names: testingString) {
			queue.enqueue(names);
		}
		assertEquals(10, queue.size());
		assertFalse(queue.isEmpty());
		
		
		try {
			queue.enqueue("Ansh");
			fail();
		} catch(IllegalArgumentException ie) {
			//Success
		}
	}
	
	/**
     * Tests the removal of elements from the queue.
     * Verifies that elements can be dequeued and that the queue
     * correctly reflects its state after the elements are removed.
     * Also checks that an exception is thrown when trying to dequeue from an empty queue.
     */
	@Test
	public void testRemovingElementFromQueue() {
		assertTrue(queue.isEmpty());
		
			queue.enqueue("Ansh");
			assertEquals("Ansh", queue.dequeue());
			
		for(String names: testingString) {
			queue.enqueue(names);
		}
		assertEquals(10, queue.size());
		
		for(int i = 0; i < 10; i++) {
			assertEquals(testingString[i], queue.dequeue());
		}
		assertTrue(queue.isEmpty());
		
		try {
			queue.dequeue();
			fail();
		} catch(NoSuchElementException nse) {
			//Success
		}
	}
	
	
	/**
     * Tests the capacity management of the queue.
     * Verifies that the queue can increase its capacity and that
     * an exception is thrown when trying to set a capacity less than the current size.
     */
	@Test
	public void testCapacity() {
		for(String names: testingString) {
			queue.enqueue(names);
		}
		assertEquals(10, queue.size());
		
		queue.setCapacity(20);
		
		for(int i = testingString.length - 1; i > -1; i--) {
			queue.enqueue(testingString[i] + Math.abs(i - testingString.length));
		}
		assertEquals(20, queue.size());
		
		
		try {
			queue.setCapacity(19);
			fail();
		} catch(IllegalArgumentException ie) {
			//Success
		}
	}

}
