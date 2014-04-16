/**
 * This class provides functions for the random selection of participants and the random shuffeling of the current review plan
 */

package logic;

import java.util.Random;

import data.Participant;
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
	protected Participant selectParticipantFromPool(Pool pool) {
		Random random = new Random();
		// get a random index between 0 and the current pool size -1
		int randomIndex = random.nextInt(pool.getCurrentPoolsize());
		return pool.getPool().get(randomIndex);
	}

	/**
	 * This function must be called if the current order of reviews has no
	 * solution. The function shuffles the reviews randomly.
	 * 
	 * @param workingReviewPlan
	 *            The current list of reviews
	 * @return A shuffled list of reviews
	 */
	protected ReviewPlan shuffleReviews(ReviewPlan workingReviewPlan) {
		return null;
		// TODO
	}

}
