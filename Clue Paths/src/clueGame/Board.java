package clueGame;

import java.util.ArrayList;
import java.util.Map;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;

	public void loadConfigFiles(){

	}
	public int calcIndex(int row, int column){
		return column + row*numColumns;
	}
	public RoomCell getRoomCellAt(int row, int column){
		return new RoomCell();
	}
}
