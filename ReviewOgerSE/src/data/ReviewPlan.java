/**
 * This class contains the results after a successful run of Oger.
 * It contains all informations needed to start the matching.
 * Is filled while the data is read by io.
 * A singleton
 */

package data;

import java.util.ArrayList;

public class ReviewPlan {

	// singleton
	private static ReviewPlan instance = null;

	private ReviewPlan() {

	}

	public static ReviewPlan getInstance() {
		if (instance == null) {
			instance = new ReviewPlan();
		}
		return instance;
	}

	// the final reviews
	private ArrayList<Review> reviews = new ArrayList<Review>();

	// a list of temporary reviews which can be edited without information loss
	private ArrayList<Review> tempReviews = new ArrayList<Review>();

	public void add(Review review) {
		reviews.add(review);
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void addTemp(Review review) {
		tempReviews.add(review);
	}

	public ArrayList<Review> getTempReviews() {
		return tempReviews;
	}

	public void resetLists() {
		reviews.clear();
		tempReviews.clear();
	}
}
