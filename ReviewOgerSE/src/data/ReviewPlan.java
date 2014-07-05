/**
 * This class contains the results after a successful run of reviewOger.
 * It contains all informations needed to start the matching.
 * Is filled while the data is read by io.
 * A singleton
 */

package data;

import java.util.ArrayList;
import java.util.List;

public class ReviewPlan {

	private static ReviewPlan instance = null;

	private ReviewPlan() {

	}

	public static ReviewPlan getInstance() {
		if (instance == null) {
			instance = new ReviewPlan();
		}
		return instance;
	}

	private List<Review> reviews = new ArrayList<Review>();
	
	private List<Review> tempReviews = new ArrayList<Review>();

	//private int maxNumberOfReviews = 2;

	public void add(Review review) {
		reviews.add(review);
	}

//	public int getMaxNumberOfReviews() {
//		return maxNumberOfReviews;
//	}
//
//	public void setMaxNumberOfReviews(int maxNumberOfReviews) {
//		this.maxNumberOfReviews = maxNumberOfReviews;
//	}

	public List<Review> getReviews() {
		return reviews;
	}
	
	public void addTemp(Review review) {
		tempReviews.add(review);
	}


	public List<Review> getTempReviews() {
		return tempReviews;
	}
	
	public void resetLists(){
		reviews = new ArrayList<Review>();
		tempReviews = new ArrayList<Review>();
	}
}
