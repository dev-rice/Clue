package experiment;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	// Map: each entry contains an Integer that represents the Index, and
	// a Linked List that contains all the possible moves from that index
	private Map<Integer, LinkedList<Integer>> adjList; 
	
	public IntBoard(){
		
	}
	
	public void calcAdjacencies(){
		
	}
	public void startTargets(int index, int numSteps){
		
	}
	
	public Set<Integer> getTargets(){
		return null;
	}
	
	public LinkedList<Integer> getAdjList(int index){
		return null;
	}
	
	public int calcIndex(int row, int col){
		return 0;
	}
}