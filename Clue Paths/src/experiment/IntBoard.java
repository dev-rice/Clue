package experiment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private static final int BOARD_WIDTH = 4;
	private static final int BOARD_HEIGHT = 4;
	// Map: each entry contains an Integer that represents the Index, and
	// a Linked List that contains all the possible moves from that index
	private Map<Integer, LinkedList<Integer>> adjList; 
	
	public IntBoard(){
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		calcAdjacencies();
		System.out.println(getAdjList(5));
	}
	
	public void calcAdjacencies(){
		LinkedList<Integer> locAdj;
		for(int col=0; col < BOARD_HEIGHT; ++col){
			for(int row=0; row < BOARD_WIDTH; ++row){
				LinkedList<Integer> valid_Indices = new LinkedList<Integer>();
				if ( row-1>=0 ){
					valid_Indices.add(calcIndex(row-1,col));
				}
				if ( row+1<BOARD_WIDTH ){
					valid_Indices.add(calcIndex(row+1,col));
				}
				if ( col-1>=0 ){
					valid_Indices.add(calcIndex(row,col-1));
				}
				if ( col+1<BOARD_HEIGHT ){
					valid_Indices.add(calcIndex(row,col+1));
				}
				adjList.put(calcIndex(row,col), valid_Indices);
			}
		}
	}
	public void startTargets(int row, int col){
		//int index = calcIndex(row,col);
	}
	
	public Set<Integer> getTargets(){
		return null;
	}
	
	public LinkedList<Integer> getAdjList(int index){
		return adjList.get(index);
	}
	
	public int calcIndex(int row, int col){
		// Takes the column index and adds it to the size of the row multiplied by the row index
		// This assumes we are using the 4x4 grid in the problem description
		// example: at 1, 2 we take 2 + (1*4) = 6
		
		return col + row*BOARD_WIDTH;
	}
}
