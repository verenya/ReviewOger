/**
 * This class represents a room where a review can take place
 */

package data;

public class Room {
	
	//Is the unique ID of a location
	private String roomID;
	
	// To know if a portable beamerhas tobe provided
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

}
