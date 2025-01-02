package edu.ncsu.csc216.pack_scheduler.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ListIterator;

/**
 * Test class for the custom LinkedList implementation.
 * This class performs unit tests to verify the behavior of the LinkedList class and its methods,
 * including add, remove, set, contains, size, and ListIterator functionalities.
 * 
 * The tests include scenarios for valid and invalid inputs, boundary conditions, and edge cases.
 * It uses the JUnit testing framework to assert expected outcomes.
 * 
 */
public class LinkedListTest {

    /** The LinkedList instance used for testing */
    private LinkedList<Integer> list;

    /**
     * Sets up the test fixture.
     * Initializes an empty LinkedList object before each test.
     */
    @BeforeEach
    public void setUp() {
        list = new LinkedList<>();
    }

    /**
     * Tests the constructor and initial state of the list.
     * Verifies that the list is empty upon creation.
     */
    @Test
    public void testConstructor() {
        assertEquals(0, list.size());
        assertFalse(list.contains(1));
    }

    /**
     * Tests adding an element at the beginning of the list.
     * Verifies that the element is added correctly.
     */
    @Test
    public void testAddFirst() {
        list.add(0, 10);
        assertEquals(1, list.size());
        assertTrue(list.contains(10));
    }

    /**
     * Tests adding an element at the end of the list.
     * Verifies that the element is added correctly.
     */
    @Test
    public void testAddEnd() {
        list.add(0, 10);
        list.add(1, 20);
        assertEquals(2, list.size());
        assertTrue(list.contains(20));
    }

    /**
     * Tests adding an element in the middle of the list.
     * Verifies that the element is inserted at the correct index.
     */
    @Test
    public void testAddMiddle() {
        list.add(0, 10);
        list.add(1, 20);
        list.add(1, 15);
        assertEquals(3, list.size());
        assertEquals(15, list.get(1));
    }

    /**
     * Tests adding a duplicate element to the list.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    public void testAddDuplicate() {
        list.add(0, 10);
        assertThrows(IllegalArgumentException.class, () -> list.add(0, 10));
    }

    /**
     * Tests setting a new value in the list without duplicates.
     * Verifies that the element is updated correctly.
     */
    @Test
    public void testSet() {
        list.add(0, 10);
        list.set(0, 20);
        assertEquals(20, list.get(0));
    }

    /**
     * Tests setting a duplicate value in the list.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    public void testSetDuplicate() {
        list.add(0, 10);
        list.add(1, 20);
        assertThrows(IllegalArgumentException.class, () -> list.set(0, 20));
    }

    /**
     * Tests retrieving an element at a valid index.
     */
    @Test
    public void testGet() {
        list.add(0, 10);
        list.add(1, 20);
        assertEquals(20, list.get(1));
    }

    /**
     * Tests retrieving an element out of bounds.
     * Verifies that an IndexOutOfBoundsException is thrown.
     */
    @Test
    public void testGetOutOfBounds() {
        list.add(0, 10);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    /**
     * Tests the size method of the list.
     */
    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add(0, 10);
        assertEquals(1, list.size());
        list.add(1, 20);
        assertEquals(2, list.size());
    }

    /**
     * Tests the listIterator() method with a valid index.
     */
    @Test
    public void testListIteratorValidIndex() {
        list.add(0, 10);
        list.add(1, 20);
        ListIterator<Integer> iterator = list.listIterator(1);
        assertTrue(iterator.hasNext());
        assertEquals(20, iterator.next());
    }

    /**
     * Tests the listIterator() method with an out-of-bounds index.
     */
    @Test
    public void testListIteratorOutOfBounds() {
        list.add(0, 10);
        assertThrows(IndexOutOfBoundsException.class, () -> list.listIterator(2));
    }

    /**
     * Tests the next() and hasNext() methods of ListIterator.
     */
    @Test
    public void testNext() {
        list.add(0, 10);
        list.add(1, 20);
        ListIterator<Integer> iterator = list.listIterator(0);
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertEquals(20, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the previous() and hasPrevious() methods of ListIterator.
     */
    @Test
    public void testPrevious() {
        list.add(0, 10);
        list.add(1, 20);
        ListIterator<Integer> iterator = list.listIterator(2);
        assertEquals(20, iterator.previous());
        assertEquals(10, iterator.previous());
        assertFalse(iterator.hasPrevious());
    }

    /**
     * Tests the remove() method of ListIterator.
     */
    @Test
    public void testRemove() {
        list.add(0, 10);
        list.add(1, 20);
        ListIterator<Integer> iterator = list.listIterator(0);
        iterator.next();
        iterator.remove();
        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    /**
     * Tests the set() method of ListIterator.
     */
    @Test
    public void testSetIterator() {
        list.add(0, 10);
        list.add(1, 20);
        ListIterator<Integer> iterator = list.listIterator(0);
        iterator.next();
        iterator.set(15);
        assertEquals(15, list.get(0));
    }

    /**
     * Tests the add() method of ListIterator.
     */
    @Test
    public void testAddIterator() {
        list.add(0, 10);
        list.add(1, 20);
        ListIterator<Integer> iterator = list.listIterator(1);
        iterator.add(25);
        assertEquals(3, list.size());
        assertEquals(25, list.get(1));
    }

    /**
     * Tests removing an element with an invalid state in ListIterator.
     */
    @Test
    public void testIteratorRemoveInvalidState() {
        ListIterator<Integer> iterator = list.listIterator(0);
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    /**
     * Tests the contains() method of the list.
     */
    @Test
    public void testContains() {
        list.add(0, 10);
        assertTrue(list.contains(10));
        assertFalse(list.contains(20));
    }

    /**
     * Tests the iterator on an empty list.
     */
    @Test
    public void testIteratorEmptyList() {
        ListIterator<Integer> iterator = list.listIterator(0);
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
    }
}
