package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedListRecursive;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Priyanshu Dongre
 */
public class CourseRecordIO {
	/** 
	 * A static `LinkedListRecursive` instance that stores a list of `Faculty` objects. 
	 * This list is used to manage and maintain faculty information across the application.
	 */
	private static LinkedListRecursive<Faculty> facultyList = new LinkedListRecursive<>();

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file

		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects

		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the ArrayList!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the ArrayList with all the courses we read!
		return courses;
	}

	/**
	 * Writes the given list of Courses to
	 * 
	 * @param fileName file to write schedule of Courses to
	 * @param courses  list of Courses to write
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();

	}

	/**
	 * Reads a line of text representing a course and converts it into a Course
	 * object. The input string should be comma-separated and must include the
	 * following details in this order:
	 * 
	 * 1. Course Name: The course's code or name (e.g., "CSC216"). 2. Course Title:
	 * The course's descriptive title (e.g., "Software Development Fundamentals").
	 * 3. Section: The section number of the course (e.g., "001"). 4. Credits: The
	 * number of credit hours assigned to the course (e.g., 3). 5. Instructor ID:
	 * The unique identifier for the instructor (e.g., "psdongre"). 6. Meeting Days:
	 * Days when the course meets (e.g., "MW" for Monday and Wednesday, or "A" for
	 * arranged). 7. Start Time (optional): The time the course starts. 8. End Time
	 * (optional): The time the course ends.
	 * 
	 * If the meeting days are listed as "A" (for arranged), no start or end time
	 * should be present. If there are extra times provided when the meeting days
	 * are "A", an exception will be thrown.
	 * 
	 * @param line A string representing the course details in a CSV format.
	 * @return A Course object constructed from the provided data.
	 * @throws IllegalArgumentException if the input format is incorrect or if the
	 *                                  data violates expected rules.
	 */
	private static Course readCourse(String line) {
		Scanner lineProcessor = new Scanner(line).useDelimiter(",");
		Course course = null;

		try {
			// Read course fields
			String courseName = lineProcessor.next();
			String courseTitle = lineProcessor.next();
			String section = lineProcessor.next();
			int credits = Integer.parseInt(lineProcessor.next());
			String instructorId = lineProcessor.next();
			int enrollmentCap = Integer.parseInt(lineProcessor.next());
			String meetingDays = lineProcessor.next();
			int startTime = 0;
			int endTime = 0;

			// Handle times for non-arranged courses
			if (!"A".equals(meetingDays)) {
				startTime = Integer.parseInt(lineProcessor.next());
				endTime = Integer.parseInt(lineProcessor.next());
			} else if ("A".equals(meetingDays) && lineProcessor.hasNext()) {
				lineProcessor.close();
				throw new IllegalArgumentException("Arranged courses cannot have start or end times.");
			}

			// Check if instructorId matches any Faculty
			Faculty assignedFaculty = null;
			for (int i = 0; i < facultyList.size(); i++) {
				Faculty faculty = facultyList.get(i);
				if (faculty.getId().equals(instructorId)) {
					assignedFaculty = faculty;
					break;
				}
			}

			// If a matching Faculty is found, add the course to their schedule
			if (assignedFaculty != null) {
				assignedFaculty.getSchedule().addCourseToSchedule(course);
			} else {
				instructorId = null; // If no matching faculty, clear the instructorId
			}

			// Create the course with the resolved instructorId
			course = new Course(courseName, courseTitle, section, credits, null, enrollmentCap, meetingDays,
					startTime, endTime);

		} catch (Exception e) {
			throw new IllegalArgumentException("Error reading course from input line.", e);
		} finally {
			lineProcessor.close();
		}

		return course;
	}

//	private static Course readCourse(String line) {
//		Scanner lineProcessor = new Scanner(line).useDelimiter(",");
//		Course course = null;
//
//		try {
//			// Read course fields
//			String courseName = lineProcessor.next();
//			String courseTitle = lineProcessor.next();
//			String section = lineProcessor.next();
//			int credits = Integer.parseInt(lineProcessor.next());
//			String instructorId = lineProcessor.next();
//			int enrollmentCap = Integer.parseInt(lineProcessor.next());
//			String meetingDays = lineProcessor.next();
//			int startTime = 0;
//			int endTime = 0;
//
//			// Handle times for non-arranged courses
//			if (!"A".equals(meetingDays)) {
//				startTime = Integer.parseInt(lineProcessor.next());
//				endTime = Integer.parseInt(lineProcessor.next());
//			} else if ("A".equals(meetingDays) && lineProcessor.hasNext()) {
//				lineProcessor.close();
//				throw new IllegalArgumentException("Arranged courses cannot have start or end times.");
//			}
//
//			// Create the course with instructorId initially set to null
//			course = new Course(courseName, courseTitle, section, credits, null, enrollmentCap, meetingDays, startTime,
//					endTime);
//
//			// Check if instructorId matches any Faculty
//			Faculty assignedFaculty = null;
//			for (int i = 0; i < facultyList.size(); i++) {
//				Faculty faculty = facultyList.get(i);
//				if (faculty.getId().equals(instructorId)) {
//					assignedFaculty = faculty;
//					break;
//				}
//			}
//
//			// If a matching Faculty is found, add the course to their schedule
//			if (assignedFaculty != null) {
//				assignedFaculty.getSchedule().addCourseToSchedule(course);
//			}
//
//		} catch (Exception e) {
//			throw new IllegalArgumentException("Error reading course from input line.", e);
//		} finally {
//			lineProcessor.close();
//		}
//
//		return course;
//	}

}