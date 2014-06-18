/**
 * This class represents a single participant of the reviews
 */

package data;

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

	@Override
	public int compareTo(Participant o) {
		if (o.groupNumber < this.groupNumber) {
			return -1;
		} else if (o.groupNumber == this.groupNumber) {
			return 0;
		} else
			return 1;
	}

}
