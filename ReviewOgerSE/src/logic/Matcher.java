/**
 * This class provides the main logical functions to match participants to reviews
 */
package logic;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;
import data.ReviewPlan;

public class Matcher implements Callable<String> {

	public Matcher(String exitSelection, boolean scribeIsAuthor,
			boolean moderatorNotReviewerGroup, int amountReviewers) {
		this.moderatorNotReviewerGroup = moderatorNotReviewerGroup;
		this.scribeIsAuthor = scribeIsAuthor;
		this.amountReviewers = amountReviewers;
	}

	private boolean scribeIsAuthor = true;;
	private boolean moderatorNotReviewerGroup = true;
	private int amountReviewers;
	private int fillUp = 0;

	/**
	 * This function matches all participants to the reviews
	 * 
	 * @return A review plan with a solution
	 */
	public ReviewPlan MatchReview() {
		// list of reviews with assigned author
		makeList();

		// TODO
		return null;
	}

	/**
	 * This method sorts the participants and then creates one review for every
	 * group
	 */
	private void makeList() {

		ReviewPlan plan = ReviewPlan.getInstance();

		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();
		// sorted list
		Collections.sort(participants);

		Participant currentParticipant = null;
		Participant previousParticipant = null;

		for (int i = 0; i < participants.size(); i++) {
			currentParticipant = participants.get(i);
			// first element
			if (i == 0) {
				Review review = new Review(currentParticipant);
				review.setAuthor(currentParticipant);
				plan.add(review);
			} else {
				previousParticipant = participants.get(i - 1);
				if (currentParticipant.getGroupNumber() != previousParticipant
						.getGroupNumber()) {
					Review review = new Review(currentParticipant);
					review.setAuthor(currentParticipant);
					plan.add(review);
				}
			}

		}

	}

	/**
	 * This method calculates the mount of reviewers based on the amount of
	 * groups
	 */
	private void calculateReviewers() {
		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();
		ReviewPlan plan = ReviewPlan.getInstance();
		List<Review> reviews = plan.getReviews();

		int fillableReviews = 0;
		int participantSize = participants.size();
		int amountGroups = reviews.size();

		// increased so that the first while is correct
		amountReviewers++;

		do {
			amountReviewers--;
			// everybody must participate 2 times -> 2* participant size
			// reviewers + scribe + author = reviewers + 2
			fillableReviews = (2 * participantSize) / (amountReviewers + 2);

		} while (fillableReviews < amountGroups);

		// The amount of reviewers that must be distributed over the reviews as
		// there are not enough for each review
		fillUp = (2 * participantSize) % (amountReviewers + 2);

	}

	@Override
	public String call() throws Exception {
		MatchReview();
		return "Ende";
	}
}
