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
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class represents a slot at a specific time and knows which rooms are
 * available at that time.
 */
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

	public Date getDate() {
		return date.getTime();
	}

	public Date getBeginTime() {
		return beginTime.getTime();
	}

	public Date getEndTime() {
		return endTime.getTime();
	}

	public void setDate(Date date) {
		this.date.setTime(date);
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime.setTime(beginTime);
	}

	public void setEndTime(Date endTime) {
		this.endTime.setTime(endTime);
	}
}
