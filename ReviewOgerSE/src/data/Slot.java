/**
 * This class represents a slot a a specific time and knows which rooms are available at that time.
 */

package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Slot implements Comparable<Slot> {

	public Slot(Date date, Date beginTime, Date endTime) {
		this.date.setTime(date);
		this.beginTime.setTime(beginTime);
		this.endTime.setTime(endTime);
	}

	List<Room> roomsAtDate = new ArrayList<Room>();
	GregorianCalendar date = new GregorianCalendar();
	GregorianCalendar beginTime = new GregorianCalendar();
	GregorianCalendar endTime = new GregorianCalendar();

	/**
	 * @return the date of the slot formated as dd:MM:yy hh:mm to hh:mm
	 */
	public String getFormatedDate() {

		DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yy");
		String dateString = dateFormatter.format(date.getTime());

		DateFormat beginFormatter = new SimpleDateFormat("HH:mm");
		String begin = beginFormatter.format(beginTime.getTime());

		DateFormat endFormatter = new SimpleDateFormat("HH:mm");
		String end = endFormatter.format(endTime.getTime());
		return dateString + " von " + begin + " Uhr bis " + end + " Uhr";
	}

	public void addRoom(Room room) {
		roomsAtDate.add(room);
	}

	public List<Room> getRooms() {
		return roomsAtDate;
	}

	@Override
	public int compareTo(Slot arg0) {
		// sorted by room size
		if (this.roomsAtDate.size() < arg0.roomsAtDate.size()) {
			return 1;
		}
		if (this.roomsAtDate.size() > arg0.roomsAtDate.size()) {
			return -1;
		}
		return 0;
	}
}
