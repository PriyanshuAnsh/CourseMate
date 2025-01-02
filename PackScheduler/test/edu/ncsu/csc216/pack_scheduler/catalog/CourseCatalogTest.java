package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the CourseCatalog class.
 * 
 * @author Priyanshu
 */
public class CourseCatalogTest {

    /** Valid course records file */
    private final String validTestFile = "test-files/course_records.txt";

    /** CourseCatalog object used in tests */
    private CourseCatalog catalog;

    /**
     * Set up for the tests.
     * Creates a new CourseCatalog object before each test.
     */
    @Before
    public void setUp() {
        catalog = new CourseCatalog();
    }

    /**
     * Tests the constructor.
     * Ensures the catalog is empty initially.
     */
    @Test
    public void testCourseCatalogConstructor() {
        assertEquals(0, catalog.getCourseCatalog().length);
    }

    /**
     * Tests newCourseCatalog method.
     * Ensures the catalog is empty after reset.
     */
    @Test
    public void testNewCourseCatalog() {
        catalog.newCourseCatalog();
        assertEquals(0, catalog.getCourseCatalog().length);
    }

    /**
     * Tests loading courses from a file.
     * Ensures valid courses are added, and duplicate or invalid ones are ignored.
     */
    @Test
    public void testLoadCoursesFromFile() {
        catalog.loadCoursesFromFile(validTestFile);
        String[][] courseCatalog = catalog.getCourseCatalog();
        
        // Ensure that 13 courses are loaded from the test file.
        assertEquals(13, courseCatalog.length);
    }

    /**
     * Tests loading courses from a non-existing file.
     * Expects IllegalArgumentException.
     */
    @Test
    public void testLoadCoursesFromInvalidFile() {
    	try {
    		catalog.loadCoursesFromFile("non_existent_file.txt");
    		fail();
    	} catch(IllegalArgumentException ie) {
    		// As long as it doesn't fail its working!
    	}
        
    }

    /**
     * Tests addCourseToCatalog method.
     * Ensures that valid courses are added and duplicates are ignored.
     */
    @Test
    public void testAddCourseToCatalog() {
        // Add a course to the catalog
        assertTrue(catalog.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "jtimberlake", 10, "MW", 1330, 1445));
        
        // Add another course to the catalog
        assertTrue(catalog.addCourseToCatalog("CSC226", "Discrete Math", "001", 3, "psdongre", 11, "TH", 1330, 1445));

        // Try adding a duplicate course
        assertFalse(catalog.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "rnmullen", 12, "MW", 1330, 1445));
    }

    /**
     * Tests removeCourseFromCatalog method.
     * Ensures that the course is removed successfully if it exists.
     */
    @Test
    public void testRemoveCourseFromCatalog() {
        // Add a course to remove
        catalog.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "jtimberlake", 10, "MW", 1330, 1445);
        assertEquals(1, catalog.getCourseCatalog().length);
        
        // Remove the course
        assertTrue(catalog.removeCourseFromCatalog("CSC216", "001"));
        assertEquals(0, catalog.getCourseCatalog().length);

        // Try removing a non-existing course
        assertFalse(catalog.removeCourseFromCatalog("CSC226", "001"));
    }

    /**
     * Tests getCourseFromCatalog method.
     * Ensures that the correct course is returned for valid name and section.
     */
    @Test
    public void testGetCourseFromCatalog() {
        // Add a course to the catalog
        catalog.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "rnmullen", 10, "MW", 1330, 1445);
        
        // Retrieve the course
        Course c = catalog.getCourseFromCatalog("CSC216", "001");
        assertNotNull(c);
        assertEquals("CSC216", c.getName());
        assertEquals("001", c.getSection());

        // Try retrieving a non-existing course
        assertNull(catalog.getCourseFromCatalog("CSC 226", "001"));
    }

    /**
     * Tests saveCourseCatalog method.
     * Ensures that courses are saved to a file correctly.
     */
    @Test
    public void testSaveCourseCatalog() {
        // Add courses to the catalog
        catalog.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "jtimberlake", 10, "MW", 1330, 1445);
        catalog.addCourseToCatalog("CSC226", "Discrete Math", "001", 3, "psdongre", 10, "TH", 1330, 1445);
 
        // Save the catalog to a file
        try {
            catalog.saveCourseCatalog("test-files/actual_course_records.txt");
        } catch (IllegalArgumentException e) {
            fail("Error saving catalog.");
        }
    }

}
