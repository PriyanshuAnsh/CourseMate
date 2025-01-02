package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;


/**
 * A custom implementation of ArrayList that extends AbstractList.
 * This implementation prevents null elements and duplicates.
 * The list automatically grows when capacity is reached.
 * 
 * @param <E> the type of elements in this list
 * @author Priyanshu Dongre
 */
public class ArrayList<E> extends AbstractList<E> {

    /** Initial capacity of the ArrayList */
	private static final int INIT_SIZE = 10;
	
	
	 /** Array to store list elements */
	private E[] list;
	
	/** Current number of elements in the list */
	private int size;
	
	
	/**
     * Constructs an empty list with an initial capacity of INIT_SIZE.
     */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		size = 0;
		list = (E[]) new Object[INIT_SIZE];
	}
	
	/**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right.
     *
     * @param index index at which the specified element is to be inserted
     * @param item element to be inserted
     * @throws NullPointerException if the specified element is null
     * @throws IllegalArgumentException if the element is already in the list
     * @throws IndexOutOfBoundsException if the index is out of range (index less than 0 or index greater than or equal to size)
     */
	public void add(int index, E item) {
		
		if(item == null) {
			throw new NullPointerException();
		}
		if(size == 0 && index > 1) {
			throw new IllegalArgumentException();
		}
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		
		if(size == 0) {
			list[index] = item;
			size++;
			return;
		}
		
		
		if(index == size && size > 1) {
			growArray();
			list[size++] = item;
			return;
		}	
		
		for(int i = 0; i < size; i++) {
			if(list[i].equals(item)) {
				throw new IllegalArgumentException();
			}
		}
		
		//This will shift the elements to right
		for(int currentIdx = size - 1; currentIdx > index - 1; currentIdx--) {
			int proceedingIdx = currentIdx + 1;
			E currentElement = list[currentIdx];
			list[proceedingIdx] = currentElement;
			list[currentIdx] = null;
		}
		
		//Once the elements are shifted to right, we will insert the element at this index.
		
		list[index] = item;
		size++;
	}
	
	
	 /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left.
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range (index less than 0 or index greater than or equal to size)
     */
	
	public E remove(int index) {
		
		if(size == 0) {
			throw new IndexOutOfBoundsException();
		}
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		E returnValue = list[index];
		if(index == size - 1) {
			
			list[index] = null;
			size--;
			return returnValue; 
		}
		
		for(int currentIdx = index; currentIdx < size; currentIdx++) {
			int proceedingIdx = currentIdx + 1;
			
			list[currentIdx] = list[proceedingIdx];
		}
		list[size - 1] = null;
		size--;
		return returnValue;
		
	}
	
	
	/**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
	@Override
	public int size() {
		
		return size;
	}

	
	/**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index less than 0 or index greater than or equal to size)
     */
	@Override
	public E get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}
	
	/**
     * Increases the capacity of this ArrayList instance, if necessary,
     * to ensure that it can hold at least the current size * 2 elements.
     */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] newArray = (E[]) new Object[size * 2];
		
		for(int i = 0; i < size; i++) {
			newArray[i] = list[i];
		}
		list = newArray;
	}
	
	
	/**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws NullPointerException if the specified element is null
     * @throws IndexOutOfBoundsException if the index is out of range (index less than 0 or index greater than or equal to size)
     * @throws IllegalArgumentException if the element is already in the list
     */
	public E set(int index, E element) {
		
		
		if(size == 0) {
			throw new IndexOutOfBoundsException();
		}
		if(element == null) {
			throw new NullPointerException();
		}
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		E earlierElement = list[index];
		
		for(int i = 0; i < size; i++) {
			if(list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		list[index] = element;
		return earlierElement;
	}

}
