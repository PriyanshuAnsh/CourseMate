package edu.ncsu.csc216.pack_scheduler.user;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;


/**
 * Tests the Student object.
 * @author Priyanshu Dongre, Suyash Patel
 * Referenced CourseRecordIOTest by Dr. Sarah Heckman
 * Some guidance and code provided by the Lab 02 CSC217 Instructions
 */
public class StudentTest {
	
	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Max credits for this Student */
	private int credits = 15;
	/** Default max credits for a Student */
	private int defaultMaxCredits = 18;
	/** Invalid credits - too few credits */
	private int invalidCreditsFew = 2;

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	//This is a block of code that is executed when the StudentTest object is
	//created by JUnit.  Since we only need to generate the hashed version
	//of the plaintext password once, we want to create it as the StudentTest object is
	//constructed.  By automating the hash of the plaintext password, we are
	//not tied to a specific hash implementation.  We can change the algorithm
	//easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes valid parameters.
	 */
	@Test
	public void testStudentWithCredits() {
		Student student = assertDoesNotThrow(
				() -> new Student(firstName, lastName, id, email, hashPW, credits),
				"Constructing valid Student with credits - should not throw exception");
		assertEquals(firstName, student.getFirstName(), "Testing that constructor sets first name correctly");
		assertEquals(lastName, student.getLastName(), "Testing that constructor sets last name correctly");
		assertEquals(id, student.getId(), "Testing that constructor sets id correctly");
		assertEquals(email, student.getEmail(), "Testing that constructor sets email correctly");
		assertEquals(hashPW, student.getPassword(), "Testing that constructor sets password correctly - password hashed during testing");
		assertEquals(credits, student.getMaxCredits(), "Testing that constructor sets max credits correctly - 15 credits passed as parameter");
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes invalid (null) firstName.
	 * Test format provided by Lab 02 CSC217 Instructions.
	 */
	@Test
	public void testStudentWithCreditsInvalidFirstName() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(null, lastName, id, email, hashPW, credits));
		assertEquals("Invalid first name", e1.getMessage(), "Testing null first name exception message");
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes invalid (null) lastName.
	 * Test format provided by Lab 02 CSC217 Instructions.
	 */
	@Test
	public void testStudentWithCreditsInvalidLastName() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, null, id, email, hashPW, credits));
		assertEquals("Invalid last name", e1.getMessage(), "Testing null last name exception message");
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes invalid (empty String) id.
	 * Test format provided by Lab 02 CSC217 Instructions.
	 */
	@Test
	public void testStudentWithCreditsInvalidId1() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, "", email, hashPW, credits));
		assertEquals("Invalid id", e1.getMessage(), "Testing empty String Id exception message");
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes invalid (null) id.
	 * Test format provided by Lab 02 CSC217 Instructions.
	 */
	@Test
	public void testStudentWithCreditsInvalidId2() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, null, email, hashPW, credits));
		assertEquals("Invalid id", e1.getMessage(), "Testing null Id exception message");
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes invalid (null) email.
	 * Test format provided by Lab 02 CSC217 Instructions.
	 */
	@Test
	public void testStudentWithCreditsInvalidEmail() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, id, null, hashPW, credits));
		assertEquals("Invalid email", e1.getMessage(), "Testing null email exception message");
		
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Student("Ayush", "Kanna", "akanna", "ayush.k", "abcd", 15));
		assertEquals("Invalid email", e2.getMessage(), "Testing Invalid email with no '@' sign");
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes invalid (empty String) password.
	 * Test format provided by Lab 02 CSC217 Instructions.
	 */
	@Test
	public void testStudentWithCreditsInvalidPassword() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, id, email, "", credits));
		assertEquals("Invalid password", e1.getMessage(), "Testing empty String password exception message");
	}
	
	/**
	 * Tests the Student constructor with max credits given as a parameter; passes invalid (too few) credits.
	 * Test format provided by Lab 02 CSC217 Instructions.
	 */
	@Test
	public void testStudentWithCreditsInvalidCredits() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, id, email, hashPW, invalidCreditsFew));
		assertEquals("Invalid max credits", e1.getMessage(), "Testing (too few) credits exception message");
	}
	
	/**
	 * Tests the Student constructor with max credits set to default.
	 */
	@Test
	public void testStudentDefaultCredits() {
		Student student = assertDoesNotThrow(
				() -> new Student(firstName, lastName, id, email, hashPW),
				"Constructing valid Student without credit parameter - should not throw exception");
		assertEquals(firstName, student.getFirstName(), "Testing that constructor sets first name correctly");
		assertEquals(lastName, student.getLastName(), "Testing that constructor sets last name correctly");
		assertEquals(id, student.getId(), "Testing that constructor sets id correctly");
		assertEquals(email, student.getEmail(), "Testing that constructor sets email correctly");
		assertEquals(hashPW, student.getPassword(), "Testing that constructor sets password correctly - password hashed during testing");
		assertEquals(defaultMaxCredits, student.getMaxCredits(), "Testing that constructor sets max credits correctly - default 18 credits");
	}
	
	
	/**
	 * Tests setFirstName with a valid first name.
	 */
	@Test
	public void testSetFirstNameValid() {
		// First, construct a valid Student
		Student student = new Student(firstName, lastName, id, email, hashPW);
		// Next, try to reset one of its fields
		student.setFirstName("Bob");
		assertEquals("Bob", student.getFirstName(), "Testing setFirstName with valid first name");
	}
	
	/**
	 * Tests setLastName with a valid last name.
	 */
	@Test
	public void testSetLastNameValid() {
		// First, construct a valid Student
		Student student = new Student(firstName, lastName, id, email, hashPW);
		// Next, try to reset one of its fields
		student.setLastName("Bobson");
		assertEquals("Bobson", student.getLastName(), "Testing setLastName with valid last name");
	}
	
	/**
	 * Tests setEmail with a valid email.
	 */
	@Test
	public void testSetEmailValid() {
		// First, construct a valid Student
		Student student = new Student(firstName, lastName, id, email, hashPW);
		// Next, try to reset one of its fields
		student.setEmail("bbobson@ncsu.edu");
		assertEquals("bbobson@ncsu.edu", student.getEmail(), "Testing setEmail with valid parameter");
	}
	
	
	/**
	 * Test checks setPassword method for valid student's password.
	 */
	@Test
	public void testSetPasswordValid() {
		Student student = new Student(firstName, lastName, id, email, hashPW);
		
		student.setPassword("abcd1234");
		assertEquals("abcd1234", student.getPassword(), "Testing setPassword with valid parameter");
	}
	
	
	/**
	 * Tests setMaxCredit method with valid parameter.
	 */
	@Test
	public void testSetMaxCreditsValid() {
		Student student = new Student(firstName, lastName, id, email, hashPW);
		
		student.setMaxCredits(5);
		
		assertEquals(5, student.getMaxCredits(), "Testing setMaxCredits with valid parameter");
	}
	
	/**
	 * Tests all setters for each invalid settings. 
	 */
	@Test
	public void testAllSettersInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student("", lastName, id, email, hashPW, credits));
		assertEquals("Invalid first name", e1.getMessage(), "Testing empty String first name exception message");
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student("Hi", "", id, email, hashPW, credits));
		assertEquals("Invalid last name", e2.getMessage(), "Testing empty string last name exception message");
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Student("Hi", "Hello", "", email, hashPW, credits));
		assertEquals("Invalid id", e3.getMessage(), "Testing empty string for id exception message");
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Student("Hi", "Hello", "HH", "", hashPW, credits));
		assertEquals("Invalid email", e4.getMessage(), "Testing empty string for email, exception message");
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Student("Hi", "Hello", "HH", "hh@ncsu.edu", "", credits));
		assertEquals("Invalid password", e5.getMessage(), "Testing empty string for password, exception message");
		
		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Student("Hi", "Hello", "HH", "hh@ncsu.edu", hashPW, 20));
		assertEquals("Invalid max credits", e6.getMessage(), "Testing max credits as 20, exception message");
	}
	
	/**
	 * Tests setMaxCredits with an invalid (negative) max credits.
	 */
	@Test
	public void testSetMaxCreditsInvalidNegative() {
	    Student student = new Student(firstName, lastName, id, email, hashPW);
	    Exception e1 = assertThrows(IllegalArgumentException.class,
	            () -> student.setMaxCredits(-18));
	    assertEquals("Cannot have negative max credits.", e1.getMessage(), "Testing negative max credits exception message");
	}
	
	
	 /**
     * Test the hashCode() method for two identical Student objects.
     */
    @Test
    public void testHashCodeSameObjects() {
        // Create two identical Student objects
        Student s1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password123", 15);
        Student s2 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password123", 15);
        
        // Assert that the hash codes are the same
        assertEquals(s1.hashCode(), s2.hashCode(), "Hash codes for identical students should match.");
    }

    /**
     * Test the hashCode() method for two different Student objects.
     */
    @Test
    public void testHashCodeDifferentObjects() {
        // Create two different Student objects
        Student s1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password123", 15);
        Student s2 = new Student("Jane", "Smith", "jsmith", "jsmith@ncsu.edu", "password456", 12);
        
        // Assert that the hash codes are different
        assertNotEquals(s1.hashCode(), s2.hashCode(), "Hash codes for different students should not match.");
    }


    /**
     * Test the consistency of the hashCode() method.
     */
    @Test
    public void testHashCodeConsistency() {
        // Create a Student object
        Student s1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password123", 15);
        
        // Check if calling hashCode multiple times returns the same value
        int initialHashCode = s1.hashCode();
        assertEquals(initialHashCode, s1.hashCode(), "Hash code should remain consistent on repeated calls.");
        assertEquals(initialHashCode, s1.hashCode(), "Hash code should remain consistent on repeated calls.");
    }

    /**
     * Test hashCode for different field values that would impact the hash code calculation.
     */
    @Test
    public void testHashCodeWithFieldChanges() {
        // Create a Student object
        Student s1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password123", 15);
        
        // Modify some fields and check if hash code changes
        Student s2 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password123", 16); // Different max credits
        assertNotEquals(s1.hashCode(), s2.hashCode(), "Hash codes should differ when maxCredits are different.");
        
        Student s3 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password456", 15); // Different password
        assertNotEquals(s1.hashCode(), s3.hashCode(), "Hash codes should differ when passwords are different.");
    }
    
	
	/**
	 * Tests the equals method with two identical Student objects.
	 */
	@Test
	public void testEqualsIdenticalStudents() {
	    Student student1 = new Student(firstName, lastName, id, email, hashPW);
	    Student student2 = new Student(firstName, lastName, id, email, hashPW);
	    assertTrue(student1.equals(student2), "Testing equals with identical students");
	}

	/**
	 * Tests the equals method with two different Student objects.
	 */
	@Test
	public void testEqualsDifferentStudents() {
	    Student student1 = new Student(firstName, lastName, id, email, hashPW);
	    Student student2 = new Student("Guruji", "FarOff", "GGuruji", "abcd@ncsu.edu", "abcd1234");
	    assertFalse(student1.equals(student2), "Testing equals with different students");
	}

	/**
	 * Tests the equals method with a null object.
	 */
	@Test
	public void testEqualsNullObject() {
	    Student student1 = new Student(firstName, lastName, id, email, hashPW);
	    
	    assertNotEquals(null, student1, "Testing equals with null object");
	    
	  
	}

	/**
	 * Tests the equals method with an object of a different class.
	 */
	@Test
	public void testEqualsDifferentClass() {
	    Student student1 = new Student(firstName, lastName, id, email, hashPW);
	    Object obj = new Object();
	    assertFalse(student1.equals(obj), "Testing equals with object of different class");
	    
	}

	/**
	 * Tests the equals method with the same object.
	 */
	@Test
	public void testEqualsSameObject() {
	    Student student1 = new Student(firstName, lastName, id, email, hashPW);
	    assertTrue(student1.equals(student1), "Testing equals with same object");
	}
	
	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}
	
	
	/**
     * Test compareTo when the last names differ.
     * Ensures that students with different last names are compared correctly.
     */
    @Test
    public void testCompareToLastNameDiffers() {
        Student student1 = new Student("John", "Don", "123", "john.don@ptsdsd.com", "password123", 15);
        Student student2 = new Student("John", "Smith", "456", "john.smith@google.com", "password456", 18);

        assertEquals(-1, student1.compareTo(student2), "Testing CompareTo Method for (LastName) 'Smaller' Scenario");

        assertEquals(1, student2.compareTo(student1), "Testing CompareTo Method for (LastName) 'Greater' Scenario");
    }

    /**
     * Test compareTo when the first names differ but last names are the same.
     * Ensures that students with the same last name but different first names are compared correctly.
     */
    @Test
    public void testCompareToFirstNameDiffers() {
        Student student1 = new Student("Alice", "Doe", "123", "alice.doube@ncsu.com", "password123", 15);
        Student student2 = new Student("Bob", "Doe", "456", "bob.doe@yahoo.com", "password456", 18);

        assertEquals(-1, student1.compareTo(student2), "Testing CompareTo Method for FirstName 'Smaller' Scenario");

       
        assertEquals(1, student2.compareTo(student1), "Testing CompmareTo Method for FirstName 'Greater' Scenario");
    }

    /**
     * Test compareTo when the IDs differ but names are the same.
     * Ensures that students with the same first and last names but different IDs are compared correctly.
     */
    @Test
    public void testCompareToIdDiffers() {
        Student student1 = new Student("John", "Don", "123", "john.don@icloud.com", "password123", 15);
        Student student2 = new Student("John", "Don", "456", "john.don@chai10.com", "password456", 18);

  
        assertEquals(-1, student1.compareTo(student2), "Testing CompmareTo Method for ID 'Smaller' Scenario");


        assertEquals(1, student2.compareTo(student1), "Testing CompmareTo Method for ID 'Greater' Scenario");
    }

    /**
     * Test compareTo for students that are completely identical.
     * Ensures that students with the same last name, first name, and ID are considered equal.
     */
    @Test
    public void testCompareToEqualStudents() {
        Student student1 = new Student("John", "Don", "123", "john.don@cscatncstate.edu", "password123", 15);
        Student student2 = new Student("John", "Don", "123", "john.don@uncc.edu", "password123", 15);

        assertEquals(0, student1.compareTo(student2), "Testing CompareTo for Same Student");
    }

    /**
     * Test compareTo when both last names and first names differ.
     * Ensures that the comparison behaves correctly when both last and first names are different.
     */
    @Test
    public void testCompareToMixedComparison() {
        Student student1 = new Student("Alice", "Smith", "123", "alice.smith@homefull.org", "password123", 15);
        Student student2 = new Student("Bob", "Don", "456", "bob.don@milton.com", "password456", 18);

        assertEquals(1, student1.compareTo(student2), "Testing CompmareTo Method for Mixed Comparision");

        assertEquals(-1, student2.compareTo(student1), "Testing CompmareTo Method for Mixed Comparision");
    }
    
    /**
     * tests getting the schedule
     */
    @Test
    public void testGetSchedule() {
        Student s1 = new Student("James", "Brother", "jbrother", "jbrother@gmail.com", "password");
        assertNotNull(s1.getSchedule());
        assertEquals("My Schedule", s1.getSchedule().getTitle());
        Schedule schedule = s1.getSchedule();
        schedule.setTitle("Fall 2024");
        assertEquals("Fall 2024", s1.getSchedule().getTitle());
        assertEquals(0, s1.getSchedule().getScheduledCourses().length);
    }
    
    /**
     * Tests the functionality of adding courses to a student's schedule.
     * 
     * This test verifies that a student can successfully add multiple courses to their schedule if there are no conflicts 
     * or duplicates. It also checks that the total credits of the schedule are calculated correctly after adding courses.
     * 
     * The test involves creating several course instances and a student instance. It checks the ability to add each course
     * and validates the total credits in the schedule after the additions.
     * 
     * Additionally, the test verifies that an attempt to add a conflicting course is unsuccessful and that the total credits 
     * remain unchanged.
     */
    @Test
    public void testCanAdd() {
    	Course course1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Course course2 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF",
				1015, 1130);
		Course course3 = new Course("CSC246", "Operating System", "001", 4, "Jackson", 10, "TH", 1500, 1640);
		Course course4 = new Course("HI264", "Modern Asia", "001", 4, "David", 20, "TH", 830, 945);
		Course course5 = new Course("CSC731", "Advance Automata", "001", 5, "Ansh", 30, "MW", 1000, 1530);
    	Course[] courses = {course1, course2, course3, course4}; 
    	Student anil = new Student("Anil", "Kumble", "AKumble", "akumble@ncsu.edu", "password");
    	assertNotNull(anil.getSchedule());
        assertEquals("My Schedule", anil.getSchedule().getTitle());
        Schedule schedule = anil.getSchedule();
        for(int i = 0; i < courses.length; i++) {
        	assertTrue(anil.canAdd(courses[i]));
        	assertTrue(schedule.addCourseToSchedule(courses[i]));
        }
        assertEquals(14, schedule.getScheduleCredits());
        
        assertFalse(schedule.canAdd(course5));
        assertFalse(anil.canAdd(course5));
        assertEquals(14, schedule.getScheduleCredits());
        
        
        
    }

}
