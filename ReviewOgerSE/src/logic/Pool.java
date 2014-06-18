/**
 * This class represents one pool for a review. A pool holds all participants which can be added to a review. These participants have no other review in the same time slot, have not the same group as the author of the review and have not participated in the maximum amount of reviews for each participabt.  
 */
package logic;

import java.util.ArrayList;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;
import data.ReviewPlan;

public class Pool {

	/**
	 * The possible participants for a review
	 */
	private ArrayList<Participant> participantList = new ArrayList<Participant>();

	/**
	 * Generates a pool of possible participants for the given review
	 * 
	 * @param review
	 *            The review the pool is generated for
	 * @return True if enough participants for all free positions are found,
	 *         else false
	 */
	protected boolean generatePoolForReview(Review review) {
		for (Participant p : ParticipantTableModel.getParticipants()) {
			// must not be the same group as the author and not too many reviews
			if (!(p.getGroupNumber() == review.getAuthor().getGroupNumber())
					&& (p.getNumberOfReviews() < ReviewPlan.getInstance()
							.getMaxNumberOfReviews())) {
				participantList.add(p);
			}
		}

		// number of reviewers + moderator
		if (review.getAuthorIsScribe()) {
			if (participantList.size() >= review.getNumberOfReviewers() + 1) {
				return true;
			}
			// //number of reviewers + moderator + scribe
		} else {
			if (participantList.size() >= review.getNumberOfReviewers() + 2) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the given participant from the pool
	 * 
	 * @param participantToRemove
	 *            The participant which should be removed from the pool
	 */
	protected void removeParticipant(Participant participantToRemove) {
		participantList.remove(participantToRemove);
	}

	/**
	 * @return the current size of the pool
	 */
	protected int getCurrentPoolsize() {
		return participantList.size();
	}

	/**
	 * @return The participants of the pool
	 */
	protected ArrayList<Participant> getPool() {
		return participantList;
	}
}
