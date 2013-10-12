package compression;

import java.util.ArrayList;

import junit.framework.TestCase;

public class ShannonFanoTest extends TestCase {
	
	private InformationSource source;
	
	@Override
	public void setUp() {
		
		ArrayList<SourceSymbol> sourceSymbols = new ArrayList<SourceSymbol>();
		sourceSymbols.add(new SourceSymbol('A', 15/39.0));
		sourceSymbols.add(new SourceSymbol('B', 7/39.0));
		sourceSymbols.add(new SourceSymbol('C', 6/39.0));
		sourceSymbols.add(new SourceSymbol('D', 6/39.0));
		sourceSymbols.add(new SourceSymbol('E', 5/39.0));
		source = new InformationSource(sourceSymbols);
	}
	
	public void testShannonFano() {

		ShannonFanoCodingTree sh = new ShannonFanoCodingTree(source, 2, false);
		Coding coding = new Coding(sh);
		
		// A is 10, B is 00, C is 010, D is 011, E is 11.
		assertEquals("10", coding.encode('A'));
		assertEquals("00", coding.encode('B'));
		assertEquals("010", coding.encode('C'));
		assertEquals("011", coding.encode('D'));
		assertEquals("11", coding.encode('E'));
	}
	
	/**
	 * Test for code alphabet with more than 2 symbols.
	 */
	public void testShannonFanoMultiple() {

		ShannonFanoCodingTree sh = new ShannonFanoCodingTree(source, 3, false);
		Coding coding = new Coding(sh);
		
		// A is 0, B is 20, C is 10, D is 11, E is 21.
		assertEquals("0", coding.encode('A'));
		assertEquals("20", coding.encode('B'));
		assertEquals("10", coding.encode('C'));
		assertEquals("11", coding.encode('D'));
		assertEquals("21", coding.encode('E'));
	}
}
