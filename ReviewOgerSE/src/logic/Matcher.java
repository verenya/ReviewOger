/**
 * This class provides the main logical functions to match participants to reviews
 */
package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;
import data.ReviewPlan;
import data.Room;
import data.RoomNode;
import data.RoomTreeModel;
import data.Slot;
import data.SlotNode;

public class Matcher implements Callable<ArrayList<Review>> {

	public Matcher(String exitSelection, boolean scribeIsAuthor,
			boolean moderatorNotReviewerGroup) {
		this.moderatorNotReviewerGroup = moderatorNotReviewerGroup;
		this.scribeIsAuthor = scribeIsAuthor;
	}

	private boolean scribeIsAuthor ;
	private boolean moderatorNotReviewerGroup ;
	private int amountReviewers;

	private boolean secondTry = false;

	private boolean firstRound = true;

	List<Slot> slots = new ArrayList<Slot>();

	/**
	 * This function matches all participants to the reviews
	 * 
	 * @return A review plan with a solution
	 */
	public ArrayList<Review> MatchReview() {
		// first try so list has to be made manually
		if (firstRound) {
			// list of reviews with assigned author
			makeList();

			amountReviewers = calculateReviewers();

			// make a list of all slots in the tree
			sortSlots();

		} else {

			resetParticipants();

			// list of reviews with assigned author
			makeList();

			// reset slots
			for (Slot s : slots) {
				for (Room r : s.getRooms()) {
					r.setReview(null);

				}
			}
		}

		// fill current slot. As slots are ordered in sortSlots(), the method
		// begins with the largest slot
		for (Slot s : slots) {
			fillSlot(s);
		}

		ReviewPlan plan = ReviewPlan.getInstance();
		List<Review> tempReviews = plan.getTempReviews();

		// every review is matched
		if (tempReviews.isEmpty()) {
			// left participants must be matched
			boolean extraOk = matchExtraReviewers();
			if (!extraOk) {
				firstRound = false;
				return null;
			}

			// shuffle and try again
		} else {
			firstRound = false;
			return null;
		}

		return ReviewPlan.getInstance().getReviews();
	}

	private void resetParticipants() {
		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();
		for (Participant p : participants) {
			p.setNumberOfReviews(0);
			p.resetReviews();
		}

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
		ReviewPlan.getInstance().resetLists();

		for (int i = 0; i < participants.size(); i++) {
			currentParticipant = participants.get(i);
			// first element
			if (i == 0) {
				Review review = new Review(currentParticipant);
				review.setAuthor(currentParticipant);
				currentParticipant.addReview(review);
				currentParticipant.increaseParticipation();
				plan.addTemp(review);
			} else {
				previousParticipant = participants.get(i - 1);
				if (currentParticipant.getGroupNumber() != previousParticipant
						.getGroupNumber()) {
					Review review = new Review(currentParticipant);
					review.setAuthor(currentParticipant);
					review.setAuthor(currentParticipant);
					currentParticipant.increaseParticipation();
					currentParticipant.addReview(review);
					plan.addTemp(review);
				}
			}

		}
	}

	/**
	 * This method calculates the mount of reviewers based on the amount of
	 * groups
	 * 
	 * @return the calculated number of reviewers
	 */
	private int calculateReviewers() {

		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();
		ReviewPlan plan = ReviewPlan.getInstance();
		List<Review> tempReviews = plan.getTempReviews();

		int neededReviewers = 0;
		int participantSize = participants.size();
		int fillableReviews = tempReviews.size();

		if (!scribeIsAuthor) {

			// for reviewers + author + scribe + moderator
			// fillable reviews F must be #groups = #tempReviews
			// #participants P
			// needed: #reviewers R
			// 2 * P / R+3 = F
			// therefore:
			// R = (2P / F) - 2

			neededReviewers = (2 * participantSize / fillableReviews) - 3;

		} else {

			// for reviewers + author + moderator
			// fillable reviews F must be #groups = #tempReviews
			// #participants P
			// needed: #reviewers R
			// 2 * P / R+2 = F
			// therefore:
			// R = (2P / F) - 1

			neededReviewers = (2 * participantSize / fillableReviews) - 2;

		}
		return neededReviewers;

	}

