/**
 * This class represents a complete review at least consisting of author, scribe, 3 reviewers, date, time and place.
 */

package data;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Review {

	public Review(Participant author) {
		this.author = author;
		this.groupNumber = author.getGroupNumber();
	}

	// The author of the reviewed artifact
	private Participant author;
	// The assigned scribe of the review
	private Participant scribe;
	// Determines if author and scribe are two separate rolls or not
	private boolean authorIsScribe = true;
	// The assigned moderator of the review
	private Participant moderator;
	// The list of reviewers for this review
	private List<Participant> reviewers = new ArrayList<Participant>();
	// The amount of reviewers, that should be assigned to the review.
	private int numberOfReviewers;
	// Date and time of the review start
	private GregorianCalendar dateAndTime;
	// The room in which the review should be conducted
	private Room assignedRoom;
	// The group number of the reviewed group
	private int groupNumber;

	public Participant getAuthor() {
		return author;
	}

	public void setAuthor(Participant author) {
		this.author = author;
	}

	public Participant getScribe() {
		return scribe;
	}

	public void setScribe(Participant scribe) {
		this.scribe = scribe;
	}

	public Boolean getAuthorIsScribe() {
		return authorIsScribe;
	}

	public void setAuthorIsScribe(Boolean authorIsScribe) {
		this.authorIsScribe = authorIsScribe;
	}

	public Participant getModerator() {
		return moderator;
	}

	public void setModerator(Participant moderator) {
		this.moderator = moderator;
	}

	/**
	 * Removes a specific participant from the review. Returns false if given
	 * participant is not assigned to this review, else true.
	 * 
	 * @param reviewerToRemove
	 * @return true if successful, else false
	 */
	public boolean removeReviewer(Participant reviewerToRemove) {
		if (this.reviewers.contains(reviewerToRemove)) {
			this.reviewers.remove(reviewerToRemove);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Adds a specific participant to the review. Returns false, if participant
	 * is already in the review as a reviewer, else true.
	 * 
	 * @param newReviewer
	 * @return false, if already a reviewer in this review, else true
	 */
	public boolean addReviewer(Participant newReviewer) {
		if (this.reviewers.contains(newReviewer)) {
			return false;
		} else {
			this.reviewers.add(newReviewer);
			return true;
		}
	}

	public int getNumberOfReviewers() {
		return numberOfReviewers;
	}

	public void setNumberOfReviewers(int numberOfReviewers) {
		this.numberOfReviewers = numberOfReviewers;
	}

	public GregorianCalendar getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(GregorianCalendar dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Room getAssignedRoom() {
		return assignedRoom;
	}

	public void setAssignedRoom(Room assignedRoom) {
		this.assignedRoom = assignedRoom;
	}

	public int getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}

}
