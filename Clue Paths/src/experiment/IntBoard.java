package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private static final int BOARD_WIDTH = 4;
	private static final int BOARD_HEIGHT = 4;
	// Map: each entry contains an Integer that represents the Index, and
	// a Linked List that contains all the possible moves from that index
	private Map<Integer, LinkedList<Integer>> adjList; 
	private Set<Integer> targets = new HashSet<Integer>();
	private boolean visited[] = new boolean[BOARD_WIDTH*BOARD_HEIGHT];

	public IntBoard(){
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		calcAdjacencies();
	}

	public void calcAdjacencies(){
		// Iterates through the entire board, creating a master list
		// of adjacencies as it goes
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
	public void startTargets(int index, int numSteps){

		System.out.println("Index:" + index + " Number of Steps left: " + numSteps);
		
		visited[index] = true;
		LinkedList<Integer> tempList = getAdjList(index);
		
		if(numSteps>0){
			for(int i = 0; i < tempList.size(); ++i){
				System.out.println("Index:" + index +" -> " + tempList.get(i));
				if(!visited[tempList.get(i)]){
					startTargets(tempList.get(i),numSteps-1);
				} else {
					System.out.println("Index:" + index + " Did not enter " + tempList.get(i));
				}
			}
			return;
		} else {
			System.out.println("--> I'm Adding index " + index + " to targets.");
			targets.add(index);
			return;
		}
	}

	public Set<Integer> getTargets(){
		System.out.println(targets);
		return targets;
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
