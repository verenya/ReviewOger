/**
 * This class provides the main logical functions to match participants to reviews
 */
package logic;

import java.util.concurrent.Callable;

import data.ReviewPlan;

public class Matcher implements Callable<Integer>{
	/**
	 * This function matches all participants to the reviews
	 * 
	 * @return A review plan with a solution
	 */
	public ReviewPlan MatchReview() {
		// TODO
		return null;
	}


	@Override
	public Integer call() throws Exception {
		int j = 0;
		for( int i = 0; i<100; i++){
			j = j+i;
		}
		return j;
	}
}
