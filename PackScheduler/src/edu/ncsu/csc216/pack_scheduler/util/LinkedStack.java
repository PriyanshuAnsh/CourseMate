package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * A generic stack implementation using a linked list. This stack has a fixed capacity
 * and supports the basic stack operations: push, pop, and checking if the stack is empty.
 * 
 * @param <E> the type of elements in this stack
 * @author Suyash Patel
 */
public class LinkedStack<E> implements Stack<E> {

	 /** The linked list used to implement the stack */
    private LinkedAbstractList<E> list;

    /** The maximum capacity of the stack */
    private int capacity;

    /**
     * Constructs a LinkedStack with a specified capacity.
     * 
     * @param capacity the maximum number of elements this stack can hold
     * @throws IllegalArgumentException if the specified capacity is negative
     */
	public LinkedStack(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be negative");
		}
		this.capacity = capacity;
		this.list = new LinkedAbstractList<>(capacity);
	}

	/**
     * Pushes an element onto the stack. The element is added to the top of the stack.
     * 
     * @param element the element to be added to the stack
     * @throws IllegalArgumentException if the element is null or the stack has reached its capacity
     */
	@Override
	public void push(E element) {
		if (element == null) {
			throw new IllegalArgumentException("Element cannot be null");
		}
		if (list.size() >= capacity) {
			throw new IllegalArgumentException("Stack capacity has been reached");
		}
		list.add(list.size(), element);
	}

	/**
     * Removes and returns the element at the top of the stack.
     * 
     * @return the element removed from the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(list.size() - 1);
	}

	/**
     * Checks if the stack is empty.
     * 
     * @return true if the stack is empty, false otherwise
     */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
     * Returns the number of elements currently in the stack.
     * 
     * @return the size of the stack
     */
	
	@Override
	public int size() {
		return list.size();
	}

	/**
     * Sets the maximum capacity of the stack. If the capacity is smaller than the current size,
     * the capacity cannot be set.
     * 
     * @param capacity the new capacity for the stack
     * @throws IllegalArgumentException if the specified capacity is negative or smaller than the current size
     */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException("Invalid capacity");
		}
		this.capacity = capacity;
		list.setCapacity(capacity);

	}

}
