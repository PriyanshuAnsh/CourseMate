package edu.ncsu.csc216.pack_scheduler.user;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Faculty class.
 * This class performs unit tests on the Faculty class, verifying the behavior of its constructor,
 * accessor methods, mutator methods, equals, hashCode, and toString methods.
 * 
 * It includes tests for valid and invalid input scenarios, ensuring that the Faculty class adheres
 * to its specified behavior.
 * 
 * @author Suyash Patel
 */
public class FacultyTest {

    /** Faculty instance used for testing */
    private Faculty faculty;

    /**
     * Sets up the test fixture.
     * Initializes a Faculty object before each test.
     */
    @BeforeEach
    public void setUp() {
        faculty = new Faculty("James", "Bond", "12345", "jbond@gmail.com", "password", 2);
    }

    /**
     * Tests the Faculty constructor with valid input.
     * Verifies that a Faculty object is created with the correct maximum courses.
     */
    @Test
    public void testConstructorValid() {
        Faculty validFaculty = new Faculty("James", "Bond", "12345", "jbond@gmail.com", "password", 3);
        assertEquals(3, validFaculty.getMaxCourses());
    }

    /**
     * Tests the Faculty constructor with an invalid maximum number of courses that is too low.
     * Verifies that an IllegalArgumentException is thrown when the maxCourses is less than 1.
     */
    @Test
    public void testConstructorInvalidMaxCoursesTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Faculty("James", "Bond", "12345", "jbond@gmail.com", "password", 0);
        });
    }

    /**
     * Tests the Faculty constructor with an invalid maximum number of courses that is too high.
     * Verifies that an IllegalArgumentException is thrown when the maxCourses is greater than 3.
     */
    @Test
    public void testConstructorInvalidMaxCoursesTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Faculty("James", "Bond", "12345", "jbond@gmail.com", "password", 4);
        });
    }

    /**
     * Tests setting a valid maximum number of courses for a Faculty object.
     * Verifies that the maximum number of courses is set correctly.
     */
    @Test
    public void testSetMaxCoursesValid() {
        faculty.setMaxCourses(1);
        assertEquals(1, faculty.getMaxCourses());
    }

    /**
     * Tests setting an invalid maximum number of courses that is too low.
     * Verifies that an IllegalArgumentException is thrown when setting maxCourses less than 1.
     */
    @Test
    public void testSetMaxCoursesTooLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            faculty.setMaxCourses(0);
        });
    }

    /**
     * Tests setting an invalid maximum number of courses that is too high.
     * Verifies that an IllegalArgumentException is thrown when setting maxCourses greater than 3.
     */
    @Test
    public void testSetMaxCoursesTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            faculty.setMaxCourses(4);
        });
    }

    /**
     * Tests the hashCode method of the Faculty class.
     * Verifies that two Faculty objects with the same attributes have the same hash code.
     */
    @Test
    public void testHashCode() {
        Faculty otherFaculty = new Faculty("James", "Bond", "12345", "jbond@gmail.com", "password", 2);
        assertEquals(faculty.hashCode(), otherFaculty.hashCode());
    }

    /**
     * Tests the equals method when comparing the same Faculty object.
     * Verifies that the equals method returns true when comparing the object to itself.
     */
    @Test
    public void testEqualsSameObject() {
        assertEquals(true, faculty.equals(faculty));
    }

    /**
     * Tests the equals method with a different Faculty object.
     * Verifies that the equals method returns false when comparing two Faculty objects
     * with different attributes.
     */
    @Test
    public void testEqualsDifferentObject() {
        Faculty differentFaculty = new Faculty("James", "Bond", "12345", "jbond@gmail.com", "password", 3);
        assertNotEquals(true, faculty.equals(differentFaculty));
    }

    /**
     * Tests the equals method when comparing a Faculty object to null.
     * Verifies that the equals method returns false when the Faculty object is null.
     */
    @Test
    public void testEqualsNull() {
        faculty = null;
        assertNull(faculty);
    }

    /**
     * Tests the toString method of the Faculty class.
     * Verifies that the string representation of the Faculty object is in the expected format.
     */
    @Test
    public void testToString() {
        String expected = "James,Bond,12345,jbond@gmail.com,password,2";
        assertEquals(expected, faculty.toString());
    }
}

