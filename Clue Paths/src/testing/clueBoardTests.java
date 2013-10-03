package testing;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class clueBoardTests {
	
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 23;
	
	@Before
	public void init(){
		board = new Board();
		board.loadConfigFiles("ClueLayout.csv", "legend.conf");
	}
	
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// Test retrieving a few from the hash, including the first
		// and last in the file and a few others
		assertEquals("Cabin", rooms.get('C'));
		assertEquals("Weapons Room", rooms.get('X'));
		assertEquals("Magazine", rooms.get('M'));
		assertEquals("Life Support", rooms.get('L'));
		assertEquals("Walkway", rooms.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction, plus two cells that are not
		// a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			// Test one each RIGHT/LEFT/UP/DOWN
			RoomCell room = board.getRoomCellAt(4, 3);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getRoomCellAt(4, 8);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
			room = board.getRoomCellAt(15, 18);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
			room = board.getRoomCellAt(14, 11);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getRoomCellAt(14, 14);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
			assertFalse(cell.isDoorway());		

		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			int totalCells = board.getNumColumns() * board.getNumRows();
			Assert.assertEquals(506, totalCells);
			for (int i=0; i<totalCells; i++)
			{
				BoardCell cell = board.getCellAt(i);
				if (cell.isDoorway())
					numDoors++;
			}
			Assert.assertEquals(16, numDoors);
		}
		
		@Test
		public void testCalcIndex() {
			// Test each corner of the board
			assertEquals(0, board.calcIndex(0, 0));
			assertEquals(NUM_COLUMNS-1, board.calcIndex(0, NUM_COLUMNS-1));
			assertEquals(483, board.calcIndex(NUM_ROWS-1, 0));
			assertEquals(505, board.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
			// Test a couple others
			assertEquals(24, board.calcIndex(1, 1));
			assertEquals(66, board.calcIndex(2, 20));		
		}
		
		// Test a few room cells to ensure the room initial is
		// correct.
		@Test
		public void testRoomInitials() {
			assertEquals('C', board.getRoomCellAt(0, 0).getInitial());
			assertEquals('E', board.getRoomCellAt(4, 8).getInitial());
			assertEquals('X', board.getRoomCellAt(9, 0).getInitial());
			assertEquals('N', board.getRoomCellAt(21, 22).getInitial());
			assertEquals('H', board.getRoomCellAt(21, 0).getInitial());
		}
		
		// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Board ctor takes config file names
			Board b = new Board();
			// You may change these calls if needed to match your function names
			// My loadConfigFiles has a try/catch, so I can't call it directly to
			// see test throwing the BadConfigFormatException
			b.loadConfigFiles("ClueLayoutBadColumns.csv", "legend.conf");
		}
		
		// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Board ctor takes config file name
			Board b = new Board();
			b.loadConfigFiles("ClueLayoutBadRoom.csv", "legend.conf");
		}
		
		// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Board ctor takes config file name
			Board b = new Board();
			b.loadConfigFiles("ClueLayout.csv", "ClueLegendBadFormat.conf");
		}
		

}
