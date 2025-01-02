package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the LinkedAbstractList class
 * 
 * @author Suyash Patel
 */
public class LinkedAbstractListTest {
	/** the list used for testing */
	private LinkedAbstractList<String> list;

	/**
	 * sets up the test fixture before each test method
	 */
	@BeforeEach
	public void setUp() {
		list = new LinkedAbstractList<>(5);
	}

	/**
	 * tests the constructor for setting capacity
	 */
	@Test
	public void testConstructor() {
		// Valid case
		assertDoesNotThrow(() -> new LinkedAbstractList<String>(5));

		// Invalid case: capacity less than 0
		assertThrows(IllegalArgumentException.class, () -> new LinkedAbstractList<String>(-1));
	}

	/**
	 * Tests that adding an element exceeds capacity throws an exception.
	 */
	@Test
	public void testAddExceedingCapacity() {
		// Arrange
		LinkedAbstractList<String> list1 = new LinkedAbstractList<>(1);
		list1.add(0, "Test Data");

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			list1.add(1, "Another Test Data");
		});
	}

	/**
	 * tests setting the capacity
	 */
	@Test
	public void testSetCapacity() {
		list.add(0, "A");
		list.add(1, "B");

		// Valid case
		assertDoesNotThrow(() -> list.setCapacity(3));

		// Invalid case: setting capacity lower than the current size
		assertThrows(IllegalArgumentException.class, () -> list.setCapacity(1));
	}

	/**
	 * tests the get method
	 */
	@Test
	public void testGet() {
		list.add(0, "A");
		list.add(1, "B");

		// Valid case
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));

		// Invalid case: index out of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
	}

	/**
	 * tests the set method
	 */
	@Test
	public void testSet() {
		list.add(0, "A");
		list.add(1, "B");

		// Valid case
		assertEquals("A", list.set(0, "C"));
		assertEquals("C", list.get(0));

		// Invalid case: setting a null element
		assertThrows(NullPointerException.class, () -> list.set(0, null));

		// Invalid case: setting a duplicate element
		assertThrows(IllegalArgumentException.class, () -> list.set(0, "B"));

		// Invalid case: index out of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "D"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(2, "D"));
	}

	/**
	 * tests the add method
	 */
	@Test
	public void testAdd() {
		// Valid cases
		list.add(0, "A");
		list.add(1, "B");
		list.add(2, "C");
		
		

		assertEquals(3, list.size());
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		
		list.add(2, "P");
		assertEquals(4, list.size());
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("P", list.get(2));
		assertEquals("C", list.get(3));

		// Invalid case: adding null element
		assertThrows(NullPointerException.class, () -> list.add(0, null));

		// Invalid case: adding duplicate element
		assertThrows(IllegalArgumentException.class, () -> list.add(3, "C"));

		// Invalid case: capacity exceeded
		list.add(4, "D");
		assertThrows(IllegalArgumentException.class, () -> list.add(5, "F"));

		// Invalid case: index out of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "Z"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(10, "Z"));
	}

	/**
	 * tests the remove method
	 */
	@Test
	public void testRemove() {
		list.add(0, "A");
		list.add(1, "B");
		list.add(2, "C");

		// Valid case: removing an element
		assertEquals("B", list.remove(1));
		assertEquals(2, list.size());

		// Invalid case: index out of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
	}

	/**
	 * tests the size method
	 */
	@Test
	public void testSize() {
		assertEquals(0, list.size());

		list.add(0, "A");
		list.add(1, "B");

		assertEquals(2, list.size());
	}
    
	/**
	 * Tests the ListNode constructor for data only.
	 */
	@Test
	public void testListNodeConstructor() {
	
		LinkedAbstractList<String> list2 = new LinkedAbstractList<>(10);
		String testData = "Test Data";
		list2.add(testData);
		String retrievedData = list2.get(0);
		assertNotNull(retrievedData);
        assertEquals(testData, retrievedData);
		assertEquals(testData, list2.get(0));
	}
}
