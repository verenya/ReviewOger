/**
 * This class represents a slot a a specific time and knows which rooms are available at that time.
 */

package data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Slot {
	
	public Slot(Date newDate){
		date.setTime(newDate);
	}

	List<Room> roomsAtDate = new ArrayList<Room>();
	GregorianCalendar date = new GregorianCalendar();

	public String getDate(){
		String day = Integer.toString(date.get( Calendar.DAY_OF_MONTH));
		String month = Integer.toString(date.get( Calendar.MONTH));
		String year = Integer.toString(date.get( Calendar.YEAR));
		System.out.println(day + "." + month + "." + year);
		return day + "." + month + "." + year;
	}
}
