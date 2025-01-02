package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * The stack class which uses a stack for its implementation.
 * 
 * @param <E> the type of elements in this queue
 * @author Suyash Patel
 */
public class ArrayStack<E> implements Stack<E> {

	/**
	 * The ArrayList of elements E
	 */
	private ArrayList<E> list;

	/**
	 * the capacity field
	 */
	private int capacity;

	/**
	 * Constructs a stack with the given capacity
	 * 
	 * @param capacity of the stack
	 * @throws IllegalArgumentException if capacity is less than 0
	 */
	public ArrayStack(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be negative.");
		}
		this.capacity = capacity;
		this.list = new ArrayList<>();
	}

	/**
	 * pushes an element to the stack
	 * 
	 * @param element to be pushed
	 * @throws IllegalArgumentException if element is null or if capacity is reached
	 */
	@Override
	public void push(E element) {
		if (element == null) {
			throw new IllegalArgumentException("Element cannot be null");
		}
		if (list.size() >= capacity) {
			throw new IllegalArgumentException("Stack capacity has been reached");
		}
		list.add(element);

	}

	/**
	 * pops from the stack
	 * 
	 * @return the element popped
	 * @throws EmptyStackException if nothing is there to be popped
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(list.size() - 1);
	}

	/**
	 * checks if the stack is empty
	 * 
	 * @return true if empty, false if not
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * returns the size of the stack
	 * 
	 * @return size of the stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * sets the capacity of the stack
	 * 
	 * @param capacity of the stack
	 * @throws IllegalArgumentException if capacity is invalid
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException("Invalid capacity");
		}
		this.capacity = capacity;

	}

}
