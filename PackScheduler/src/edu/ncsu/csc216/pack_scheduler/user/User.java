/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * Represents an abstract user in the system, such as a student or registrar.
 * A user has basic information like first name, last name, ID, email, and password.
 * This class provides methods to set and get these fields with validation.
 * @author Priyanshu Dongre, Suyash Patel
 */
public abstract class User {
	
	/** The first name of the user */
	private String firstName;
	
	/** The last name of the user */
	private String lastName;
	
	/** The ID of the user */
	private String id;
	
	/** The email address of the user */
	private String email;
	
	/** The password of the user */
	private String password;
	
	
	/**
	 * Constructs a User object with the provided first name, last name, ID, email, and password.
	 * @param firstName the first name of the user
	 * @param lastName the last name of the user
	 * @param id the ID of the user
	 * @param email the email of the user
	 * @param password the password of the user
	 * @throws IllegalArgumentException if any parameter is invalid
	 */
	
	public User(String firstName, String lastName, String id, String email, String password) {
		
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}
	
	/**
	 * sets the first name of the user, if it is not empty
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if the parameter is null or empty.
	 */
	public void setFirstName(String firstName) {
		
		if(firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}


	/**
	 * sets the last name of the student object to the given parameter, if it is not empty.
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		if(lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}


	/**
	 * sets the id of the student object to the given parameter, if it is not empty.
	 * @param id the id to set
	 */
	protected void setId(String id) {
		if(id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}


	/**
	 * sets the email of the student object to the given parameter. Fails if:
	 * email is null
	 * email does not contain "@" or "."
	 * "." does not occur after "@" in the email String
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		
		if(email == null || email.length() == 0) {
			throw new IllegalArgumentException("Invalid email");
		}
		
		if(!email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}
		
		int addressIndex = email.indexOf("@");
		
		String temp = email.substring(addressIndex, email.length());
		
		if(!temp.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}
	
	/**
	 * sets the password of the student object to the given parameter, if it is not empty.
	 * @param password password to be set
	 */
	public void setPassword(String password) {
		
		if(password == null || password.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
		
	}
	
	/**
	 * gets the user's id
	 * @return returns the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * gets the user's first name
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * gets the user's last name
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * gets the user's password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * gets the uer's email
	 * @return the user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * converts the string to hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}

	/**
	 * checks if objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}
	
	
}
