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
		System.out.println(newDate.toString());
		calendar.setTime(newDate);
		System.out.println(calendar.toString());
	}

	List<Room> roomsAtDate = new ArrayList<Room>();
	GregorianCalendar calendar;

	public String getDate(){
		String day = Integer.toString(calendar.get( Calendar.DAY_OF_MONTH));
		//TODO hier ist das problem mit der NPE!!!
		String month = Integer.toString(calendar.get( Calendar.MONTH));
		String year = Integer.toString(calendar.get( Calendar.YEAR));
		return day + "." + month + "." + year;
	}
}
