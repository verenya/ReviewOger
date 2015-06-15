/*******************************************************************************
 * Copyright (c) 2014 Verena Käfer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/gpl.html
 *
 * Contributors:
 * Verena Käfer - initial version
 *******************************************************************************/
package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class represents a room where a review can take place
 */
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
	private Slot slot;

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

	public boolean hasBeamer() {
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
	 * @return the date of the review formated as room x.xxx from hh:mm to hh:mm
	 */
	public String getFormatedDate() {

		DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		String begin = timeFormatter.format(beginTime.getTime());

		String end = timeFormatter.format(endTime.getTime());

		return "Raum " + roomID + " von " + begin + " Uhr bis " + end + " Uhr";
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

}
