/**
 * This class represents a slot a a specific time and knows which rooms are available at that time.
 */

package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Slot {
	
	public Slot(Date date, Date beginTime, Date endTime){
		this.date.setTime(date);
		this.beginTime.setTime(beginTime);
		this.endTime.setTime(endTime);
	}

	List<Room> roomsAtDate = new ArrayList<Room>();
	GregorianCalendar date = new GregorianCalendar();
	GregorianCalendar beginTime = new GregorianCalendar();
	GregorianCalendar endTime = new GregorianCalendar();

	/**
	 * @return the date of the review formated as dd:mm:yy
	 */
	public String getFormatedDate(){
		
		DateFormat dateFormatter = new SimpleDateFormat("dd:mm:yy");
		String completeDate = dateFormatter.format(date.getTime());
		
		DateFormat beginFormatter = new SimpleDateFormat("HH:mm");
		String begin = beginFormatter.format(beginTime.getTime());
		
		DateFormat endFormatter = new SimpleDateFormat("HH:mm");
		String end = endFormatter.format(endTime.getTime());
		return completeDate + " von " + begin + " Uhr bis " + end + " Uhr";
	}
}