	/**
	 * This method makes a sorted list of all slots
	 */
	@SuppressWarnings("unchecked")
	private void sortSlots() {
		slots.clear();
		DefaultTreeModel model = RoomTreeModel.getInstance();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

		// all slots
		SlotNode currentSlotNode;
		RoomNode currentRoomNode;

		for (Enumeration<SlotNode> enumSlots = root.children(); enumSlots
				.hasMoreElements();) {
			currentSlotNode = (SlotNode) enumSlots.nextElement();
			Slot currentSlot = (Slot) currentSlotNode.getUserObject();

			// all rooms
			for (Enumeration<RoomNode> enumRooms = currentSlotNode.children(); enumRooms
					.hasMoreElements();) {
				currentRoomNode = (RoomNode) enumRooms.nextElement();
				Room currentRoom = (Room) currentRoomNode.getUserObject();
				currentSlot.addRoom(currentRoom);
				currentRoom.setSlot(currentSlot);
			}

			slots.add((Slot) currentSlotNode.getUserObject());

		}

		// sort slots
		Collections.sort(slots);
	}

	/**
	 * This method fills the current slot with reviews
	 * 
	 * @param slot
	 *            the current slot
	 * @param slots
	 */
	private void fillSlot(Slot slot) {

		ReviewPlan plan = ReviewPlan.getInstance();
		ArrayList<Review> tempReviews = plan.getTempReviews();

		// fill all rooms in slot
		for (Room currentRoom : slot.getRooms()) {
			Review currentReview = fillReview(tempReviews, currentRoom, slot);

			// fill was not possible so next slot
			if (currentReview == null) {

				break;
			}
			// The review is finished and can be added to the final plan
			plan.add(currentReview);
			tempReviews.remove(currentReview);
		}

	}

	private Review fillReview(List<Review> tempReviews, Room currentRoom,
			Slot currentSlot) {
		// take first review and after successfully adding, delete it
		if (tempReviews.size() > 0) {

			Participant author = null;
			Review currentReview = null;

			// check for current slot if new author is in it
			for (Review r : tempReviews) {
				author = r.getAuthor();
				if (!Pool.ParticipantInSlot(author, currentSlot)) {
					currentReview = r;
					break;
				} else {
					author = null;
				}
			}

			if (author != null) {
				currentRoom.setReview(currentReview);
				currentReview.setAssignedRoom(currentRoom);

				Pool pool = new Pool(currentSlot);

				ArrayList<Participant> possibleReviewers = pool
						.generatePoolForReviewers(currentReview);

				// number of reviewers + moderator
				if (scribeIsAuthor) {
					// enough participants -> randomly select participants
					if (possibleReviewers.size() >= amountReviewers) {

						for (int i = 0; i < amountReviewers; i++) {
							Participant reviewer = RandomFunctions
									.selectReviewerFromPool(pool);
							reviewer.increaseParticipation();
							reviewer.addReview(currentReview);
							currentReview.addReviewer(reviewer);
							possibleReviewers.remove(reviewer);
						}

						currentReview.setScribe(currentReview.getAuthor());

						ArrayList<Participant> possibleModerators = pool
								.generatePoolForModerator(currentReview,
										moderatorNotReviewerGroup,
										currentReview.getReviewers());

						if (possibleModerators.size() > 0) {

							Participant moderator = RandomFunctions
									.selectModeratorFromPool(pool);
							moderator.increaseParticipation();
							moderator.addReview(currentReview);
							currentReview.setModerator(moderator);

							return currentReview;
						} else {
							// reset
							currentRoom.setReview(null);
							resetReview(currentReview, false, false, true);
							currentReview.setScribe(null);
							return null;
						}

					} else {
						// we change the review one time then we take the
						// next slot
						// if (!secondTry) {
						// // change first and second place
						//
						// // shuffle possible
						// if (tempReviews.size() > 1) {
						//
						// currentReview.setAssignedRoom(null);
						// Review temp = tempReviews.get(0);
						// tempReviews.set(0, tempReviews.get(1));
						// tempReviews.set(1, temp);
						//
						// secondTry = true;
						//
						// return fillReview(tempReviews, currentRoom,
						// currentSlot);
						// }
						//
						// // next slot
						// else {
						// // Reset the connections
						// currentRoom.setReview(null);
						// resetReview(currentReview, false, false,
						// false);
						// return false;
						// }
						//
						// } else {
						// We tried one time to shuffle the first two
						// reviews.
						// Now we take the next slot
						// Reset the connections
						currentRoom.setReview(null);
						resetReview(currentReview, false, false, false);
						return null;
						// }
					}

					// //number of reviewers + moderator + scribe
				} else {
					// enough participants -> randomly select participants
					// and
					// scribe
					if (possibleReviewers.size() >= amountReviewers + 1) {

						for (int i = 0; i < amountReviewers; i++) {
							Participant reviewer = RandomFunctions
									.selectReviewerFromPool(pool);
							reviewer.increaseParticipation();
							reviewer.addReview(currentReview);
							currentReview.addReviewer(reviewer);
							possibleReviewers.remove(reviewer);
						}

						Participant scribe = RandomFunctions
								.selectReviewerFromPool(pool);
						scribe.increaseParticipation();
						scribe.addReview(currentReview);
						currentReview.setScribe(scribe);

						ArrayList<Participant> possibleModerators = pool
								.generatePoolForModerator(currentReview,
										moderatorNotReviewerGroup,
										currentReview.getReviewers());

						if (possibleModerators.size() > 0) {

							Participant moderator = RandomFunctions
									.selectModeratorFromPool(pool);
							moderator.increaseParticipation();
							moderator.addReview(currentReview);
							currentReview.setModerator(moderator);

							return currentReview;
						} else {
							currentRoom.setReview(null);
							resetReview(currentReview, false, true, true);
							return null;
						}
					} else {
						// we change the review one time then we take the
						// next slot
						// if (!secondTry) {
						// // change first and second place
						// // shuffle possible
						// if (tempReviews.size() > 1) {
						//
						// currentReview.setAssignedRoom(null);
						// Review temp = tempReviews.get(0);
						// tempReviews.set(0, tempReviews.get(1));
						// tempReviews.set(1, temp);
						//
						// secondTry = true;
						//
						// return fillReview(tempReviews, currentRoom,
						// currentSlot);
						// }
						//
						// // next slot
						// else {
						// // Reset the connections
						// currentRoom.setReview(null);
						// resetReview(currentReview, false, false,
						// false);
						// return false;
						// }
						//
						// } else {
						// We tried one time to shuffle the first two
						// reviews.
						// Now
						// we take the next slot
						// Reset the connections
						currentRoom.setReview(null);
						resetReview(currentReview, false, false, false);
						return null;
						// }
					}
				}
			}

		}
		return null;
	}

