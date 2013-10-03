package clueGame;

import java.util.ArrayList;
import java.util.Map;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;

	public void loadConfigFiles(String layout, String legend){

	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public int calcIndex(int row, int column){
		return column + row*numColumns;
	}
	
	public RoomCell getRoomCellAt(int row, int column){
		return new RoomCell();
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	public static void main(String[] args) throws BadConfigFormatException {
		throw new BadConfigFormatException();
	}
	public BoardCell getCellAt(int index){
		return null;
	}
	
}
