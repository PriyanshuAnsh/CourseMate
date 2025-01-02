package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Represents a generic Queue interface that provides methods 
 * for managing a collection of elements in a first-in, first-out (FIFO) order.
 * 
 * @param <E> the type of elements in the queue
 * 
 * This interface defines the basic operations for a queue data structure,
 * including enqueueing and dequeueing elements, checking if the queue is empty, 
 * retrieving the size of the queue, and setting the queue's capacity.
 * @author Priyanshu Dongre
 */
public interface Queue<E> {

	/**
	 * Adds an element to the back of the queue.
	 * 
	 * @param element the element to be added to the queue
	 * @throws IllegalArgumentException if the element is null
	 */
	void enqueue(E element);

	/**
	 * Removes and returns the element at the front of the queue.
	 * 
	 * @return the element removed from the front of the queue
	 */
	E dequeue();

	/**
	 * Checks whether the queue is empty.
	 * 
	 * @return true if the queue is empty, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the queue.
	 * 
	 * @return the size of the queue
	 */
	int size();

	/**
	 * Sets the maximum capacity of the queue.
	 * 
	 * @param capacity the new capacity of the queue
	 * @throws IllegalArgumentException if the specified capacity is negative 
	 *         or less than the current size of the queue
	 */
	void setCapacity(int capacity);
}
