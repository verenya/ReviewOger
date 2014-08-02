/**
 * This class provides the main logical functions to match participants to reviews
 */
package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;
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
			boolean moderatorNotReviewerGroup, int amountReviewers) {
		this.moderatorNotReviewerGroup = moderatorNotReviewerGroup;
		this.scribeIsAuthor = scribeIsAuthor;
		this.amountReviewers = amountReviewers;
	}

	private boolean scribeIsAuthor = true;
	private boolean moderatorNotReviewerGroup = true;
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
			// Problem with the group calculation
			if (amountReviewers == 0) {
				return null;
			}

			// make a list of all slots in the tree
			sortSlots();

		} else {
			// reset rooms
			for (Slot s : slots) {
				for (Room r : s.getRooms()) {
					r.setReview(null);
					;
				}
			}
		}

		// fill current slot, as slots are ordered in sortSlots(), the method
		// begins with the largest slot
		for (Slot s : slots) {
			System.out.println("fillSlots");
			fillSlot(s);
		}

		ReviewPlan plan = ReviewPlan.getInstance();
		List<Review> tempReviews = plan.getTempReviews();

		// every review is matched
		if (tempReviews.isEmpty()) {
			// left participants must be matched
			boolean extraOk = matchExtraReviewers();
			if (!extraOk) {
				System.out.println("error extra matching");
				firstRound = false;
				return null;
			}

			// shuffle
		} else {
			System.out.println("error shuffle");
			firstRound = false;
			return null;
		}

		return ReviewPlan.getInstance().getReviews();
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
				plan.addTemp(review);
			} else {
				previousParticipant = participants.get(i - 1);
				if (currentParticipant.getGroupNumber() != previousParticipant
						.getGroupNumber()) {
					Review review = new Review(currentParticipant);
					review.setAuthor(currentParticipant);
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

		int fillableReviews = 0;
		int participantSize = participants.size();
		int amountGroups = tempReviews.size();

		// needed for first while
		amountReviewers++;
		if (!scribeIsAuthor) {

			do {
				amountReviewers--;
				// everybody must participate 2 times -> 2* participant size
				// reviewers + scribe + author = reviewers + 2
				fillableReviews = (2 * participantSize) / (amountReviewers + 2);
			} while (fillableReviews < amountGroups);

			// The amount of reviewers that must be distributed over the reviews
			// as there are not enough for each review
			// fillUp = (2 * participantSize) % (amountReviewers + 2);

		} else {
			do {
				amountReviewers--;
				// everybody must participate 2 times -> 2* participant size
				// reviewers + author = reviewers + 2
				fillableReviews = (2 * participantSize) / (amountReviewers + 1);
			} while (fillableReviews < amountGroups);

			// The amount of reviewers that must be distributed over the reviews
			// as there are not enough for each review
			// fillUp = (2 * participantSize) % (amountReviewers + 1);
		}

		if (amountReviewers == 0) {
			JOptionPane.showMessageDialog(null,
					"Zu viele Teilnehmer allein in Gruppe. Bitte ändern",
					"Error", JOptionPane.ERROR_MESSAGE);
			return 0;

		}
		return amountReviewers;

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
	 */
	private void fillSlot(Slot slot) {

		ReviewPlan plan = ReviewPlan.getInstance();
		List<Review> tempReviews = plan.getTempReviews();

		// fill all rooms in slot
		for (Room currentRoom : slot.getRooms()) {
			System.out.println("id "+ currentRoom.getRoomID());
			boolean fillOk = fillReview(tempReviews, currentRoom);
			// fill was not possible so next slot
			if (!fillOk) {
				break;
			}
			// The review is finished and can be added to the final plan
			plan.add(tempReviews.get(0));
			tempReviews.remove(0);
		}

	}

	private boolean fillReview(List<Review> tempReviews, Room currentRoom) {
		// take first review and after successfully adding delete it
		if (tempReviews.size() > 0) {
			Review currentReview = tempReviews.get(0);
			currentRoom.setReview(currentReview);
			currentReview.setAssignedRoom(currentRoom);

			Pool pool = new Pool();

			ArrayList<Participant> possibleReviewers = pool
					.generatePoolForReviewers(currentReview);

			// number of reviewers + moderator
			if (scribeIsAuthor) {
				// enough participants -> randomly select participants
				if (possibleReviewers.size() >= amountReviewers + 1) {

					for (int i = 0; i < amountReviewers; i++) {
						Participant reviewer = RandomFunctions
								.selectReviewerFromPool(pool);
						reviewer.increaseParticipation();
						currentReview.addReviewer(reviewer);
					}
					
					currentReview.setScribe(currentReview.getAuthor());

					pool.generatePoolForModerator(currentReview,
							moderatorNotReviewerGroup, possibleReviewers);
					Participant moderator = RandomFunctions
							.selectModeratorFromPool(pool);
					moderator.increaseParticipation();
					currentReview.setModerator(moderator);

					return true;

				} else {
					// we change the review one time then we take the next slot
					if (!secondTry) {
						// change first and second place

						// shuffle possible
						if (tempReviews.size() > 1) {

							currentReview.setAssignedRoom(null);
							Review temp = tempReviews.get(0);
							tempReviews.set(0, tempReviews.get(1));
							tempReviews.set(1, temp);

							secondTry = true;

							return fillReview(tempReviews, currentRoom);
						}

						// next slot
						else {
							// Reset the connections
							currentRoom.setReview(null);
							currentReview.setAssignedRoom(null);
							return false;
						}

					} else {
						// We tried one time to shuffle the first two reviews.
						// Now
						// we take the next slot
						// Reset the connections
						currentRoom.setReview(null);
						currentReview.setAssignedRoom(null);
						return false;
					}
				}

				// //number of reviewers + moderator + scribe
			} else {
				// enough participants -> randomly select participants
				if (possibleReviewers.size() >= amountReviewers + 2) {

					Participant scribe = RandomFunctions
							.selectReviewerFromPool(pool);
					scribe.increaseParticipation();
					currentReview.setScribe(scribe);

					for (int i = 0; i < amountReviewers; i++) {
						Participant reviewer = RandomFunctions
								.selectReviewerFromPool(pool);
						reviewer.increaseParticipation();
						currentReview.addReviewer(reviewer);
					}

					pool.generatePoolForModerator(currentReview,
							moderatorNotReviewerGroup, possibleReviewers);
					Participant moderator = RandomFunctions
							.selectModeratorFromPool(pool);
					moderator.increaseParticipation();
					currentReview.setModerator(moderator);

					return true;
				} else {
					// we change the review one time then we take the next slot
					if (!secondTry) {
						// change first and second place
						// shuffle possible
						if (tempReviews.size() > 1) {

							currentReview.setAssignedRoom(null);
							Review temp = tempReviews.get(0);
							tempReviews.set(0, tempReviews.get(1));
							tempReviews.set(1, temp);

							secondTry = true;

							return fillReview(tempReviews, currentRoom);
						}

						// next slot
						else {
							// Reset the connections
							currentRoom.setReview(null);
							currentReview.setAssignedRoom(null);
							return false;
						}

					} else {
						// We tried one time to shuffle the first two reviews.
						// Now
						// we take the next slot
						// Reset the connections
						currentRoom.setReview(null);
						currentReview.setAssignedRoom(null);
						return false;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This method finds all extra reviewers and matches them to the existing
	 * reviews
	 */
	private boolean matchExtraReviewers() {

		List<Review> reviews = ReviewPlan.getInstance().getReviews();

		Pool pool = new Pool();

		for (Review currentReview : reviews) {
			ArrayList<Participant> possibleReviewers = pool
					.generatePoolForReviewers(currentReview);
			// reviewers left
			if (possibleReviewers.size() > 0) {

				Participant reviewer = RandomFunctions
						.selectReviewerFromPool(pool);
				currentReview.addReviewer(reviewer);

				// no reviewers left -> error
			} else {
				return false;
			}

		}

		return true;

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
