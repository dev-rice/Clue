package clueGame;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	
	private DoorDirection doorDirection;
	private char room;

	public boolean isRoom(){
		return true;
	}
	
	public char getInitial() {
		return room;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public void draw(){

	}
	
}
