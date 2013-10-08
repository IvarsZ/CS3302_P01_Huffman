package compression;

import java.util.ArrayList;

import junit.framework.TestCase;

public class ShannonFanoTest extends TestCase {
	
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
	
	public void testShannonFano() {

		ShannonFanoCodingTree sh = new ShannonFanoCodingTree(source, 2);
		Coding coding = new Coding(sh);
		
		// A is 10, B is 00, C is 010, D is 011, E is 11.
		assertEquals("10", coding.encode('A'));
		assertEquals("00", coding.encode('B'));
		assertEquals("010", coding.encode('C'));
		assertEquals("011", coding.encode('D'));
		assertEquals("11", coding.encode('E'));
		assertEquals("100001001111", coding.encode("ABCDE"));
	}
	
	/**
	 * Test for code alphabet with more than 2 symbols.
	 */
	public void testShannonFanoMultiple() {

		ShannonFanoCodingTree sh = new ShannonFanoCodingTree(source, 3);
		Coding coding = new Coding(sh);
		
		// A is 0, B is 20, C is 10, D is 11, E is 21.
		assertEquals("0", coding.encode('A'));
		assertEquals("20", coding.encode('B'));
		assertEquals("10", coding.encode('C'));
		assertEquals("11", coding.encode('D'));
		assertEquals("21", coding.encode('E'));
		assertEquals("020101121", coding.encode("ABCDE"));
	}
}
