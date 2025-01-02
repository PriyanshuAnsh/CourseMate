package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * The LinkedAbstractList class
 * 
 * @author Suyash Patel
 * @param <E> the type of elements in this List
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** Front of the linked list */
	private ListNode front;
	
	/**
	 * Back of the linked list
	 */
	private ListNode back;

	/** Size of the linked list */
	private int size;

	/** Capacity of the linked list */
	private int capacity;

	/**
	 * Constructor to create a linked list
	 * 
	 * @param capacity of the list
	 * @throws IllegalArgumentException if capacity is less than 0
	 */
	public LinkedAbstractList(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException("Capacity cannot be less than 0.");
		}
		this.capacity = capacity;
		this.front = null;
		this.back = null;
		this.size = 0;
	}

	

	/**
	 * sets the capacity of the list
	 * 
	 * @param capacity of the list
	 * @throws IllegalArgumentException if capacity is less than the current size
	 */
	public void setCapacity(int capacity) {
		if (capacity < this.size) {
			throw new IllegalArgumentException("Capacity must be greater than or equal to the current size.");
		}
		this.capacity = capacity;
	}

	/**
	 * gets the data in the index of the list
	 * 
	 * @param index of the data to get
	 * @return the data at the specified index
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * method for setting the data at the index
	 * 
	 * @param index   of the data to be set
	 * @param element the data to be set at the index
	 * @return the data
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException("Element cannot be null.");
		}
		if (contains(element)) {
			throw new IllegalArgumentException("Element is already in the list.");
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		E oldData = current.data;
		current.data = element;
		return oldData;
	}

	/**
	 * adds the element at the specified index
	 * 
	 * @param index   the index to add at
	 * @param element the element to be added
	 * @throws NullPointerException      if element is null
	 * @throws IllegalArgumentException  if list contains the element or capacity is
	 *                                   exceeded
	 * @throws IndexOutOfBoundsException if index is out of bounds.
	 */
	@Override
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException("Element cannot be null.");
		}
		if (contains(element)) {
			throw new IllegalArgumentException("Duplicate elements not allowed.");
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (size >= capacity) {
			throw new IllegalArgumentException("Capacity exceeded.");
		}
		
		if (index == 0) {
			if(front == null) {
				front = new ListNode(element);
				back = front;
			} else {
				ListNode newNode = new ListNode(element);
				newNode.next = front;
				front = newNode;
			}
			
		} else if(index == size) {
			ListNode newNode = new ListNode(element);
			back.next = newNode;
			back = newNode;
			size++;
			
			return;
		} else {
			ListNode current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			current.next = new ListNode(element, current.next);
		}
		
		ListNode currentNode = front;
		while(currentNode != null) {
			if(currentNode.next == null) {
				back = currentNode;
				break;
			} else {
				currentNode = currentNode.next;
			}
		}
		size++;
	}

	/**
	 * method to remove the element at the index
	 * 
	 * @param index the index at which the data must be removed
	 * @return the removed data
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		E removedData;

		if (index == 0) {
			removedData = front.data;
			front = front.next;
		} else {
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			removedData = current.next.data;
			current.next = current.next.next;
		}
		size--;
		
		ListNode currentNode = front;
		while(currentNode != null) {
			if(currentNode.next == null) {
				back = currentNode;
				break;
			} else {
				currentNode = currentNode.next;
			}
		}
		return removedData;
	}

	/**
	 * the size of the list 
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * private listNode class
	 * 
	 * @author Suyash Patel
	 */
	private class ListNode {

		/** Data stored in the node */
		public E data;

		/** Next node in the list */
		public ListNode next;

		/**
		 * Constructor for creating a new node with data only.
		 * 
		 * @param data the data to store
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * Constructor for creating a new node with data and a reference to the next
		 * node.
		 * 
		 * @param data the data to store
		 * @param next the next node in the list
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

}
