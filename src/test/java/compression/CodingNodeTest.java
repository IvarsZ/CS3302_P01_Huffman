package compression;

import junit.framework.TestCase;

public class CodingNodeTest extends TestCase {

	public void testAverageLength() {
		
		CodingNode codingTree = new CodingNode();
		
		CodingNode node = new CodingNode(1/3.0);
		node.addSuccessor(new CodingLeaf('A', 1/6.0));
		node.addSuccessor(new CodingLeaf('S', 1/6.0));
		codingTree.addSuccessor(node);
		
		node = new CodingLeaf('D', 1/3.0);
		codingTree.addSuccessor(node);
		
		node = new CodingLeaf('F', 1/3.0);
		codingTree.addSuccessor(node);
		
		// The average length should be 2*1/6 + 2*1/6 + 1/3 + 1/3 = 4/3.
		assertEquals(4/3.0, codingTree.averageLength());
	}
	
	public void testAverageLengthWithOne() {
		
		CodingNode codingTree = new CodingLeaf('A', 1);
		
		// The average length should be 1.
		assertEquals(1.0, codingTree.averageLength());
	}
}
