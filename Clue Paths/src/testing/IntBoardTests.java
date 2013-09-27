
package testing;

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import experiment.IntBoard;

public class IntBoardTests {

	IntBoard board;
	
	@Before
	public void init() {
		board = new IntBoard();
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalcIndex() {
		
		Assert.assertEquals(1, board.calcIndex(0, 1));
		Assert.assertEquals(5, board.calcIndex(1, 1));
		Assert.assertEquals(9, board.calcIndex(2, 1));
		Assert.assertEquals(15, board.calcIndex(3, 3));
	}
	
	@Test
	public void testAdjacency1() {
		LinkedList testList = board.getAdjList(0);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency2() {
		LinkedList testList = board.getAdjList(7);
		Assert.assertTrue(testList.contains(3));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(11));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency3() {
		LinkedList testList = board.getAdjList(8);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(12));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency4() {
		LinkedList testList = board.getAdjList(15);
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(14));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency5() {
		LinkedList testList = board.getAdjList(5);
		Assert.assertTrue(testList.contains(1));
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(9));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency6() {
		LinkedList testList = board.getAdjList(10);
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(14));
		Assert.assertEquals(4, testList.size());
	}
	
	
	@Test
	public void testTargets0_3() {
		board.startTargets(0, 3);
		Set targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
	}
	@Test
	public void testTargets1_2() {
		board.startTargets(1, 2);
		Set targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(3));
	}
	@Test
	public void testTargets12_3() {
		board.startTargets(12, 3);
		Set targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(15));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(13));
	}
	@Test
	public void testTargets15_1() {
		board.startTargets(15, 1);
		Set targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(14));
	}
	@Test
	public void testTargets3_2() {
		board.startTargets(3, 3);
		Set targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(11));
	}
	@Test
	public void testTargets6_1() {
		board.startTargets(6, 1);
		Set targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(10));

	}
	
}
