/**
 * This class represents a room where a review can take place
 */

package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Room {

	public Room(String roomNumber, boolean hasBeamer, Date beginTime,
			Date endTime) {
		this.roomID = roomNumber;
		this.hasBeamer = hasBeamer;
		this.beginTime.setTime(beginTime);
		this.endTime.setTime(endTime);
	}

	private GregorianCalendar beginTime = new GregorianCalendar();
	private GregorianCalendar endTime = new GregorianCalendar();
	private Review review;

	// Is the unique ID of a location
	private String roomID;

	// To know if a portable beamer has to be provided
	private boolean hasBeamer;

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public boolean isHasBeamer() {
		return hasBeamer;
	}

	public void setHasBeamer(boolean hasBeamer) {
		this.hasBeamer = hasBeamer;
	}

	public Date getBeginTime() {
		return beginTime.getTime();
	}

	public Date getEndTime() {
		return endTime.getTime();
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime.setTime(beginTime);
	}

	public void setEndTime(Date endTime) {
		this.endTime.setTime(endTime);
	}
	
	

	/**
	 * @return the date of the review formated as x.xxx hh:mm to hh:mm
	 */
	public String getFormatedDate() {

		DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		String begin = timeFormatter.format(beginTime.getTime());

		String end = timeFormatter.format(endTime.getTime());

		return roomID + " von " + begin + " Uhr bis " + end + " Uhr";
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}
