package edu.ncsu.csc216.pack_scheduler.util;

/**
 * A recursive implementation of a singly linked list.
 * Provides methods to add, remove, get, and set elements in the list.
 * 
 * @param <E> The type of elements stored in this linked list.
 * @author Priyanshu Dongre
 */
public class LinkedListRecursive<E> {
	 /** The size of the linked list */
    private int size;
    /** The front node of the linked list */
    private ListNode front;

    /**
     * Constructs an empty linked list.
     */
	
	public LinkedListRecursive() {
		size = 0;
		front = null;
	}
	
	 /**
     * Checks if the linked list is empty.
     * 
     * @return true if the linked list is empty, false otherwise
     */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
     * Returns the size of the linked list.
     * 
     * @return the size of the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Retrieves the element at the specified index.
     * 
     * @param idx the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
	
	public E get(int idx) {
		if(front == null) {
			throw new IllegalArgumentException();
		}
		
		if(idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if(idx == 0) {
			return front.data;
		}
		return front.get(idx);
	}
	
	/**
     * Removes the element at the specified index.
     * 
     * @param idx the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
	public E remove(int idx) {
		
		if(front == null) {
			throw new IndexOutOfBoundsException();
		}
		if(idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(idx == 0) {
			E removedElement = front.data;
			front = front.next;
			size--;
			return removedElement;
		}
		return front.remove(idx);
	}
	
	/**
     * Replaces the element at the specified index with a new element.
     * 
     * @param idx the index of the element to replace
     * @param element the new element
     * @return the previous element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws NullPointerException if the element is null
     * @throws IllegalArgumentException if the element already exists in the list
     */
	public E set(int idx, E element) {
		
		if(front == null) {
			throw new IndexOutOfBoundsException();
		}
		if(element == null) {
			throw new NullPointerException();
		}
		if(front.contains(element)) {
			throw new IllegalArgumentException();
		}
		if(idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if(idx == 0) {
			E previousData = front.data;
			front.data = element;
			return previousData;
		}
		return front.set(idx, element);
	}
	
	/**
     * Removes the first occurrence of the specified element from the list.
     * 
     * @param element the element to remove
     * @return true if the element was removed, false otherwise
     */
	public boolean remove(E element) {
		if(front == null) {
			return false;
		}
		
		if(front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		}
		
		return front.remove(element);
	}
	
	/**
     * Checks if the list contains the specified element.
     * 
     * @param element the element to check
     * @return true if the element is found, false otherwise
     * @throws NullPointerException if the element is null
     */
	public boolean contains(E element) {
		if(front == null) { 
			return false;
		}
		if(element == null) {
			throw new NullPointerException();
		}
		return front.contains(element);
	}
	
   /**
    * Adds an element to the end of the list.
    * 
    * @param element the element to add
    * @return true if the element was added successfully
    * @throws NullPointerException if the element is null
    * @throws IllegalArgumentException if the element already exists in the list
    */
	public boolean add(E element) {
		
		if(element == null) {
			throw new NullPointerException();
		}
		
		if(front == null) {
			front = new ListNode(element, null);
			size++;
			return true;
		}
		if(front.contains(element)) {
			throw new IllegalArgumentException();
		}
		front.add(size, element);
		return true;	
	}
	
	/**
     * Adds an element at the specified index.
     * 
     * @param idx the index to insert the element
     * @param element the element to insert
     * @throws NullPointerException if the element is null
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException if the element already exists in the list
     */
	public void add(int idx, E element) {
		
		if(element == null) {
			throw new NullPointerException();
		}
		if(idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if(front == null) {
			front = new ListNode(element, null);
			size++;
			return;
		} 
		
		if(idx == 0 && !front.contains(element)) {
			ListNode newFront = new ListNode(element, front);
			front = newFront;
			size++;
			return;
		}
		if(front.contains(element)) {
			throw new IllegalArgumentException();
		}
		
		front.add(idx, element);
	}
	
	
	/**
     * Inner class representing a node in the linked list.
     */
	private class ListNode {
		 /** The data stored in the node */
        private E data;
        /** The next node in the linked list */
        private ListNode next;

        /**
         * Constructs a ListNode with the specified data and next node.
         * 
         * @param data the data for the node
         * @param next the next node in the list
         */
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Adds a new element at the specified index.
         * 
         * @param idx the index to insert the element
         * @param element the element to insert
         */
		public void add(int idx, E element) {
			if(idx == 1) {
				ListNode newNode = new ListNode(element, next);
				this.next = newNode;
				size++;
				return;
			}
			next.add(idx - 1, element);
		}
		
		/**
         * Retrieves the element at the specified index.
         * 
         * @param idx the index of the element
         * @return the element at the specified index
         */
		public E get(int idx) {
			
			if(idx == 0) {
				return data;
			}
			return next.get(idx - 1);
		}
		
		/**
         * Removes the element at the specified index.
         * 
         * @param idx the index of the element to remove
         * @return the removed element
         */
		public E remove(int idx) {
			if(idx == 1) {
				E removedElement = next.data;
				next = next.next;
				size--;
				return removedElement;
			}
			return next.remove(idx - 1);
		}
		
		/**
         * Removes the first occurrence of the specified element.
         * 
         * @param element the element to remove
         * @return true if the element was removed, false otherwise
         */
		public boolean remove(E element) {
			if(this.next == null) {
				return false;
			}
			if(this.next.data.equals(element)) {
				this.next = this.next.next;
				size--;
				return true;
			}
			return next.remove(element);
		}
		
		/**
         * Replaces the element at the specified index with a new element.
         * 
         * @param idx the index of the element to replace
         * @param element the new element
         * @return the previous element at the index
         */
		public E set(int idx, E element) {
			if(idx == 1) {
				E currentElement = next.data;
				next.data = element;
				return currentElement;
			}
			return next.set(idx - 1, element);
			
		}
		
		/**
         * Checks if the linked list contains the specified element.
         * 
         * @param element the element to check
         * @return true if the element is found, false otherwise
         */
		
		public boolean contains(E element) {
			if(this.data.equals(element)) {
				return true;
			}
			if(next == null) {
				return false;
			}
			
			return next.contains(element);
		}
	}
}
