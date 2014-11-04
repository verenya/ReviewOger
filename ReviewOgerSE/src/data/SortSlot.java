package data;

import java.util.Date;
import java.util.List;

public class SortSlot extends Slot {

	public SortSlot(Date date, Date beginTime, Date endTime) {
		super(date, beginTime, endTime);
	}

	@Override
	public int compareTo(Slot arg0) {
		// sorted by date
		// this date before arg0 date
		if (this.getDate().before(arg0.getDate())) {
			return -1;

			// arg0 date before this date
		} else if (this.getDate().after(arg0.getDate())) {
			return 1;
		}

		// same date, different time
		if (this.getDate().equals(arg0.getDate())
				&& this.getBeginTime().before(arg0.getBeginTime())) {
			return -1;
		} else if (this.getDate().equals(arg0.getDate())
				&& this.getBeginTime().after(arg0.getBeginTime())) {
			return 1;
		}
		// same
		return 0;
	}
	
	public void setRooms(List<Room> rooms){
		this.roomsAtDate = rooms;
	}

}
