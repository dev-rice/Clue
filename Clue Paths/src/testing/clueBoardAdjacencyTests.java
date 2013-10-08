package testing;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class clueBoardAdjacencyTests {
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;
	
	@Before
	public void setUp() throws FileNotFoundException, BadConfigFormatException {
		board = new Board("ClueLayout.csv", "legend.conf", NUM_ROWS, NUM_COLUMNS);
		board.loadConfigFiles();
		board.calcAdjacencies();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(4, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.calcIndex(8, 1));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(2, 2));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.calcIndex(3, 8));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(23, 0));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(2, 4));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(2, 5)));
		
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(board.calcIndex(3, 7));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(3, 6)));
		
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(16, 9));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(17, 9)));
		
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(8, 2));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(7, 2)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(2, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(2, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(2, 4)));
		Assert.assertEquals(4, testList.size());
		
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(17, 2));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 2)));
		Assert.assertEquals(4, testList.size());
		
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(3, 6));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(2, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 6)));
		Assert.assertEquals(4, testList.size());
		
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(7, 2));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 2)));
		Assert.assertEquals(4, testList.size());
		
		// Test beside a door that's not the right direction
		testList = board.getAdjList(board.calcIndex(21, 12));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(22, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 11)));
		// This ensures we haven't included cell (4, 3) which is a doorway
		Assert.assertEquals(3, testList.size());		
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		//
		// Test on left edge of board, just one walkway piece
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(16, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 0)));
		Assert.assertEquals(1, testList.size());
		
		//
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(board.calcIndex(7, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 0)));
		Assert.assertEquals(3, testList.size());

		//
		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.calcIndex(0, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 5)));
		Assert.assertEquals(2, testList.size());

		//
		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(6,5));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 5)));
		Assert.assertEquals(4, testList.size());
		
		//
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(23, 8));
		Assert.assertTrue(testList.contains(board.calcIndex(23, 9)));
		Assert.assertTrue(testList.contains(board.calcIndex(22, 8)));
		Assert.assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(0, 19));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 18)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 19)));
		Assert.assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(board.calcIndex(2, 12));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(2, 11)));
		Assert.assertEquals(3, testList.size());
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.startTargets(0, 5, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 6))));	
		
		board.startTargets(1, 14, 1);
		targets = board.getTargets();
		System.out.println("Targets: " + targets);
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 13))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 15))));			
	}
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.startTargets(9, 6, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 6))));
		
		board.startTargets(6, 23, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 21))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 22))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 23))));			
	}
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.startTargets(16, 11, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 12))));
		
		// Includes a path that doesn't have enough length
		board.startTargets(8, 0, 4);
		targets = board.getTargets();

		Assert.assertEquals(5, targets.size());
		System.out.println(targets);
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 2))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 1))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 3))));
	}	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.startTargets(12, 15, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 15))));	
	}
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.startTargets(6, 19, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 20))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 18))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 18))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 20))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 21))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 19))));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.startTargets(22, 13, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(21, 12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(23, 12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 15))));
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.startTargets(2, 4, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 5))));
		// Take two steps
		board.startTargets(18, 20, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 20))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 21))));
	}

}