package clueGame;

import clueGame.RoomCell.DoorDirection;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int row, int column) {
		super(row, column);
	}
	public boolean isWalkway() {
		return true;
	}
	public void draw(){
		
	}
}
