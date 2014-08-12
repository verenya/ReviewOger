/**
 * This class represents one pool for a review. A pool holds all participants which can be added to a review. These participants have no other review in the same time slot, have not the same group as the author of the review and have not participated in the maximum amount of reviews for each participabt.  
 */
package logic;

import java.util.ArrayList;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;
import data.Room;
import data.Slot;

public class Pool {

	static ArrayList<Participant> reviewerList = new ArrayList<Participant>();
	static ArrayList<Participant> moderatorList = new ArrayList<Participant>();
	Slot slot;

	public Pool(Slot currentSlot) {
		slot = currentSlot;
	}

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
			// reviews and not in review yet
			if (!(p.getGroupNumber() == review.getAuthor().getGroupNumber())
					&& (p.getNumberOfReviews() < 2)
					&& !ParticipantInReview(review, p) && !ParticipantInSlot(p)) {
				reviewerList.add(p);
			}
		}

		return reviewerList;
	}

	/**
	 * @param review
	 * @param participant
	 * @return true if participant already added
	 */
	private boolean ParticipantInReview(Review review, Participant participant) {
		if (review.getAuthor() != null) {
			if (review.getAuthor() == participant) {
				return true;
			}
		}
		if (review.getModerator() != null) {
			if (review.getModerator() == participant) {
				return true;
			}
		}

		if (review.getScribe() != null) {
			if (review.getScribe() == participant) {
				return true;
			}
		}

		if (review.getReviewers().size() > 0) {
			if (review.getReviewers().contains(participant)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param review
	 * @param participant
	 * @return true if participant already in slot and therefore at the same
	 *         time
	 */
	private boolean ParticipantInSlot(Participant participant) {
		for (Room r : slot.getRooms()) {
			Review review = r.getReview();

			if (review == null) {
				return false;
			}

			if (review.getAuthor() != null) {
				if (review.getAuthor() == participant) {
					return true;
				}
			}
			if (review.getModerator() != null) {
				if (review.getModerator() == participant) {
					return true;
				}
			}

			if (review.getScribe() != null) {
				if (review.getScribe() == participant) {
					return true;
				}
			}

			if (review.getReviewers().size() > 0) {
				if (review.getReviewers().contains(participant)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @param review
	 *            the current review to fill
	 * @param moderatorNotReviewerGroup
	 * @param reviewers
	 *            the added reviewers in the review
	 * @return
	 */
	public ArrayList<Participant> generatePoolForModerator(Review review,
			boolean moderatorNotReviewerGroup, ArrayList<Participant> reviewers) {
		moderatorList.clear();

		if (moderatorNotReviewerGroup) {

			for (Participant participantToAdd : ParticipantTableModel
					.getInstance().getParticipants()) {
				// must not be the same group as the author and not too many
				// reviews and not already in review
				if (!(participantToAdd.getGroupNumber() == review.getAuthor()
						.getGroupNumber())
						&& (participantToAdd.getNumberOfReviews() < 2)
						&& !ParticipantInReview(review, participantToAdd)
						&& !ParticipantInSlot(participantToAdd)) {

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
						&& (p.getNumberOfReviews() < 2)
						&& !ParticipantInReview(review, p)
						&& !ParticipantInSlot(p)) {
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
