package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

	private Map<Integer, LinkedList<Integer>> adjList;

	private Set<BoardCell> targets;
	private boolean visited[] = new boolean[1000];

	public Board(String layout, String legend, int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.layout = layout;
		this.legend = legend;
	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
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

	public BoardCell getCellAt(int index){
		return cells.get(index);
	}

	public void calcAdjacencies() {
		// Iterates through the entire board, creating a master list
		// of adjacencies as it goes
		adjList = new HashMap<Integer, LinkedList<Integer>>();

		for(int col=0; col < numColumns; ++col){
			for(int row=0; row < numRows; ++row){
				LinkedList<Integer> valid_Indices = new LinkedList<Integer>();

				BoardCell cell = getCellAt(calcIndex(row, col));

				if (cell.isDoorway()){
					RoomCell.DoorDirection dir = getRoomCellAt(row, col).getDoorDirection();

					if (dir == RoomCell.DoorDirection.UP){
						//System.out.println("Doorway (" + dir + "): " + row + ", " + col + " Exit: " + (row -1) + ", " + col);
						valid_Indices.add(calcIndex(row-1,col));

					} else if (dir == RoomCell.DoorDirection.DOWN){
						//System.out.println("Doorway (" + dir + "): " + row + ", " + col + " Exit: " + (row +1) + ", " + col);
						valid_Indices.add(calcIndex(row+1,col));

					} else if (dir == RoomCell.DoorDirection.LEFT){
						//System.out.println("Doorway (" + dir + "): " + row + ", " + col + " Exit: " + row + ", " + (col-1));
						valid_Indices.add(calcIndex(row,col-1));

					} else if (dir == RoomCell.DoorDirection.RIGHT){
						//System.out.println("Doorway (" + dir + "): " + row + ", " + col + " Exit: " + row  + ", " + (col+1));
						valid_Indices.add(calcIndex(row,col+1));
					}

				} else if (cell.isWalkway()) {
					LinkedList<Integer> temp_indices = new LinkedList<Integer>();
					if (row > 0){
						temp_indices.add(calcIndex(row -1, col));
					} 
					if (row < numRows -1){
						temp_indices.add(calcIndex(row +1, col));
					}
					if (col > 0){
						temp_indices.add(calcIndex(row, col - 1));
					}
					if (col < numColumns - 1){
						temp_indices.add(calcIndex(row, col + 1));
					}

					for (int i = 0; i < temp_indices.size(); ++i){
						BoardCell temp_cell = getCellAt(temp_indices.get(i));

						if (temp_cell.isWalkway()){
							valid_Indices.add(temp_indices.get(i));
						} else if (temp_cell.isDoorway()){
							RoomCell door = getRoomCellAt(temp_cell.getRow(), temp_cell.getColumn());

							if (door.getDoorDirection() == RoomCell.DoorDirection.UP && row == (door.getRow() - 1)){
								valid_Indices.add(temp_indices.get(i));
							} else if (door.getDoorDirection() == RoomCell.DoorDirection.DOWN && row == (door.getRow() + 1)){
								valid_Indices.add(temp_indices.get(i));
							} else if (door.getDoorDirection() == RoomCell.DoorDirection.LEFT && col == (door.getColumn() - 1)){
								valid_Indices.add(temp_indices.get(i));
							} else if (door.getDoorDirection() == RoomCell.DoorDirection.RIGHT && col == (door.getColumn() + 1)){
								valid_Indices.add(temp_indices.get(i));
							}

						}
					}

				} else if (cell.isRoom()) {
					//System.out.println("room at " + row + ", " + col );
				}
				adjList.put(calcIndex(row,col), valid_Indices);
			}
		}
	}

	public LinkedList<Integer> getAdjList(int index) {
		return adjList.get(index);
	}
	public void startTargets(int row, int column, int numSteps){
		targets = new HashSet<BoardCell>();
		targets.add(getCellAt(calcIndex(row,column)));
		calcTargets(row, column, numSteps);
		targets.remove(getCellAt(calcIndex(row,column)));
	}
	public void calcTargets(int row, int column, int numSteps) {
		int index = calcIndex(row, column);
		LinkedList<Integer> tempList = getAdjList(index);
		//System.out.println(tempList);
		//System.out.println("Index: " + index + " with steps left: " + numSteps);

		if( numSteps > 0 ){
			visited[index] = true;
			for(int i = 0; i < tempList.size(); ++i){
				BoardCell temp_cell = getCellAt(tempList.get(i));
				//System.out.println("Trying " + temp_cell + ", index: " 
						//+ calcIndex(temp_cell.getRow(), temp_cell.getColumn()));

				if (temp_cell.isDoorway()){
					//System.out.println("I found a doorway!");
					targets.add(temp_cell);
				} else if (! visited[tempList.get(i)]){
					calcTargets(temp_cell.getRow(), temp_cell.getColumn(), numSteps-1);
				}
			}	
		} else {
			if( !targets.contains(index) && !visited[index] ){
				//System.out.println("Added " + index);
				targets.add(getCellAt(index));
			}
			return;
		}
		visited[index] = false;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
}
