
package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

/**
 * Represents an abstract Activity that serves as a base class for different
 * activities such as courses or events. An Activity has a title, meeting days,
 * start time, and end time. This class provides methods for handling meeting
 * times and ensures the correct format for time and days.
 * 
 * Subclasses must implement methods to return both short and long display
 * arrays, as well as determine if two activities are duplicates.
 * 
 * @author Priyanshu Dongre
 */

public abstract class Activity implements Conflict {

	/** Course's title. */
	protected String title;
	/** Course's meeting days */
	protected String meetingDays;
	/** Course's starting time */
	protected int startTime;
	/** Course's ending time */
	protected int endTime;

	/**
	 * Constructs an Activity with:
	 * 
	 * @param title       the title of the activity
	 * @param meetingDays the days the activity meets (e.g., "MWF" for Monday,
	 *                    Wednesday, Friday)
	 * @param startTime   the start time of the activity in military format
	 * @param endTime     the end time of the activity in military format
	 * @throws IllegalArgumentException if any parameters are invalid
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Returns the Course's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}

		this.title = title;
	}

	/**
	 * Returns the Course's meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Course's start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the meeting days and time for this Course. This method validates that
	 * the provided meetingDays, startTime, and endTime are valid and throws an
	 * IllegalArgumentException if the input is invalid.
	 * 
	 * Valid meeting days are represented by the characters 'M', 'T', 'W', 'H', and
	 * 'F' (Monday to Friday).
	 * 
	 * @param meetingDays a String representing the days of the week the course will
	 *                    meet
	 * @param startTime   the start time of the course in military time (e.g., 1330
	 *                    for 1:30 PM)
	 * @param endTime     the end time of the course in military time (e.g., 1530
	 *                    for 3:30 PM)
	 * @throws IllegalArgumentException if the input is invalid
	 */

	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if ("A".equals(meetingDays)) {
			if (startTime == 0 && endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			if (startTime != 0 && endTime == 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			if (startTime != 0 && endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		} else {
			int countMonday = 0;
			int countTuesday = 0;
			int countWednesday = 0;
			int countThursday = 0;
			int countFriday = 0;

			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'M') {
					countMonday++;
				} else if (meetingDays.charAt(i) == 'T') {
					countTuesday++;
				} else if (meetingDays.charAt(i) == 'W') {
					countWednesday++;
				} else if (meetingDays.charAt(i) == 'H') {
					countThursday++;
				} else if (meetingDays.charAt(i) == 'F') {
					countFriday++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}

			if (countMonday > 1 || countTuesday > 1 || countWednesday > 1 || countThursday > 1 || countFriday > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			int startHour = startTime / 100;
			int startMin = startTime % 100;
			int endHour = endTime / 100;
			int endMin = endTime % 100;

			if (startHour < 0 || startHour >= 24) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (startMin < 0 || startMin >= 60) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endHour < 0 || endHour >= 24) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endMin < 0 || endMin >= 60) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			if (startTime > endTime) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

		}
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns a string representation of the Course's meeting days and times.
	 * 
	 * @return Course's meeting days and times.
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}

		return meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);
	}

	/**
	 * Returns the time in AM/PM format.
	 * 
	 * @param time as an integer
	 * @return time as a string
	 */
	private String getTimeString(int time) {
		int hour = time / 100;
		int min = time % 100;
		boolean morning = true;

		if (hour >= 12) {
			hour -= 12;
			morning = false;
		}
		if (hour == 0) {
			hour = 12;
		}

		String minS = "" + min;
		if (min < 10) {
			minS = "0" + minS;
		}

		String end = morning ? "AM" : "PM";

		return hour + ":" + minS + end;
	}

	/**
	 * Returns a short version of the display array for this `Activity`. This method
	 * is abstract and should be implemented by subclasses to return key details
	 * about the activity in a concise format.
	 * 
	 * @return String array containing a brief representation of the `Activity`'s
	 *         details
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Returns a detailed version of the display array for this `Activity`. This
	 * method is abstract and should be implemented by subclasses to return a more
	 * comprehensive set of details about the activity.
	 * 
	 * @return String array containing an expanded representation of the
	 *         `Activity`'s details
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Generates a hash code for the object based on the values of its fields. This
	 * method overrides the default implementation of `hashCode` in the `Object`
	 * class and ensures that two objects with the same `endTime`, `meetingDays`,
	 * `startTime`, and `title` will return the same hash code.
	 * 
	 * @return an integer hash code value derived from the object's `endTime`,
	 *         `meetingDays`, `startTime`, and `title` fields.
	 */

	@Override
	public int hashCode() {
		return Objects.hash(endTime, meetingDays, startTime, title);
	}

	/**
	 * Compares this object to the specified object for equality. This method
	 * overrides the default implementation of `equals` in the `Object` class. Two
	 * `Activity` objects are considered equal if they have the same `endTime`,
	 * `meetingDays`, `startTime`, and `title` values.
	 * 
	 * @param obj the object to compare to this `Activity` object
	 * @return true if the specified object is equal to this `Activity` object,
	 *         `false` otherwise
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Activity))
			return false;
		Activity other = (Activity) obj;
		return endTime == other.endTime && Objects.equals(meetingDays, other.meetingDays)
				&& startTime == other.startTime && Objects.equals(title, other.title);
	}

	/**
	 * Determines whether the specified `Activity` object is a duplicate of this
	 * `Activity` object. This method is abstract and must be implemented by
	 * subclasses to define what constitutes a duplicate activity. The comparison
	 * criteria should be specified in the implementing class.
	 * 
	 * @param activity the `Activity` object to compare against this one
	 * @throws IllegalArgumentException if the activity is null.
	 * @return true if the specified `Activity` is considered a duplicate, `false`
	 *         otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);

	 /**
     * Checks for a conflict between this activity and another. This method checks
     * if the two activities overlap in terms of meeting days and times.
     * 
     * @param possibleConflictingActivity the activity to check for a conflict
     * @throws ConflictException if a conflict is detected
     */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {

		if (!("A".equals(this.meetingDays) && "A".equals(possibleConflictingActivity.getMeetingDays()))) {
			for (int i = 0; i < this.meetingDays.length(); i++) {
				if (i == possibleConflictingActivity.getMeetingDays().length()) {
					break;
				}
				if (possibleConflictingActivity.getMeetingDays().contains("" + this.meetingDays.charAt(i))
						|| this.meetingDays.contains("" + possibleConflictingActivity.getMeetingDays().charAt(i))) {

					// Start time of both activities are same.
					if (this.startTime == possibleConflictingActivity.getStartTime()) {
						throw new ConflictException();
					}

					// One activity ends and immediately another activity starts
					if (this.startTime == possibleConflictingActivity.getEndTime()) {
						throw new ConflictException();
					}

					// One activity ends and immediately another activity starts
					if (possibleConflictingActivity.getStartTime() == this.endTime) {
						throw new ConflictException();
					}

					// Both activities ends at same time.
					if (this.endTime == possibleConflictingActivity.getEndTime()) {
						throw new ConflictException();
					}

					// If one activity starts while another activity is in progress.
					if (this.startTime > possibleConflictingActivity.getStartTime()
							&& this.startTime < possibleConflictingActivity.getEndTime()
							|| possibleConflictingActivity.getStartTime() > this.startTime
									&& possibleConflictingActivity.getStartTime() < this.endTime) {
						throw new ConflictException();
					}

				}
			}
		}
	}

}
