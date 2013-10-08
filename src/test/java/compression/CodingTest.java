package compression;

import junit.framework.TestCase;

/**
 * Unit test for Coding.
 */
public class CodingTest extends TestCase
{

	public void testFromTree() {

		CodingNode codingTree = new CodingNode();
		
		CodingNode node = new CodingNode();
		node.addSuccessor(new CodingNode(new SourceSymbol('A', 1/6.0)));
		node.addSuccessor(new CodingNode(new SourceSymbol('S', 1/6.0)));
		codingTree.addSuccessor(node);
		
		node = new CodingNode(new SourceSymbol('D', 1/3.0));
		codingTree.addSuccessor(node);
		
		node = new CodingNode(new SourceSymbol('F', 1/3.0));
		codingTree.addSuccessor(node);
		
		Coding coding = new Coding(codingTree);
		
		// A encoded as 00, S as 01, D as 1, F as 2.
		assertEquals("000112", coding.encode("ASDF"));
		assertEquals("ASDF", coding.decode("000112"));
	}

	public void testFromTreeWithOneNode() {

		CodingNode codingTree = new CodingNode(new SourceSymbol('A', 1));
		Coding coding = new Coding(codingTree);
		
		// The only symbol A is encoded and decoded as 0.
		assertEquals("00", coding.encode("AA"));
		assertEquals("AAA", coding.decode("000"));
	}
}
