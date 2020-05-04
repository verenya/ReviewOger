/*******************************************************************************
 * Copyright (c) 2014 Verena Käfer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/gpl.html
 *
 * Contributors:
 * Verena Käfer - initial version
 *******************************************************************************/
package logic;

import java.util.List;
import java.util.Random;

import data.Participant;
import data.Review;
import data.ReviewPlan;

/**
 * This class provides functions for the random selection of participants and
 * the random shuffeling of the current review plan
 */
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

		int n = reviews.size();
		Random random = new Random();
		random.nextInt();

		notWorkingReviewPlan.getTempReviews().clear();

		// select randomly one review from the left reviews and add it to a new
		// list
		for (int i = n; i > 0; i--) {

			Review currentReview = reviews.get(random.nextInt(i));
			currentReview.setAssignedRoom(null);
			currentReview.setModerator(null);
			currentReview.setScribe(null);
			currentReview.getReviewers().clear();
			notWorkingReviewPlan.addTemp(currentReview);
		}

		notWorkingReviewPlan.getReviews().clear();

	}

}
