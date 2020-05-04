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
 * This class represents a single participant of the reviews
 */
public class Participant implements Comparable<Participant> {

	public Participant(String firstName, String lastName, String eMail, String groupNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMailAdress = eMail;
		this.groupNumber = groupNumber;
	}

	// The amount of reviews this participant is assigned to
	private int numberOfReviews;

	private String firstName;
	private String lastName;
	private String eMailAdress;

	private ArrayList<Review> reviews = new ArrayList<Review>();

	// The SoPra group number
	private String groupNumber;

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMailAdress() {
		return eMailAdress;
	}

	public void seteMailAdress(String eMailAdress) {
		this.eMailAdress = eMailAdress;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Participant o) {
		// sorting by group number
		return o.groupNumber.compareTo(this.groupNumber);
	}

	public void increaseParticipation() {
		numberOfReviews++;
	}

	public void decreaseParticipation() {
		numberOfReviews--;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	public void resetReviews() {
		reviews.clear();
	}
}
