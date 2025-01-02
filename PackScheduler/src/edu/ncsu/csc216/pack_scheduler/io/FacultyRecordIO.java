package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * The FacultyRecordIO class handles input and output operations for faculty records.
 * It includes methods to read faculty data from a file and write faculty data to a file.
 * This class provides the functionality to parse a file containing faculty information,
 * create Faculty objects, and add them to a list while ensuring no duplicates are added.
 * It also handles saving the faculty list back to a file.
 * It uses LinkedList to store the faculty records.
 */
public class FacultyRecordIO {

	/**
	 * Reads faculty records from a specified file and returns a list of Faculty objects.
	 * Each line in the file is expected to contain the faculty details in a comma-separated format:
	 * firstName, lastName, id, email, password, maxCourses.
	 * The method skips invalid lines or duplicates.
	 *
	 * @param fileName the name of the file to read from
	 * @return a LinkedList of Faculty objects read from the file
	 * @throws FileNotFoundException if the specified file cannot be found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    LinkedList<Faculty> faculties = new LinkedList<>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readStudent, and get the object
	            //If trying to construct a student in readStudent() results in an exception, flow of control will transfer to the catch block, below
	            Faculty faculty = processFaculty(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < faculties.size(); i++) {
	                //Get the course at index i
	                Faculty current = faculties.get(i);
	                //Check if the name and section are the same
	                if (faculty.getFirstName().equals(current.getFirstName()) &&
	                        faculty.getLastName().equals(current.getLastName())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                faculties.add(faculty); //Add to the ArrayList!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the ArrayList with all the courses we read!
	    return faculties;
	}

	/**
	 * Processes a single line of input to create a Faculty object.
	 * The line should be in a comma-separated format: 
	 * firstName, lastName, id, email, password, maxCourses.
	 * If the input line is invalid or does not have the correct number of tokens, an exception is thrown.
	 *
	 * @param line the line of text to process
	 * @return a Faculty object created from the line input
	 * @throws IllegalArgumentException if the line format is invalid
	 */
	private static Faculty processFaculty(String line) {
		int numberOfToken = 0;
		try (Scanner lineProcessor = new Scanner(line).useDelimiter(",")) {
			try {
				int maxCourses = 1;
				
				String firstName = lineProcessor.next();
				numberOfToken++;
				String lastName = lineProcessor.next();
				numberOfToken++;
				String id = lineProcessor.next();
				numberOfToken++;
				String email = lineProcessor.next();
				numberOfToken++;
				String password = lineProcessor.next();
				numberOfToken++;
				if(lineProcessor.hasNext()) {
					maxCourses = Integer.parseInt(lineProcessor.next());
					numberOfToken++;
				}
				 
				if(numberOfToken != 6) {
					throw new IllegalArgumentException();
				}
				return new Faculty(firstName, lastName, id, email, password, maxCourses);
			} catch(Exception e) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Writes the list of Faculty objects to a specified file. 
	 * Each Faculty object is written in a comma-separated format:
	 * firstName, lastName, id, email, password, maxCourses.
	 *
	 * @param fileName the name of the file to write to
	 * @param facultyDirectory the list of Faculty objects to write
	 * @throws IOException if an error occurs while writing to the file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(fileName)));
		
		for(int i = 0; i < facultyDirectory.size(); i++) {
			printWriter.println(facultyDirectory.get(i).toString());
		}
		printWriter.close();
		
	}

}
