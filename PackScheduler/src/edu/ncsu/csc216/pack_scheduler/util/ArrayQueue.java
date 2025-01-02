package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A generic ArrayQueue class that implements the Queue interface.
 * This queue is backed by an ArrayList and has a specified capacity.
 * 
 * @param <E> the type of elements in this queue
 * @author Priyanshu Dongre
 */
public class ArrayQueue<E> implements Queue<E> {
	
	/** The current size of the queue */
    private int size = 0;
    
    /** The maximum capacity of the queue */
    private int capacity;
    
    /** The list backing the queue */
    private ArrayList<E> list;
	
    /**
     * Constructs an ArrayQueue with a specified capacity.
     * 
     * @param capacity the maximum number of elements this queue can hold
     * @throws IllegalArgumentException if the specified capacity is negative
     */

	public ArrayQueue(int capacity) {
		this.size = 0;
		this.setCapacity(capacity);
		list = new ArrayList<>();
	}

	/**
     * Adds an element to the back of the queue if the queue is not at capacity.
     * 
     * @param element the element to be added to the queue
     * @throws IllegalArgumentException if the queue is at capacity
     */
	@Override
	public void enqueue(E element) {
		if(size < capacity) {
			list.add(element);
			size++;
		} else {
			throw new IllegalArgumentException();
		}
		
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
		E element = list.remove(0);
		size--;
		return element;
	}

	/**
     * Checks if the queue is empty.
     * 
     * @return true if the queue is empty, false otherwise
     */
	@Override
	public boolean isEmpty() {
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
		
		if(capacity < size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		
	}
	

}
