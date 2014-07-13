/**
 * This class represents one pool for a review. A pool holds all participants which can be added to a review. These participants have no other review in the same time slot, have not the same group as the author of the review and have not participated in the maximum amount of reviews for each participabt.  
 */
package logic;

import java.util.ArrayList;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;

public class Pool {

	static ArrayList<Participant> reviewerList = new ArrayList<Participant>();
	static ArrayList<Participant> moderatorList = new ArrayList<Participant>();

	/**
	 * Generates a pool of possible participants for the given review
	 * 
	 * @param review
	 *            The review the pool is generated for
	 * @return True if enough participants for all free positions are found,
	 *         else false
	 */
	public ArrayList<Participant> generatePoolForReviewers(Review review) {

		reviewerList.clear();

		for (Participant p : ParticipantTableModel.getInstance()
				.getParticipants()) {
			// must not be the same group as the author and not too many
			// reviews
			if (!(p.getGroupNumber() == review.getAuthor().getGroupNumber())
					&& (p.getNumberOfReviews() < 2)) {
				reviewerList.add(p);
			}
		}

		return reviewerList;
	}

	public ArrayList<Participant> generatePoolForModerator(Review review,
			boolean moderatorNotReviewerGroup, ArrayList<Participant> reviewers) {
		moderatorList.clear();

		if (moderatorNotReviewerGroup) {

			for (Participant participantToAdd : ParticipantTableModel
					.getInstance().getParticipants()) {
				// must not be the same group as the author and not too many
				// reviews
				if (!(participantToAdd.getGroupNumber() == review.getAuthor()
						.getGroupNumber())
						&& (participantToAdd.getNumberOfReviews() < 2)) {

					// check for the existing reviewers if in same group
					for (Participant reviewer : reviewers) {
						if (reviewer.getGroupNumber() != participantToAdd
								.getGroupNumber()) {
							moderatorList.add(participantToAdd);
						}
					}

				}

			}
			// moderator and reviewers may be in same group
		} else {
			for (Participant p : ParticipantTableModel.getInstance()
					.getParticipants()) {
				// must not be the same group as the author and not too many
				// reviews
				if (!(p.getGroupNumber() == review.getAuthor().getGroupNumber())
						&& (p.getNumberOfReviews() < 2)) {
					moderatorList.add(p);
				}
			}
		}

		return moderatorList;
	}
	
	/**
	 * Removes the given participant from the pool
	 * 
	 * @param participantToRemove
	 *            The participant which should be removed from the pool
	 */
	protected void removeParticipant(Participant participantToRemove) {
		reviewerList.remove(participantToRemove);
	}

	/**
	 * @return the current size of the pool
	 */
	protected int getCurrentReviewerPoolsize() {
		return reviewerList.size();
	}

	/**
	 * @return The participants of the pool
	 */
	protected ArrayList<Participant> getReviewers() {
		return reviewerList;
	}

	/**
	 * @return the current size of the moderator pool
	 */
	protected int getCurrentModeratorPoolsize() {
		return moderatorList.size();
	}

	/**
	 * @return The moderators of the pool
	 */
	protected ArrayList<Participant> getModerators() {
		return moderatorList;
	}
}
