package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of a doubly linked list that extends AbstractSequentialList.
 * This class provides methods to add, set, and iterate over elements in the list, with checks
 * to prevent duplicate elements.
 * 
 * @param <E> The type of elements held in this list
 * @author Suyash Patel
 * 
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** Front of the LinkedList */
	private ListNode front;

	/** The back of the LinkedList */
	private ListNode back;

	/** The size of the list */
	private int size;

	/**
     * Constructs an empty LinkedList with front and back sentinel nodes.
     */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;

	}

	 /**
     * Sets the element at the specified index in the list.
     * 
     * @param index   The index of the element to replace
     * @param element The element to set
     * @return The element previously at the specified position
     * @throws IllegalArgumentException If the element is a duplicate
     */
	@Override
	public E set(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException("Duplicate element.");
		}
		return super.set(index, element);
	}

	/**
     * Adds an element at the specified index in the list.
     * 
     * @param index   The index where the element is to be inserted
     * @param element The element to be added
     * @throws IllegalArgumentException If the element is a duplicate
     */
	@Override
	public void add(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException("Duplicate element.");
		}
		super.add(index, element);
	}

	/**
     * Returns the number of elements in this list.
     * 
     * @return The size of the list
     */
	@Override
	public int size() {
		return size;
	}

	/**
     * Returns a list iterator over the elements in this list starting at the specified index.
     * 
     * @param index The index to start the iterator from
     * @return A ListIterator over the elements in this list
     */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}

	 /**
     * Represents a node in the LinkedList. Each node holds data and references
     * to the previous and next nodes in the list.
     * 
     * @author Suyash Patel
     */
	private class ListNode {

		/** The data stored in this node */
        private E data;
        /** The next node in the list */
        private ListNode next;
        /** The previous node in the list */
        private ListNode prev;
        
        /**
         * Constructs a ListNode with the given data.
         * 
         * @param data The data to store in the node
         */
		public ListNode(E data) {
			this(data, null, null);
		}

		/**
         * Constructs a ListNode with the given data, previous, and next nodes.
         * 
         * @param data The data to store in the node
         * @param prev The previous node in the list
         * @param next The next node in the list
         */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}

	 /**
     * An iterator over the elements in the LinkedList. Supports forward and backward traversal,
     * element removal, and setting of elements.
     * 
     * @author Suyash Patel
     */
	private class LinkedListIterator implements ListIterator<E> {

		/** The previous node in the iteration */
        private ListNode previous;
        /** The next node in the iteration */
        private ListNode next;
        /** The index of the previous element */
        private int previousIndex;
        /** The index of the next element */
        private int nextIndex;
        /** The last node that was returned by next() or previous() */
        private ListNode lastRetrieved;


        /**
         * Constructs a LinkedListIterator starting at the specified index.
         * 
         * @param index The index to start the iterator from
         * @throws IndexOutOfBoundsException If the index is out of range
         */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException("Index out of bounds.");
			}
			next = front.next;
			for (int i = 0; i < index; i++) {
				next = next.next;
			}
			previous = next.prev;
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
		}

		/**
         * Checks if there is a next element in the list.
         * 
         * @return true if there is a next element, false otherwise
         */
		@Override
		public boolean hasNext() {
			return next != back;
		}

		/**
         * Returns the next element in the list.
         * 
         * @return The next element
         * @throws NoSuchElementException If there is no next element
         */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			previous = next;
			next = next.next;
			previousIndex++;
			nextIndex++;
			return lastRetrieved.data;
		}

		/**
         * Checks if there is a previous element in the list.
         * 
         * @return true if there is a previous element, false otherwise
         */
		@Override
		public boolean hasPrevious() {
			return previous != front;
		}

		 /**
         * Returns the previous element in the list.
         * 
         * @return The previous element
         * @throws NoSuchElementException If there is no previous element
         */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			next = previous;
			previous = previous.prev;
			previousIndex--;
			nextIndex--;
			return lastRetrieved.data;
		}

		/**
		 * Returns the index of the next element in the list.
		 *
		 * @return the index of the next element
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the index of the previous element in the list.
		 *
		 * @return the index of the previous element
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes the last element retrieved by the iterator. If no element has been 
		 * retrieved yet, an {@link IllegalStateException} is thrown. 
		 * The size of the list is decremented after removal.
		 * 
		 * @throws IllegalStateException if no element has been retrieved yet
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			ListNode toRemove = lastRetrieved;
			toRemove.prev.next = toRemove.next;
			toRemove.next.prev = toRemove.prev;
			size--;
			lastRetrieved = null;
		}

		/**
		 * Sets the data of the last retrieved element. If no element has been retrieved 
		 * yet, an IllegalStateException is thrown. Also throws a 
		 * NullPointerException if the input element is null, and an 
		 * IllegalArgumentException if the element already exists in the list 
		 * (unless it is the same element).
		 * 
		 * @param e the element to set as the data of the last retrieved element
		 * @throws IllegalStateException if no element has been retrieved yet
		 * @throws NullPointerException if the provided element is null
		 * @throws IllegalArgumentException if the element already exists in the list
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			if (contains(e) && !lastRetrieved.data.equals(e)) {
				throw new IllegalArgumentException("Duplicate element.");
			}
			lastRetrieved.data = e;

		}

		/**
		 * Adds a new element to the list, ensuring that the element is not null 
		 * and does not already exist in the list. If the element is already in the 
		 * list, an IllegalArgumentException is thrown. After adding, the 
		 * new element becomes the previous element, and the indices are updated.
		 * 
		 * @param e the element to add to the list
		 * @throws NullPointerException if the provided element is null
		 * @throws IllegalArgumentException if the element already exists in the list
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			if (contains(e)) {
				throw new IllegalArgumentException("Duplicate element.");
			}
			ListNode newNode = new ListNode(e, previous, next);
			previous.next = newNode;
			next.prev = newNode;
			size++;
			previous = newNode;
			previousIndex++;
			nextIndex++;
			lastRetrieved = null;
		}
	}
}
