
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the LinkedListRecursive class.
 * This class provides unit tests for the various methods of LinkedListRecursive.
 * Tests include adding, removing, setting, retrieving, and checking for containment of elements.
 * 
 * @author Priyanshu Dongre
 */
public class LinkedListRecursiveTest {

    /** Instance of LinkedListRecursive used for testing */
    LinkedListRecursive<String> list;

    /**
     * Sets up the test environment before each test case.
     * Initializes an empty LinkedListRecursive instance.
     */
    @BeforeEach
    public void setUp() {
        list = new LinkedListRecursive<>();
    }

    /**
     * Tests the add method of LinkedListRecursive.
     * Verifies addition of elements to the list, both at the end and at a specific index.
     * Ensures exceptions are thrown for invalid operations.
     */
    @Test
    public void testAdd() {
        assertEquals(0, list.size());
        assertTrue(list.add("Hello"));
        assertEquals(1, list.size());
        assertEquals("Hello", list.get(0));
        assertEquals("Hello", list.remove(0));
        assertEquals(0, list.size());
        for (int i = 0; i < 5; i++) {
            assertTrue(list.add(i + " added to list"));
        }

        assertEquals(5, list.size());
        list.add(2, "Added at index 2");
        assertEquals("Added at index 2", list.get(2));
        assertEquals(6, list.size());

        assertThrows(IllegalArgumentException.class, () -> list.add(2, "Added at index 2"));
        assertThrows(IllegalArgumentException.class, () -> list.add("Added at index 2"));

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(10, "Something"));
    }

    /**
     * Tests the contains method of LinkedListRecursive.
     * Verifies whether the list correctly identifies the presence or absence of elements.
     */
    @Test
    public void testContain() {
        assertFalse(list.contains("Hello"));
        assertTrue(list.isEmpty());
        assertTrue(list.add("Hello"));
        assertEquals(1, list.size());
        assertTrue(list.contains("Hello"));
        assertThrows(NullPointerException.class, () -> list.contains(null));
    }

    /**
     * Tests the remove method of LinkedListRecursive.
     * Validates the removal of elements by index and by value.
     * Ensures that invalid operations do not alter the list.
     */
    @Test
    public void testRemove() {
        assertFalse(list.remove("something"));
        for (int i = 0; i < 5; i++) {
            assertTrue(list.add(i + " added to list"));
        }

        assertEquals(5, list.size());
        assertEquals("0 added to list", list.remove(0));
        assertEquals(4, list.size());
        assertEquals("3 added to list", list.remove(2));

        assertEquals(3, list.size());

        assertTrue(list.remove("4 added to list"));
        assertEquals(2, list.size());
        assertTrue(list.remove("1 added to list"));
        assertFalse(list.remove("10 added to list"));
    }

    /**
     * Tests the set method of LinkedListRecursive.
     * Verifies the replacement of elements at specific indices.
     * Ensures that invalid operations throw appropriate exceptions.
     */
    @Test
    public void testSet() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "Empty"));

        for (int i = 0; i < 5; i++) {
            assertTrue(list.add(i + " added to list"));
        }
        assertThrows(NullPointerException.class, () -> list.set(1, null));
        assertEquals("0 added to list", list.set(0, "I removed 0 and added 00"));
        assertEquals(5, list.size());

        assertEquals("3 added to list", list.set(3, "I removed 3 and added *3*"));
        assertEquals(5, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(10, "Something"));
    }

    /**
     * Tests the get method of LinkedListRecursive.
     * Verifies that elements can be retrieved correctly by index.
     */
    @Test
    public void testGet() {
        for (int i = 0; i < 10; i++) {
            assertTrue(list.add("" + i));
        }

        for (int i = 0; i < 10; i++) {
            assertEquals("" + i, list.get(i));
        }
    }
}