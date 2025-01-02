package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Creates a Course Catalog with a SortedList of available Courses.
 * 
 * @author Priyanshu Dongre, Suyash Patel
 */
public class CourseCatalog {
	
	/** A catalog of Courses, sorted alphabetically by name and then section */
	private SortedList<Course> catalog;
	
	/**
	 * Constructs an empty catalog.
	 */
	public CourseCatalog() {
		this.catalog = new SortedList<Course>();
	}
	
	/**
	 * Resets the catalog to a new, empty catalog.
	 */
	public void newCourseCatalog() {
		this.catalog = new SortedList<Course>();
	}
	
	/**
	 * Loads course records into the catalog as new Courses.
	 * Throws IllegalArgumentException if course records file cannot be found.
	 * This method was adjusted from the CourseRecordIO readCourseRecords method, written by Dr. Sarah Heckman.
	 * @param fileName the name of the course records file
	 * @throws IllegalArgumentException if the course records file cannot be found
	 */
	public void loadCoursesFromFile(String fileName) {
//		try {
//			Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file, if it can be found
//			
//			// We will add the new Courses to our catalog field (a SortedList).
//			while (fileReader.hasNextLine()) { //While we have more lines in the file
//		        try { //Attempt to do the following
//		            //Read the line, process it in readCourse, and get the object
//		            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
//		            Course course = readCourse(fileReader.nextLine()); 
//
//		            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
//		            boolean duplicate = false;
//		            //Look at all the courses in our list
//		            for (int i = 0; i < catalog.size(); i++) {
//		                //Get the course at index i
//		                Course current = catalog.get(i);
//		                //Check if the name and section are the same
//		                if (course.compareTo(current) == 0) {
//		                    //It's a duplicate!
//		                    duplicate = true;
//		                    break; //We can break out of the loop, no need to continue searching
//		                }
//		            }
//		            //If the course is NOT a duplicate
//		            if (!duplicate) {
//		                catalog.add(course); //Add to the SortedList!
//		            } //Otherwise ignore
//		        } catch (IllegalArgumentException e) {
//		            //The line is invalid b/c we couldn't create a course, skip it!
//		        }
//		    }
//		    //Close the Scanner b/c we're responsible with our file handles
//		    fileReader.close();
//		    
//		} catch (FileNotFoundException f) {
//			// If the file cannot be found, throw IllegalArgumentException
//			throw new IllegalArgumentException("Unable to read file " + fileName);
//		}
		
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch(IOException io) {
			throw new IllegalArgumentException();
		}
	}
	
//	/**
//	 * Attempts to create a Course from each line in a file,
//	 * where the file has Course fields delimited by commas.
//	 * Copied from CourseRecordIO by Dr. Sarah Heckman.
//	 * @param line the line of the file to read
//	 * @return a new Course created from file information
//	 */
//	
//	private static Course readCourse(String line) {
//    	Scanner lineProcessor = new Scanner(line).useDelimiter(",");
//    	Course course = null;
//    	try {
//    		String courseName = "";
//    		String courseTitle = "";
//    		String section = "";
//    		int credits = 0;
//    		String instructorId = "";
//    		int enrollmentCap = 0;
//    		String meetingDays = "";
//    		int startTime = 0;
//    		int endTime = 0;
//    		
//    		while(lineProcessor.hasNext()) {
//    			courseName = lineProcessor.next();
//    			courseTitle = lineProcessor.next();
//    			section = lineProcessor.next();
//    			credits = Integer.parseInt(lineProcessor.next());
//    			instructorId = lineProcessor.next();
//    			enrollmentCap = Integer.parseInt(lineProcessor.next());
//    			meetingDays = lineProcessor.next();
//    			
//    			if(!"A".equals(meetingDays)) {
//    				startTime = Integer.parseInt(lineProcessor.next());
//    				endTime = Integer.parseInt(lineProcessor.next());
//    			} else if("A".equals(meetingDays) && lineProcessor.hasNext()) {
//    				lineProcessor.close();
//    				throw new IllegalArgumentException();
//    			}
//    			course = new Course(courseName, courseTitle, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
//    		}
//    	} catch (Exception e) {
//    		throw new IllegalArgumentException();
//    	} finally {
//    		lineProcessor.close();
//    	}
//    	lineProcessor.close();
//		return course;
//    }
	
