/**
 * This class provides functions for the random selection of participants and the random shuffeling of the current review plan
 */

package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import data.Participant;
import data.Review;
import data.ReviewPlan;

public class RandomFunctions {

	/**
	 * This function randomly selects a participant from the given pool and
	 * returns it.
	 * 
	 * @param pool
	 *            The pool with the participants
	 * @return A randomly selected participant
	 */
	public static Participant selectReviewerFromPool(Pool pool) {
		Random random = new Random();
		// get a random index between 0 and the current pool size -1
		int randomIndex = random.nextInt(pool.getCurrentReviewerPoolsize());
		Participant reviewer = pool.getReviewers().get(randomIndex);
		pool.removeParticipant(reviewer);
		return reviewer;
	}

	/**
	 * This function randomly selects a moderator from the given pool and
	 * returns it.
	 * 
	 * @param pool
	 *            The pool with the moderators
	 * @return A randomly selected moderator
	 */
	public static Participant selectModeratorFromPool(Pool pool) {
		Random random = new Random();
		// get a random index between 0 and the current pool size -1
		int randomIndex = random.nextInt(pool.getCurrentModeratorPoolsize());
		return pool.getModerators().get(randomIndex);
	}

	/**
	 * This function must be called if the current order of reviews has no
	 * solution. The function shuffles the reviews randomly.
	 * 
	 * @param notWorkingReviewPlan
	 *            The current list of reviews
	 * @return A shuffled list of reviews
	 */
	public static void shuffleReviews(ReviewPlan notWorkingReviewPlan) {
		List<Review> reviews = notWorkingReviewPlan.getReviews();

		Collections.shuffle(reviews);

		notWorkingReviewPlan.getTempReviews().clear();

		// reset all, add to temporary plan and clear current reviews
		
		//TODO currentModificationException
		for (Review r : reviews) {
			r.setAssignedRoom(null);
			r.setDateAndTime(null);
			r.setModerator(null);
			r.setScribe(null);
			r.getReviewers().clear();

			notWorkingReviewPlan.addTemp(r);
			notWorkingReviewPlan.getReviews().clear();
		}

		// TODO
	}

}
