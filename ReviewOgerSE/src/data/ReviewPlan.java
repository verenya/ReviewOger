/**
 * This class contains the results after a successful run of reviewOger.
 * It contains all informations needed to start the matching.
 * Is filled while the data is read by io.
 */

package data;

import java.util.ArrayList;
import java.util.List;

public class ReviewPlan {
	
	public List<Review> reviews = new ArrayList<Review>();
	
	public List<Slot> slots = new ArrayList<Slot>();
	
	public List<Participant> participants = new ArrayList<Participant>();
	

}
