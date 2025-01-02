package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A generic LinkedQueue class that implements the Queue interface.
 * This queue is backed by a linked list and has a specified capacity.
 * 
 * @param <E> the type of elements in this queue
 * @author Priyanshu Dongre
 */
public class LinkedQueue<E> implements Queue<E> {
	
	/** The current size of the queue */
    private int size;
    
    /** The linked list backing the queue */
    private LinkedAbstractList<E> list;


	/**
     * Constructs a LinkedQueue with a specified capacity.
     * 
     * @param capacity the maximum number of elements this queue can hold
     * @throws IllegalArgumentException if the specified capacity is negative
     */

	public LinkedQueue(int capacity) {
		this.size = 0; 
		list = new LinkedAbstractList<>(capacity);
	}
	/**
     * Adds an element to the back of the queue if the queue is not at capacity.
     * 
     * @param element the element to be added to the queue
     * @throws IllegalArgumentException if the queue is at capacity
     */

	@Override
	public void enqueue(E element) {
		list.add(size, element);
		size++;
	}

	/**
     * Removes and returns the element at the front of the queue.
     * 
     * @return the element removed from the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
	@Override
	public E dequeue() {
		
		if(size == 0) {
			throw new NoSuchElementException();
		}
		size--;
	
		return list.remove(0);
	}

	
	/**
     * Checks if the queue is empty.
     * 
     * @return true if the queue is empty, false otherwise
     */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	/**
     * Returns the current number of elements in the queue.
     * 
     * @return the size of the queue
     */
	@Override
	public int size() {
		return size;
	}

	/**
     * Sets the maximum capacity of the queue.
     * 
     * @param capacity the maximum capacity for the queue
     * @throws IllegalArgumentException if the specified capacity is less than the current size of the queue
     */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
		
	}

}
