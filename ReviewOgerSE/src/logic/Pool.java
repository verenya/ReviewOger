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

	static ArrayList<Participant> participantList = new ArrayList<Participant>();

	/**
	 * Generates a pool of possible participants for the given review
	 * 
	 * @param review
	 *            The review the pool is generated for
	 * @return True if enough participants for all free positions are found,
	 *         else false
	 */
	public ArrayList<Participant> generatePoolForReview(Review review) {

		for (Participant p : ParticipantTableModel.getInstance()
				.getParticipants()) {
			// must not be the same group as the author and not too many reviews
			if (!(p.getGroupNumber() == review.getAuthor().getGroupNumber())
					&& (p.getNumberOfReviews() < 2)) {
				participantList.add(p);
			}
		}

		return participantList;
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
