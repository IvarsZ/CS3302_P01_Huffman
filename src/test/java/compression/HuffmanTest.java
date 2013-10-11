package compression;

import java.util.ArrayList;

import junit.framework.TestCase;

public class HuffmanTest extends TestCase {

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

	public void testHuffman() {

		HuffmanCodingTree hf = new HuffmanCodingTree(source, 2);
		Coding coding = new Coding(hf);

		// A is 0, B is 111, C is 110, D is 101, E is 100.
		assertEquals("0", coding.encode('A'));
		assertEquals("111", coding.encode('B'));
		assertEquals("110", coding.encode('C'));
		assertEquals("101", coding.encode('D'));
		assertEquals("100", coding.encode('E'));
	}

	public void testMergeCount() {

		// Given 15 nodes and 12 code symbols, one should merge 4.
		assertEquals(4, HuffmanCodingTree.mergeCount(15, 12));
	}

	public void testMergeCount2() {

		// Given 5 nodes and 5 code symbols, one should merge 5.
		assertEquals(5, HuffmanCodingTree.mergeCount(5, 5));
	}
	
	public void testMergeCount3() {

		// Given 5 nodes and 6 code symbols, one should merge 5.
		assertEquals(5, HuffmanCodingTree.mergeCount(5, 6));
	}

	/**
	 * Test where always have to merge code alphabet size nodes.
	 */
	public void testMultipleHuffman() {

		HuffmanCodingTree hf = new HuffmanCodingTree(source, 3);
		Coding coding = new Coding(hf);

		// A is 1, B is 0, C is 22, D is 21, E is 20.
		assertEquals("1", coding.encode('A'));
		assertEquals("0", coding.encode('B'));
		assertEquals("22", coding.encode('C'));
		assertEquals("21", coding.encode('D'));
		assertEquals("20", coding.encode('E'));
	}

	/**
	 * Test where have to merge less nodes at the start.
	 */
	public void testMultipleHuffman2() {

		ArrayList<SourceSymbol> sourceSymbols = new ArrayList<SourceSymbol>();
		sourceSymbols.add(new SourceSymbol('A', 3/10.0));
		sourceSymbols.add(new SourceSymbol('B', 2/10.0));
		sourceSymbols.add(new SourceSymbol('C', 2/10.0));
		sourceSymbols.add(new SourceSymbol('D', 1/10.0));
		sourceSymbols.add(new SourceSymbol('E', 1/10.0));
		sourceSymbols.add(new SourceSymbol('F', 1/10.0));
		source = new InformationSource(sourceSymbols);

		HuffmanCodingTree hf = new HuffmanCodingTree(source, 3);
		Coding coding = new Coding(hf);

		// A is 1, B is 0, C is 22, D is 210, E is 211, F is 20.
		assertEquals("1", coding.encode('A'));
		assertEquals("0", coding.encode('B'));
		assertEquals("22", coding.encode('C'));
		assertEquals("210", coding.encode('D'));
		assertEquals("211", coding.encode('E'));
		assertEquals("20", coding.encode('F'));
	}

	/**
	 * Test Huffman when there are enough code alphabet symbols for each symbol.
	 */
	public void testMultipleHuffman3() {

		ArrayList<SourceSymbol> sourceSymbols = new ArrayList<SourceSymbol>();
		sourceSymbols.add(new SourceSymbol('A', 5/15.0));
		sourceSymbols.add(new SourceSymbol('B', 4/15.0));
		sourceSymbols.add(new SourceSymbol('C', 3/15.0));
		sourceSymbols.add(new SourceSymbol('D', 2/15.0));
		sourceSymbols.add(new SourceSymbol('E', 1/15.0));
		source = new InformationSource(sourceSymbols);

		HuffmanCodingTree hf = new HuffmanCodingTree(source, 6);
		Coding coding = new Coding(hf);

		// Each symbol is one digit.
		assertEquals("3", coding.encode('A'));
		assertEquals("2", coding.encode('B'));
		assertEquals("4", coding.encode('C'));
		assertEquals("1", coding.encode('D'));
		assertEquals("0", coding.encode('E'));
	}
}
