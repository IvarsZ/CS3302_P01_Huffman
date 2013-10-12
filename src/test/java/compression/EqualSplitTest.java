package compression;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.paukov.combinatorics.ICombinatoricsVector;

/**
 * Unit test for splitting list of coding nodes into equal parts.
 */
public class EqualSplitTest extends TestCase
{

	// List of nodes to split.
	private ArrayList<CodingNode> nodes;
	
	// Nodes in the list.
	private  CodingNode a;
	private  CodingNode b;
	private  CodingNode c;
	private  CodingNode d;
	private  CodingNode e;

	@Override
	public void setUp() {
		
		a = new CodingNode(7/32.0);
		b = new CodingNode(4/32.0);
		c = new CodingNode(2/32.0);
		d = new CodingNode(11/32.0);
		e = new CodingNode(8/32.0);
		
		nodes = new ArrayList<CodingNode>();
		nodes.add(a);
		nodes.add(b);
		nodes.add(c);
		nodes.add(d);
		nodes.add(e);
	}

	public void testEqualSplitIn2()
	{

		List<List<CodingNode>> equalHalves = CodingNode.equalSplit(nodes, 2);

		// First half should contain B and D.
		List<CodingNode> equalHalf = equalHalves.get(0);
		assertEquals(2, equalHalf.size());
		assertTrue(equalHalf.contains(b));
		assertTrue(equalHalf.contains(d));

		// Second half should contain A, C and E.
		equalHalf = equalHalves.get(1);
		assertEquals(equalHalf.size(), 3);
		assertTrue(equalHalf.contains(a));
		assertTrue(equalHalf.contains(c));
		assertTrue(equalHalf.contains(e));
	}

	public void testEqualSplit1In2()
	{
		// When splitting a list with one element,
		ArrayList<CodingNode> source = new ArrayList<CodingNode>();
		CodingNode a = new CodingNode(1);
		source.add(a);
		List<List<CodingNode>> part = CodingNode.equalSplit(source, 2);

		// it should be split in one part containing only the element.
		assertEquals(1, part.size());
		assertEquals(1, part.get(0).size());
		assertEquals(a, part.get(0).get(0));
	}

	public void testEqualSplitIn3() {

		List<List<CodingNode>> equalThirds = CodingNode.equalSplit(nodes, 3);

		// First third should contain A and B
		List<CodingNode> equalThird = equalThirds.get(0);
		assertEquals(2, equalThird.size());
		assertTrue(equalThird.contains(a));
		assertTrue(equalThird.contains(b));

		// Second third should contain C and E.
		equalThird = equalThirds.get(2);
		assertEquals(equalThird.size(), 2);
		assertTrue(equalThird.contains(c));
		assertTrue(equalThird.contains(e));

		// Third third should contain D. 
		equalThird = equalThirds.get(1);
		assertEquals(equalThird.size(), 1);
		assertTrue(equalThird.contains(d));
	}
	
	public void testEqualSplitIn3_2() {
		
		a = new CodingNode(15/39.0);
		b = new CodingNode(7/39.0);
		c = new CodingNode(6/39.0);
		d = new CodingNode(6/39.0);
		e = new CodingNode(5/39.0);
		
		nodes = new ArrayList<CodingNode>();
		nodes.add(a);
		nodes.add(b);
		nodes.add(c);
		nodes.add(d);
		nodes.add(e);
		
		List<List<CodingNode>> equalThirds = CodingNode.equalSplit(nodes, 3);

		// First third should contain A
		List<CodingNode> equalThird = equalThirds.get(0);
		assertEquals(1, equalThird.size());
		assertTrue(equalThird.contains(a));

		// Second third should contain B and E.
		equalThird = equalThirds.get(2);
		assertEquals(equalThird.size(), 2);
		assertTrue(equalThird.contains(b));
		assertTrue(equalThird.contains(e));

		// Third third should contain C and D
		equalThird = equalThirds.get(1);
		assertEquals(equalThird.size(), 2);
		assertTrue(equalThird.contains(c));
		assertTrue(equalThird.contains(d));
	}
	
	public void testEqualHeuristicSplitIn2()
	{

		List<List<CodingNode>> equalHalves = CodingNode.equalHeuresticSplit(nodes, 2);

		// First half should contain D
		List<CodingNode> equalHalf = equalHalves.get(0);
		assertEquals(1, equalHalf.size());
		
		assertTrue(equalHalf.contains(d));

		// Second half should contain B, C, D, E.
		equalHalf = equalHalves.get(1);
		assertEquals(equalHalf.size(), 4);
		assertTrue(equalHalf.contains(a));
		assertTrue(equalHalf.contains(c));
		assertTrue(equalHalf.contains(e));
		assertTrue(equalHalf.contains(b));
	}
	
	public void testEqualHeuristicSplitIn3() {

		List<List<CodingNode>> equalThirds = CodingNode.equalHeuresticSplit(nodes, 3);

		// First third should contain D
		List<CodingNode> equalThird = equalThirds.get(0);
		assertEquals(1, equalThird.size());
		assertTrue(equalThird.contains(d));

		// Second third should contain E.
		equalThird = equalThirds.get(1);
		assertEquals(1, equalThird.size());
		assertTrue(equalThird.contains(e));

		// Third third should contain A, B and C. 
		equalThird = equalThirds.get(2);
		assertEquals(3, equalThird.size());
		assertTrue(equalThird.contains(a));
		assertTrue(equalThird.contains(b));
		assertTrue(equalThird.contains(c));
	}
}
