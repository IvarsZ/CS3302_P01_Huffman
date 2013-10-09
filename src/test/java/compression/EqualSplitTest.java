package compression;

import java.util.ArrayList;

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

		ICombinatoricsVector<ICombinatoricsVector<CodingNode>> equalHalves = CodingNode.equalSplit(nodes, 2);

		// First half should contain A and E
		ICombinatoricsVector<CodingNode> equalHalf = equalHalves.getValue(0);
		assertEquals(2, equalHalf.getSize());
		assertTrue(equalHalf.contains(b));
		assertTrue(equalHalf.contains(d));

		// Second half should contain B, C, D.
		equalHalf = equalHalves.getValue(1);
		assertEquals(equalHalf.getSize(), 3);
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
		ICombinatoricsVector<ICombinatoricsVector<CodingNode>> part = CodingNode.equalSplit(source, 2);

		// it should be split in one part containing only the element.
		assertEquals(1, part.getSize());
		assertEquals(1, part.getValue(0).getSize());
		assertEquals(a, part.getValue(0).getValue(0));
	}

	public void testEqualSplitIn3() {

		ICombinatoricsVector<ICombinatoricsVector<CodingNode>> equalThirds = CodingNode.equalSplit(nodes, 3);

		// First third should contain A and B
		ICombinatoricsVector<CodingNode> equalThird = equalThirds.getValue(0);
		assertEquals(2, equalThird.getSize());
		assertTrue(equalThird.contains(a));
		assertTrue(equalThird.contains(b));

		// Second third should contain C and E.
		equalThird = equalThirds.getValue(2);
		assertEquals(equalThird.getSize(), 2);
		assertTrue(equalThird.contains(c));
		assertTrue(equalThird.contains(e));

		// Third third should contain D. 
		equalThird = equalThirds.getValue(1);
		assertEquals(equalThird.getSize(), 1);
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
		
		ICombinatoricsVector<ICombinatoricsVector<CodingNode>> equalThirds = CodingNode.equalSplit(nodes, 3);

		// First third should contain A
		ICombinatoricsVector<CodingNode> equalThird = equalThirds.getValue(0);
		assertEquals(1, equalThird.getSize());
		assertTrue(equalThird.contains(a));

		// Second third should contain B and E.
		equalThird = equalThirds.getValue(2);
		assertEquals(equalThird.getSize(), 2);
		assertTrue(equalThird.contains(b));
		assertTrue(equalThird.contains(e));

		// Third third should contain C and D
		equalThird = equalThirds.getValue(1);
		assertEquals(equalThird.getSize(), 2);
		assertTrue(equalThird.contains(c));
		assertTrue(equalThird.contains(d));
	}
}
