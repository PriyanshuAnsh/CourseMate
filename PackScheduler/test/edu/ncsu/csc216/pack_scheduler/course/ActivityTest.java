package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * A java Program to Test the Activity Class.
 * 
 * @author Priyanshu Dongre
 */
class ActivityTest {

	/**
	 * Test method for checking conflicts where no conflict exists.
	 */
	@Test
	public void testCheckConflictNoConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "sesmith5", 10, "TH", 1330,
				1445);

		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
	}

	/**
	 * Tests checkConflict() where there is a conflict on the same day and time.
	 */
	@Test
	public void testCheckConflictWithConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "sesmith5", 10, "M", 1330,
				1445);

		Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertEquals("Schedule conflict.", e1.getMessage());

		Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
		assertEquals("Schedule conflict.", e2.getMessage());
	}

	/**
	 * Tests checkConflict() when two activities have a time overlap.
	 */
	@Test
	public void testCheckConflictWithOverlap() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "sesmith5", 10, "W", 1400,
				1500);

		Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertEquals("Schedule conflict.", e1.getMessage());

		Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
		assertEquals("Schedule conflict.", e2.getMessage());
	}

	/**
	 * Tests checkConflict() when two activities are back-to-back but no conflict
	 * exists.
	 */
	@Test
	public void testCheckConflictBackToBack() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "sesmith5", 10, "MW", 1445,
				1600);

		assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	}

	/**
	 * Tests checkConflict() with activities arranged with no specific meeting
	 * times.
	 */
	@Test
	public void testCheckConflictArrangedCourses() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "A");
		Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "sesmith5", 10, "A");

		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
	}

	/**
	 * Tests checkConflict() when one activity starts immediately after another
	 * ends.
	 */
	@Test
	public void testCheckConflictWithBoundaryTime() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "sesmith5", 10, "MW", 1445,
				1600);

		assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	}

	/**
	 * Test the equals() method for equality of two different objects.
	 */
	@Test
	public void testEquals() {
		Object obj = new Object();
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);

		assertFalse(a1.equals(obj), "Equals should return false when comparing with a non-Activity object.");
	}

	/**
	 * Test equals() for two identical activities.
	 */
	@Test
	public void testEqualsSameActivity() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);

		assertTrue(a1.equals(a2), "Equals should return true when both activities are identical.");
	}

	/**
	 * Test equals() for two different activities.
	 */
	@Test
	public void testNotEqualsDifferentActivity() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC217", "Software Development Fundamentals Lab", "002", 1, "sesmith5", 10, "MW", 1330,
				1445);

		assertFalse(a1.equals(a2), "Equals should return false when activities have different details.");
	}

	/**
	 * Test hashCode() for consistent results.
	 */
	@Test
	public void testHashCodeConsistency() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);

		assertEquals(a1.hashCode(), a2.hashCode(), "hashCode should be consistent for identical objects.");
	}
}
