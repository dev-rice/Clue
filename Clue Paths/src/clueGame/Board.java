package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;

	
	public Board() {
		numRows = 24;
		numColumns = 24;
	}

	public void loadConfigFiles(String layout, String legend) throws FileNotFoundException, BadConfigFormatException{
		rooms = new HashMap();
		cells = new ArrayList<BoardCell>();
		// ClueLayout file
		FileReader layoutFile;
		layoutFile = new FileReader(layout);
		Scanner layoutScanner = new Scanner(layoutFile);
		// Legend file
		FileReader configFile;
		configFile = new FileReader(legend);
		Scanner configScanner = new Scanner(configFile);
		// Parsing the config file
		String curLine;
		String curChar;
		String curStr;
		while(configScanner.hasNext()){
			curLine = configScanner.nextLine();
			curChar = (curLine.split(",")[0]);
			curStr = curLine.split(",")[1];
			rooms.put(curChar.charAt(0), curStr);
		}
		// Parsing the layout file
		String curTag;
		int j = 0;
		while(layoutScanner.hasNext()){
			curTag = layoutScanner.next();
			String[] curTagArr = curTag.split(",");
			for(int i = 0; i < 24; ++i){
				try {
					if( curTagArr[i].equals("W")){
						// It's a walkway
						BoardCell walkway = new WalkwayCell(j,i);
						cells.add(walkway);
					} else {
						if(curTagArr[i].length() == 2){
							// it's a roomcell with a door
							RoomCell.DoorDirection dir = null;
							switch(curTagArr[i].charAt(1)){
							case 'D':
								dir = RoomCell.DoorDirection.DOWN;
								break;
							case 'U':
								dir = RoomCell.DoorDirection.UP;
								break;
							case 'R':
								dir = RoomCell.DoorDirection.RIGHT;
								break;
							case 'L':
								dir = RoomCell.DoorDirection.LEFT;
								break;
							}
							BoardCell room = new RoomCell(j,i,curTagArr[i].charAt(0),dir);
							cells.add(room);
							//cells.add(calcIndex(j,i), room);
						} else if( rooms.containsKey(curTagArr[i].charAt(0))){
							// it's a roomcell without a door
							BoardCell room = new RoomCell(j,i,curTagArr[i].charAt(0),RoomCell.DoorDirection.NONE);
							//cells.add(calcIndex(j,i), room);
							cells.add(room);
						} else {
							throw new BadConfigFormatException();
						}
					}
				} catch (ArrayIndexOutOfBoundsException e){
					throw new BadConfigFormatException();
				}
			}
			j++;
		}
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
	
	public BoardCell getRoomCellAt(int row, int column){
		//return cells.get(calcIndex(row,column));
		return null;
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	public static void main(String[] args) throws BadConfigFormatException {
		Board a = new Board();
		try {
			a.loadConfigFiles("ClueLayout.csv", "legend.conf");
		} catch (FileNotFoundException e) {
			throw new BadConfigFormatException();
		}
		for(int i = 0; i<575; ++i){
			System.out.println(i + ": " + a.getCellAt(i));
		}	
	}
	
	public BoardCell getCellAt(int index){
		return cells.get(index);
	}
	
}
