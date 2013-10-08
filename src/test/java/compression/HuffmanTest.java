package compression;

import java.util.ArrayList;

import junit.framework.TestCase;

public class HuffmanTest extends TestCase {
	
	private ArrayList<SourceSymbol> source;
	
	@Override
	public void setUp() {
		
		source = new ArrayList<SourceSymbol>();
		source.add(new SourceSymbol('A', 15/41.0));
		source.add(new SourceSymbol('B', 7/41.0));
		source.add(new SourceSymbol('C', 6/41.0));
		source.add(new SourceSymbol('D', 6/41.0));
		source.add(new SourceSymbol('E', 5/41.0));
	}
	
	public void testHuffman() {
		
		HuffmanCodingTree hf = new HuffmanCodingTree(source, 2);
		Coding coding = new Coding(hf);
		
		// A is 0, B is 100, C is 101, D is 110, E is 111.
		assertEquals("0", coding.encode('A'));
		assertEquals("111", coding.encode('B'));
		assertEquals("110", coding.encode('C'));
		assertEquals("101", coding.encode('D'));
		assertEquals("100", coding.encode('E'));
	}
}
