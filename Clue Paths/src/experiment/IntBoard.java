package experiment;

import java.util.LinkedList;
import java.util.Set;

public class IntBoard {
	
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
		// Takes the column index and adds it to the size of the row multiplied by the row index
		// This assumes we are using the 4x4 grid in the problem description
		// example: at 1, 2 we take 2 + (1*4) = 6
		
		return col + row*4;
	}
}
