/*******************************************************************************
 * Copyright (c) 2014 Verena Käfer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/gpl.html
 *
 * Contributors:
 * Verena Käfer - initial version
 *******************************************************************************/
package data;

import java.util.ArrayList;

/**
 * This class represents a complete review at least consisting of author,
 * scribe, 3 reviewers, date, time and place.
 */
public class Review {

	public Review(Participant author) {
		this.author = author;
		this.groupNumber = author.getGroupNumber();
	}

	// The author of the reviewed artifact
	private Participant author;
	// The assigned scribe of the review
	private Participant scribe;
	// The assigned moderator of the review
	private Participant moderator;
	// The list of reviewers for this review
	private ArrayList<Participant> reviewers = new ArrayList<Participant>();
	// The room in which the review should be conducted
	private Room assignedRoom;
	// The group number of the reviewed group
	private int groupNumber;
	// letter for anonymisation
	private String letter;

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

	public ArrayList<Participant> getReviewers() {
		return reviewers;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String string) {
		this.letter = string;
	}

}
