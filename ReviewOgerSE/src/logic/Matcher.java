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
			boolean moderatorNotReviewerGroup) {

	}

	/**
	 * This function matches all participants to the reviews
	 * 
	 * @return A review plan with a solution
	 */
	public ReviewPlan MatchReview() {
		makeList();

		// TODO
		return null;
	}

	/**
	 * This method sorts the participants and then creates one review for every
	 * group
	 */
	private void makeList() {

		// TODO NPE

		ReviewPlan plan = ReviewPlan.getInstance();

		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();
		// sorted list
		Collections.sort(participants);
		ListIterator<Participant> iter = participants.listIterator();

		Participant currentParticipant = null;
		Participant previousParticipant = null;

		for (int i = 0; i < participants.size(); i++) {
			currentParticipant = participants.get(i);
			//first element
			if (i == 0) {
				Review review = new Review(currentParticipant);
				plan.add(review);
			}else{
				previousParticipant = participants.get(i-1);
				if(currentParticipant.getGroupNumber() != previousParticipant.getGroupNumber()){
					Review review = new Review(currentParticipant);
					plan.add(review);
				}
			}

		}

		for (Review p : plan.getReviews()) {
			System.out.println(p.getGroupNumber());
		}
	}

	@Override
	public String call() throws Exception {
		MatchReview();
		return "Ende";
	}
}
