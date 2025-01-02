package edu.ncsu.csc217.collections.list;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Tests the methods provided in the SortedList class
 * @author Priyanshu Dongre, Suyash Patel
 */
public class SortedListTest {

	/** Array of fruit-related Strings to add to SortedList */
    private String[] fruitList = {"apple", "pear", "banana", "cucumber", "grape", "blueberry", "melon", "kiwi", "pineapple", "tomato", "manzano banana"};
    /** Length of fruitList */
    private int fruitListLength = fruitList.length;
    /** Sorted array of fruit-related Strings */
    private String[] sortedFruitList = {"apple", "banana", "blueberry", "cucumber", "grape", "kiwi", "manzano banana", "melon", "pear", "pineapple", "tomato"};
	
    /**
	 * Tests that sorted list grows and sorts properly.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
        assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		int i = 0;
		while (i < fruitListLength) {
	        list.add(fruitList[i]);
	        i++;
	    }
        assertEquals(11, list.size());
        for (int j = 0; j < sortedFruitList.length; j++) {
            assertEquals(sortedFruitList[j], list.get(j));
        }
	}

	/**
	 * Tests add method with individual elements, including null and duplicate elements.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
        list.add("apple");
        assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
        list.add("clementine");
        assertEquals(3, list.size());
		assertEquals("clementine", list.get(2));
        list.add("blueberry");
        assertEquals(4, list.size());
		assertEquals("blueberry", list.get(2));
        assertThrows(NullPointerException.class, () -> list.add(null), "Should throw NullPointerException for null parameter");
        assertThrows(IllegalArgumentException.class, () -> list.add("banana"), "Should throw IllegalArgumentException for duplicate parameter");
	}
	
    /**
     * Tests get method with empty list, negative index, and element added at size.
     */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0), "Should throw IndexOutOfBoundsException for empty list");
        int i = 0;
        while (i < fruitListLength) {
            list.add(fruitList[i]);
            i++;
        }
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1), "Should throw IndexOutOfBoundsException for -1 index");
        int oldSize = list.size();
        list.add("zucchini");
        assertEquals("zucchini", list.get(oldSize));
		
	}
	
	/**
	 * Tests removing a Student from the list
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		//Test removing from an empty list
		assertThrows(Exception.class, () -> {
			list.remove(3);
		});
		
		//Add some elements to the list - at least 4
		list.add("apple");
		list.add("banana");
		list.add("guava");
		list.add("melon");
		list.add("peach");
		
		//Test removing an element at an index < 0
		assertThrows(Exception.class, () -> {
			list.remove(-1);
		});
		
		//Test removing an element at size
		assertThrows(Exception.class, () -> {
			list.remove(list.size());
		});
		
		//Test removing a middle element
		assertEquals("guava", list.remove(2));
		
		//Test removing the last element
		assertEquals("peach", list.remove(3));
		
		//Test removing the first element
		assertEquals("apple", list.remove(0));
		
		//Test removing the last element
		assertEquals("melon", list.remove(1));
	}
	
	/**
	 * Tests finding the index of a certain Student in the list
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		//Test indexOf on an empty list
		assertEquals(-1, list.indexOf("apple"));
		
		//Add some elements
		list.add("apple");
		list.add("banana");
		list.add("melon");
		list.add("peach");
		
		//Test various calls to indexOf for elements in the list
		//and not in the list
		assertEquals(0, list.indexOf("apple"));
		assertEquals(3, list.indexOf("peach"));
		assertEquals(2, list.indexOf("melon"));
		assertEquals(-1, list.indexOf("watermelon"));
		
		//Test checking the index of null
		assertThrows(NullPointerException.class, () -> {
			list.indexOf(null);
		});
		
	}
	
	/**
	 * Tests clearing the list of Students
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		//Add some elements
		list.add("apple");
		list.add("banana");
		list.add("peach");
		list.add("melon");
		
		//Clear the list
		list.clear();
		
		//Test that the list is empty
		assertTrue(list.isEmpty());
		
	}

	/**
	 * Tests finding if the list is empty or not
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		//Test that the list starts empty
		assertTrue(list.isEmpty());
		
		//Add at least one element
		list.add("apple");
		
		//Check that the list is no longer empty
		assertFalse(list.isEmpty());
		
	}

	/**
	 * Tests if the list contains a certain Student after adding it
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		//Test the empty list case
		assertFalse(list.contains("apple"));
		
		//Add some elements
		list.add("apple");
		list.add("banana");
		list.add("peach");
		list.add("melon");
		
		//Test some true and false cases
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("peach"));
		assertTrue(list.contains("melon"));
		assertFalse(list.contains("blueberry"));
		
	}
	
	/**
	 * test if lists correctly state that they are equal to each other
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		//Make two lists the same and one list different
		list1.add("apple");
		list1.add("banana");
		list1.add("melon");
		list2 = list1;
		list3.add("raspberry");
		list3.add("blueberry");
		list3.add("cherry");
		
		//Test for equality and non-equality
		assertEquals("Testing Equal Lists", list1, list2);
		
		assertNotEquals("Testing Unequal Lists", list1, list3);
		
	}
	
	/**
	 * tests that the hashCode for the lists is generated properly
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		// Make list1 and list2 the same, and make list3 different.
		int i = 0;
		while (i < fruitListLength) {
	        list1.add(fruitList[i]);
	        list2.add(fruitList[i]);
	        list3.add(fruitList[i]);
	        i++;
	    }
		list3.remove(1);
		list3.add("papaya");		
		
		// Test for the same and different hashCodes.
		assertEquals( "Testing Equal Hashcode", list1.hashCode(), list2.hashCode());
		
		assertNotEquals("Testing Unequal Hashcode", list1.hashCode(), list3.hashCode());
	}

}
 