	private void resetReview(Review review, boolean moderatorReset,
			boolean scribeReset, boolean reviewerReset) {
		review.setAssignedRoom(null);
		if (moderatorReset) {
			review.getModerator().decreaseParticipation();
		}
		review.setModerator(null);

		if (scribeReset) {
			review.getScribe().decreaseParticipation();
		}
		review.setScribe(null);

		if (reviewerReset) {
			for (Participant p : review.getReviewers()) {
				p.decreaseParticipation();
			}
		}
		review.getReviewers().clear();
	}

	/**
	 * This method finds all extra reviewers and matches them to the existing
	 * reviews
	 */
	private boolean matchExtraReviewers() {

		// check if there are missing participants left
		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();

		boolean notEnoughParticipations = false;

		for (Participant p : participants) {
			if (p.getNumberOfReviews() < 2) {
				notEnoughParticipations = true;
			} else if (p.getNumberOfReviews() > 2) {
				// error!!
				return false;
			}
		}

		if (notEnoughParticipations) {
			List<Review> reviews = ReviewPlan.getInstance().getReviews();

			for (Review currentReview : reviews) {
				Pool pool = new Pool(currentReview.getAssignedRoom().getSlot());
				ArrayList<Participant> possibleReviewers = pool
						.generatePoolForReviewers(currentReview);
				// reviewers left
				if (possibleReviewers.size() > 0) {

					Participant reviewer = RandomFunctions
							.selectReviewerFromPool(pool);
					currentReview.addReviewer(reviewer);
					reviewer.increaseParticipation();
					reviewer.addReview(currentReview);

					// no reviewers left -> error
				} else {
					return false;
				}

			}

			return true;
		} else {
			// all have been matched efore
			return true;
		}

	}

	public boolean isFirstRound() {
		return firstRound;
	}

	public void setFirstRound(boolean firstRound) {
		this.firstRound = firstRound;
	}

	@Override
	public ArrayList<Review> call() throws Exception {
		ArrayList<Review> reviews = MatchReview();
		return reviews;
	}
}
