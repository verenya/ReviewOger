/**
 * This class represents a single participant of the reviews
 */

package data;

import java.util.ArrayList;

public class Participant implements Comparable<Participant> {

	public Participant(String firstName, String lastName, String eMail,
			int groupNumber) {
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
	private int groupNumber;

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

	public int getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Participant o) {
		//sorting by group number
		if (o.groupNumber < this.groupNumber) {
			return -1;
		} else if (o.groupNumber == this.groupNumber) {
			return 0;
		} else
			return 1;
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

	public void resetReviews(){
		reviews.clear();
	}
}
