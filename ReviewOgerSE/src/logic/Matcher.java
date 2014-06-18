/**
 * This class provides the main logical functions to match participants to reviews
 */
package logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import data.Participant;
import data.ParticipantTableModel;
import data.Review;
import data.ReviewPlan;
import data.RoomTreeModel;

public class Matcher implements Callable<Integer> {

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

	private void makeList() {
		List<Participant> participants = ParticipantTableModel
				.getParticipants();
		Collections.sort(participants);
		ListIterator<Participant> iter = participants.listIterator();

		while (iter.hasNext()) {
			Participant currentParticipant = iter.next();
			System.out.print(currentParticipant.getGroupNumber());

			// first element
			if (!iter.hasPrevious()) {
				Review review = new Review(currentParticipant);
				ReviewPlan.add(review);

			} else {
				Participant previousParticipant = iter.previous();
				// Reset pointer
				iter.next();
				// not same group
				if (currentParticipant.getGroupNumber() != previousParticipant
						.getGroupNumber()) {
					Review review = new Review(currentParticipant);
					ReviewPlan.add(review);
				}
			}
		}

		for (Review p : ReviewPlan.reviews) {
			System.out.print(p.getGroupNumber());
		}
	}

	@Override
	public Integer call() throws Exception {
		MatchReview();
		return 10;
	}
}
