/**
 * This class provides the main logical functions to match participants to reviews
 */
package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
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

	private boolean secondTry = false;

	List<Slot> slots = new ArrayList<Slot>();

	/**
	 * This function matches all participants to the reviews
	 * 
	 * @return A review plan with a solution
	 */
	public ReviewPlan MatchReview() {

		// list of reviews with assigned author
		makeList();
		
		calculateReviewers();

		// make a list of all slots in the tree
		sortSlots();

		// fill current slot, as slots are ordered in sortSlots(), the method
		// begins with the
		// largest slot
		for (Slot s : slots) {
			fillSlot(s);
		}

		ReviewPlan plan = ReviewPlan.getInstance();
		List<Review> tempReviews = plan.getTempReviews();

		// every review is matched
		if (tempReviews.isEmpty()) {
			System.out.println("extra");
			matchExtraReviewers();

			// shuffle
		} else {
			System.out.println("error");
			RandomFunctions.shuffleReviews(plan);
		}

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

		// TODO
		for (Review r : plan.getTempReviews()) {
			System.out.println("Review " + r.getGroupNumber()
					+ r.getAuthor().getFirstName());
		}

	}

	/**
	 * This method calculates the mount of reviewers based on the amount of
	 * groups
	 */
	private void calculateReviewers() {
		// TODO zu kleine Zahl
		// TODO ergebnis 0
		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();
		ReviewPlan plan = ReviewPlan.getInstance();
		List<Review> tempReviews = plan.getTempReviews();

		int fillableReviews = 0;
		int participantSize = participants.size();
		int amountGroups = tempReviews.size();

		// increased so that the first while is correct
		amountReviewers++;

		do {
			amountReviewers--;
			// everybody must participate 2 times -> 2* participant size
			// reviewers + scribe + author = reviewers + 2
			fillableReviews = (2 * participantSize) / (amountReviewers + 2);
			System.out.println(fillableReviews);
			System.out.println(amountGroups);

		} while (fillableReviews < amountGroups);

		// The amount of reviewers that must be distributed over the reviews as
		// there are not enough for each review
		fillUp = (2 * participantSize) % (amountReviewers + 2);

		// TODO
		System.out.println("calculateReviewers: " + amountReviewers + "  "
				+ fillUp);

	}

	/**
	 * This method makes a sorted list of all slots
	 */
	@SuppressWarnings("unchecked")
	private void sortSlots() {
		slots = new ArrayList<Slot>();
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
				currentSlot.addRoom((Room) currentRoomNode.getUserObject());
			}

			slots.add((Slot) currentSlotNode.getUserObject());

		}

		// sort slots
		Collections.sort(slots);

		// TODO slots vermehren sich
		for (Slot s : slots) {
			for (Room r : s.getRooms())
				System.out.println("sortSlots" + r.getRoomID());
		}
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
			boolean fillOk = fillReview(tempReviews, currentRoom);
			// fill was not possible so next slot
			if (!fillOk) {
				// TODO
				System.out.println("break");
				break;
			}
			// The review is finished and can be added to the final plan
			// TODO
			System.out.println("fillSlot");
			System.out.println(tempReviews.get(0).getAuthor().getFirstName());
			System.out.println(tempReviews.get(0).getModerator());
			System.out.println(tempReviews.get(0).getScribe());

			plan.add(tempReviews.get(0));
			tempReviews.remove(0);
		}

	}

	private boolean fillReview(List<Review> tempReviews, Room currentRoom) {
		// take first review and after successfully adding delete it
		Review currentReview = tempReviews.get(0);
		currentRoom.setReview(currentReview);
		currentReview.setAssignedRoom(currentRoom);

		Pool pool = new Pool();

		ArrayList<Participant> possibleParticipants = pool
				.generatePoolForReview(currentReview);
		// TODO
		System.out.println(pool.getPool().size());
		// number of reviewers + moderator
		if (currentReview.getAuthorIsScribe()) {
			// enough participants -> randomly select participants
			if (possibleParticipants.size() >= currentReview
					.getNumberOfReviewers() + 1) {

				Participant moderator = RandomFunctions
						.selectParticipantFromPool(pool);
				moderator.increaseParticipation();
				currentReview.setModerator(moderator);

				for (int i = 0; i < currentReview.getNumberOfReviewers(); i++) {
					Participant reviewer = RandomFunctions
							.selectParticipantFromPool(pool);
					reviewer.increaseParticipation();
					currentReview.addReviewer(reviewer);
				}

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
					// We tried one time to shuffle the first two reviews. Now
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
			if (possibleParticipants.size() >= currentReview
					.getNumberOfReviewers() + 2) {

				Participant moderator = RandomFunctions
						.selectParticipantFromPool(pool);
				moderator.increaseParticipation();
				currentReview.setModerator(moderator);

				Participant scribe = RandomFunctions
						.selectParticipantFromPool(pool);
				scribe.increaseParticipation();
				currentReview.setScribe(scribe);

				for (int i = 0; i < currentReview.getNumberOfReviewers(); i++) {
					Participant reviewer = RandomFunctions
							.selectParticipantFromPool(pool);
					reviewer.increaseParticipation();
					currentReview.addReviewer(reviewer);
				}

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
					// We tried one time to shuffle the first two reviews. Now
					// we take the next slot
					// Reset the connections
					currentRoom.setReview(null);
					currentReview.setAssignedRoom(null);
					return false;
				}
			}
		}
	}

	/**
	 * This method finds all extra reviewers and matches them to the existing
	 * reviews
	 */
	private void matchExtraReviewers() {
		// TODO
		List<Participant> participants = ParticipantTableModel.getInstance()
				.getParticipants();

		List<Review> reviews = ReviewPlan.getInstance().getReviews();
		int counter = 0;
		for (Participant p : participants) {
			// everybody must participate 2 times
			if (p.getNumberOfReviews() < 2) {
				System.out.println("matchExtra: " + p.getFirstName());
				reviews.get(counter).addReviewer(p);
				counter++;
			}

		}

	}

	@Override
	public String call() throws Exception {
		MatchReview();
		return "Ende";
	}
}
