package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;

	private String layout;
	private String legend;


	public Board(String layout, String legend, int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.layout = layout;
		this.legend = legend;
	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		// TODO Auto-generated method stub
		rooms = new HashMap();
		FileReader configFile;
		configFile = new FileReader(legend);
		Scanner configScanner = new Scanner(configFile);
		String curLine;
		String curChar;
		String curStr;
		while(configScanner.hasNext()){
			curLine = configScanner.nextLine();
			
			int counter = 0;
			for( int i=0; i<curLine.length(); i++ ) {
			    if( curLine.charAt(i) == ',' ) {
			        counter++;
			    } 
			}
			
			if (counter != 1){
				throw new BadConfigFormatException();
			} else {

				curChar = curLine.split(",")[0];
				curStr = curLine.split(", ")[1];
				rooms.put(curChar.charAt(0), curStr);
			}
		}
	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		// TODO Auto-generated method stub
		cells = new ArrayList<BoardCell>();
		FileReader layoutFile;
		layoutFile = new FileReader(layout);
		Scanner layoutScanner = new Scanner(layoutFile);
		// Parsing the layout file
		String curTag;
		int j = 0;
		while(layoutScanner.hasNext()){
			curTag = layoutScanner.next();

			String[] curTagArr = curTag.split(",");
			if (curTagArr.length != numColumns){
				throw new BadConfigFormatException();
			} else {
				for(int i = 0; i < numColumns; ++i){
					if( curTagArr[i].equals("W")){
						// It's a walkway
						BoardCell walkway = new WalkwayCell(j,i);
						cells.add(walkway);
					} else {
						if(curTagArr[i].length() == 2){
							// it's a roomcell with a door
							RoomCell.DoorDirection dir = RoomCell.DoorDirection.NONE;
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
				}
			}
			j++;
		}
	}

	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException{
		loadRoomConfig();
		loadBoardConfig();
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
		return (RoomCell) cells.get(calcIndex(row, column));
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public static void main(String[] args) throws BadConfigFormatException {
		
	}

	public BoardCell getCellAt(int index){
		return cells.get(index);
	}

	public void calcAdjacencies() {
		// TODO implement this part
	}

	public LinkedList<Integer> getAdjList(int calcIndex) {
		// TODO  implement this part
		return null;
	}

	public void calcTargets(int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}

	public Set<BoardCell> getTargets() {
		// TODO  implement this part
		return null;
	}

}