	/**
	 * Adds a new Course to the Course Catalog.
	 * Returns true if the Course is sucessfully added.
	 * Throws IllegalArgumentException if any parameter is invalid as a Course field.
	 * @param name the Course name
	 * @param title the Course title
	 * @param section the Course section
	 * @param credits the Course's number of credit hours
	 * @param instructorId the Id of the Course instructor
	 * @param meetingDays the days that the Course meets
	 * @param startTime the time the Course starts
	 * @param endTime the time the Course ends
	 * @param enrollmentCap Maximum number of students that could enroll into the course.
	 * @return true if the Course is successfully added
	 * @throws IllegalArgumentException if any parameter is invalid as a Course field
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		
		// Attempt to create a new Course with the given parameters as fields.
		// If an IllegalArgumentException results, allow it to propagate to the user.
		Course courseToAdd = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
				
		// Return false if Course (of same name and section)
		// already exists in schedule
		for (int i = 0; i < catalog.size(); i++) {
			if (courseToAdd.compareTo(catalog.get(i)) == 0) {
				return false;
			}
		}
		
		// If the code reaches this point, the Course is valid and unique.
		// Add it to the Course Catalog.
		this.catalog.add(courseToAdd);
		return true;
	}
	
	/**
	 * Removes a Course from the catalog, given its name and section.
	 * Returns true if the Course is sucessfully removed.
	 * @param name the Course name
	 * @param section the Course section
	 * @return true if the Course is sucessfully removed.
	 * @throws IllegalArgumentException if either parameter is null
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		if (name == null | section == null) {
			throw new IllegalArgumentException("Cannot remove a Course with null name or section.");
		}
		
		// Search for the Course within the catalog.
		for (int i = 0; i < catalog.size(); i++) {
			// If the Course is found (by name and section), remove it and return true.
			if (name.equals(catalog.get(i).getName()) && section.equals(catalog.get(i).getSection())) {
				catalog.remove(i);
				return true;
			}
		}
		
		// If the code reaches this point, the Course to remove was not found.
		// So, return false.
		return false;
	}
	
	/**
	 * Returns a 2D String array representation of the catalog,
	 * with a row per course and four columns: name, section, title, and meeting information.
	 * 
	 * Copied from WolfScheduler.java by Jackson Timberlake.
	 * Referenced lecture notes from CSC216 (Dr. Heckman) for ArrayList syntax
	 * 
	 * @return 2D String array representation of the catalog
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][3];
		
		// Loop through each Course in the catalog
		for (int i = 0; i < catalog.size(); i++) {
			catalogArray[i] = catalog.get(i).getShortDisplayArray();		
		}
		return catalogArray;
	}
	
	/**
	 * Returns the Course from the catalog that matches the given name and section.
	 * Throws IllegalArgumentException if name or section is null.
	 * 
	 * Copied from WolfScheduler.java by Jackson Timberlake
	 * Referenced lecture notes from CSC216 (Dr. Heckman) for ArrayList syntax
	 * 
	 * @param name of the Course
	 * @param section of the Course
	 * @return the Course with the specified name and section
	 * @throws IllegalArgumentException if name or section is null
	 */
	public Course getCourseFromCatalog(String name, String section) {
		if (name == null || section == null) {
			throw new IllegalArgumentException("Cannot search for null name or section.");
		}
		
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		
		return null;
	}
	
	/**
     * Writes the given list of Courses to a file.
     * Adjusted from the writeCourseRecords method in CourseRecordIO by Dr. Sarah Heckman.
     * @param fileName file to write schedule of Courses to
     * @throws IllegalArgumentException if cannot write to file
     */
    public void saveCourseCatalog(String fileName) {
    	
    	try {
    		PrintStream fileWriter = new PrintStream(new File(fileName));

    		for (int i = 0; i < catalog.size(); i++) {
    			fileWriter.println(catalog.get(i).toString());
    		}
    		fileWriter.close();
        	
    	} catch (IOException i) {
    		throw new IllegalArgumentException("Unable to write to file " + fileName);
    	}
    }
}