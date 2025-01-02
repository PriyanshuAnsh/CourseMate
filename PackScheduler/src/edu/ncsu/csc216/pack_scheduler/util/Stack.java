package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Represents a generic Stack interface that provides methods 
 * for managing a collection of elements in a last-in, first-out (LIFO) order.
 * 
 * @param <E> the type of elements in the stack
 * 
 * This interface defines the basic operations for a stack data structure,
 * including pushing and popping elements, checking if the stack is empty, 
 * retrieving the size of the stack, and setting the stack's capacity.
 * 
 * @author Priyanshu Dongre
 */
public interface Stack<E> {

	/**
	 * Adds an element to the top of the stack.
	 * 
	 * @param element the element to be added to the stack
	 * @throws IllegalArgumentException if the element is null
	 */
	void push(E element);

	/**
	 * Removes and returns the element from the top of the stack.
	 * 
	 * @return the element removed from the top of the stack
	 */
	E pop();

	/**
	 * Checks whether the stack is empty.
	 * 
	 * @return true if the stack is empty, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the stack.
	 * 
	 * @return the size of the stack
	 */
	int size();

	/**
	 * Sets the maximum capacity of the stack.
	 * 
	 * @param c the new capacity of the stack
	 * @throws IllegalArgumentException if the specified capacity is negative 
	 *         or less than the current size of the stack
	 */
	void setCapacity(int c);
}
