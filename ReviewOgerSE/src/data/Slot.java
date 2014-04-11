/**
 * This class represents a slot a a specific time and knows which rooms are available at that time.
 */

package data;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Slot {
	
	List<Room> roomsAtDate = new ArrayList<Room>();
	GregorianCalendar date;

}
