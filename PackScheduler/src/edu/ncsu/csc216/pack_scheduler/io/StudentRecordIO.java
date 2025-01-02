package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileInputStream;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * StudentRecordIO outlines methods for Student file input and output
 * @author Priyanshu Dongre
 */
public class StudentRecordIO {

	/**
     * Reads student records from a file and generates a list of valid Students. Any invalid
     * Students are ignored. If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Student records from
     * @return a Sorted list of valid Students
     * @throws FileNotFoundException if the file cannot be found or read
     * @throws IllegalArgumentException if any student information is invalid
     */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
//		Scanner fileReader = new Scanner(new FileInputStream(fileName));
//		SortedList<Student> students = new SortedList<>();
//		while(fileReader.hasNextLine()) {
//				Student student = readStudent(fileReader.nextLine());
//				
//				boolean duplicate = false;
//				
//				for(int i = 0; i < students.size(); i++) {
//					if(student.equals(students.get(i))) {
//						duplicate = true;
//						break;
//					}
//				}
//				
//				if(!duplicate) {
//					students.add(student);
//				}
//		}
//		fileReader.close();
//		return students;
		
		 Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
		    SortedList<Student> students = new SortedList<>(); //Create an empty array of Course objects
		    while (fileReader.hasNextLine()) { //While we have more lines in the file
		        try { //Attempt to do the following
		            //Read the line, process it in readStudent, and get the object
		            //If trying to construct a student in readStudent() results in an exception, flow of control will transfer to the catch block, below
		            Student student = readStudent(fileReader.nextLine()); 

		            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
		            boolean duplicate = false;
		            //Look at all the courses in our list
		            for (int i = 0; i < students.size(); i++) {
		                //Get the course at index i
		                Student current = students.get(i);
		                //Check if the name and section are the same
		                if (student.getFirstName().equals(current.getFirstName()) &&
		                        student.getLastName().equals(current.getLastName())) {
		                    //It's a duplicate!
		                    duplicate = true;
		                    break; //We can break out of the loop, no need to continue searching
		                }
		            }
		            //If the course is NOT a duplicate
		            if (!duplicate) {
		                students.add(student); //Add to the ArrayList!
		            } //Otherwise ignore
		        } catch (IllegalArgumentException e) {
		            //The line is invalid b/c we couldn't create a course, skip it!
		        }
		    }
		    //Close the Scanner b/c we're responsible with our file handles
		    fileReader.close();
		    //Return the ArrayList with all the courses we read!
		    return students;
	}

	/**
	 * Writes the list of Students to a file
	 * @param fileName file to be written to
	 * @param studentDirectory list of students to write to the file
	 * @throws IOException if cannot write to file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		
		PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(fileName)));
		
		for(int i = 0; i < studentDirectory.size(); i++) {
			printWriter.println(studentDirectory.get(i).toString());
		}
		printWriter.close();
	}
	
	/**
	 * Reads Student information from a line of a file
	 * @param line line to be read
	 * @return Student object for line read
	 * @throws IllegalArgumentException if student is invalid
	 */
	private static Student readStudent(String line) {
		int numberOfToken = 0;
		try (Scanner lineProcessor = new Scanner(line).useDelimiter(",")) {
			try {
				int maxCredits = 18;
				
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
					maxCredits = Integer.parseInt(lineProcessor.next());
					numberOfToken++;
				}
				 
				if(numberOfToken != 6) {
					throw new IllegalArgumentException();
				}
				return new Student(firstName, lastName, id, email, password, maxCredits);
			} catch(Exception e) {
				throw new IllegalArgumentException();
			}
		}
		
	}

}