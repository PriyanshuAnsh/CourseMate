package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ArrayList implementation.
 * This class contains unit tests for various methods of the ArrayList class,
 * including add, remove, set, and other operations.
 * @author Priyanshu Dongre
 */

public class ArrayListTest {

	/** ArrayList instance used for testing. */
	private ArrayList<String> list;

	/**
     * Set up method to initialize the list before each test.
     */
	@Before
	public void setUp() {
		list = new ArrayList<>();
	}

	
	/**
     * Test the ArrayList constructor.
     */
	@Test
	public void testConstructor() {
		assertEquals(0, list.size());
	}

	/**
     * Test the add method of ArrayList.
     */
	@Test
	public void testAdd() {
		try {
			list.add(0, "First");
			assertEquals(1, list.size());
			assertEquals("First", list.get(0));

			try {
				list.add(2, "Something");
				fail("Failed to throw exception");
			} catch (IndexOutOfBoundsException iobe) {
				// successful
			}

			list.add(1, "Second");
			assertEquals(2, list.size());
			assertEquals("Second", list.get(1));

			list.add(1, "Middle");
			assertEquals(3, list.size());
			assertEquals("Middle", list.get(1));
			assertEquals("Second", list.get(2));

		} catch (Exception e) {
			fail("Exception thrown during add test: " + e.getMessage());
		}
	}
	
	/**
     * Test removing an element by index from an empty list.
     */
	@Test
	public void testRemoveInt() {
		ArrayList<Integer> newList = new ArrayList<>();
		assertEquals(0, newList.size());
		try {
			newList.remove(4);
		} catch(IndexOutOfBoundsException iobe) {
			//Successful.
		}
		
	}
	
	
	/**
     * Test removing elements from a large list.
     */
	@Test
	public void testRemoveElementFromLargeList() {
		for(int i = 1; i < 100; i++) {
			list.add("" + i);
		}
		
		assertEquals(99, list.size());
		assertEquals("" + 75, list.remove(74));
		assertEquals(98, list.size());
		
		try {
			list.remove(100);
			fail("Excepted IndexOutOfBoundsException");
		} catch(IndexOutOfBoundsException iobe) {
			//successful
		}
		assertEquals("" + 99, list.remove(97));
		assertEquals("" + 1, list.remove(0));
	}

	
	/**
     * Test adding a null element to the list.
     */
	@Test
	public void testAddNull() {
		try {
			list.add(0, null);
			fail("Expected NullPointerException not thrown");
		} catch (NullPointerException e) {
			// Expected exception
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	
	/**
     * Test adding a duplicate element to the list.
     */
	@Test
	public void testAddDuplicate() {
		try {
			list.add(0, "First");
			list.add(1, "First"); // Duplicate
			fail("Expected IllegalArgumentException not thrown");
		} catch (IllegalArgumentException e) {
			// Expected exception
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	/**
     * Test adding an element at an out-of-bounds index.
     */
	@Test
	public void testAddOutOfBounds() {
		try {
			list.add(1, "First"); // Out of bounds
			fail("Expected IndexOutOfBoundsException not thrown");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	
	 /**
     * Test the remove method of ArrayList.
     */
	@Test
	public void testRemove() {
		try {
			list.add(0, "First");
			list.add(1, "Second");
			assertEquals("First", list.remove(0));
			assertEquals(1, list.size());
			assertEquals("Second", list.get(0));
		} catch (Exception e) {
			fail("Exception thrown during remove test: " + e.getMessage());
		}
	}

	
	/**
     * Test removing an element at an out-of-bounds index.
     */
	@Test
	public void testRemoveOutOfBounds() {
		try {
			list.remove(3); // Out of bounds
			fail("Expected IndexOutOfBoundsException not thrown");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	
	/**
     * Test the set method of ArrayList.
     */
	@Test
	public void testSet() {
		try {
			list.add(0, "First");
			list.add(1, "Second");
			assertEquals("First", list.set(0, "NewFirst"));
			assertEquals("NewFirst", list.get(0));
		} catch (Exception e) {
			fail("Exception thrown during set test: " + e.getMessage());
		}
	}

	
	/**
     * Test setting a null element in the list.
     */
	@Test
	public void testSetNull() {
		try {
			list.add(0, "First");
			list.set(0, null);
			fail("Expected NullPointerException not thrown");
		} catch (NullPointerException e) {
			// Expected exception
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	
	/**
     * Test setting a duplicate element in the list.
     */
	@Test
	public void testSetDuplicate() {
		try {
			list.add(0, "First");
			list.add(1, "Second");
			list.set(1, "First"); // Duplicate
			fail("Expected IllegalArgumentException not thrown");
		} catch (IllegalArgumentException e) {
			// Expected exception
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	
	/**
     * Test setting an element at an out-of-bounds index.
     */
	@Test
	public void testSetOutOfBounds() {
		try {
			list.set(0, "First"); // Out of bounds
			fail("Expected IndexOutOfBoundsException not thrown");
		} catch (IndexOutOfBoundsException e) {
			// Expected exception
		}

	}
	
	/**
     * Test the private growArray method indirectly.
     */
	@Test
    public void testGrowArray() {
        for (int i = 0; i < 10; i++) {
            list.add(i, "Element " + i);
        }
        assertEquals(10, list.size(), "Size should be 10 after adding 10 elements.");

        list.add(10, "Element 10");

        assertEquals(11, list.size(), "Size should be 11 after adding the 11th element.");
        assertEquals("Element 10", list.get(10), "The 11th element should be 'Element 10'.");

        for (int i = 0; i < 10; i++) {
            assertEquals("Element " + i, list.get(i), "Element at index " + i + " should be 'Element " + i + "'.");
        }
    }
}