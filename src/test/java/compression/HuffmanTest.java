package compression;

import java.util.ArrayList;

import junit.framework.TestCase;

public class HuffmanTest extends TestCase {

	private ArrayList<SourceSymbol> source;

	@Override
	public void setUp() {

		source = new ArrayList<SourceSymbol>();
		source.add(new SourceSymbol('A', 15/39.0));
		source.add(new SourceSymbol('B', 7/39.0));
		source.add(new SourceSymbol('C', 6/39.0));
		source.add(new SourceSymbol('D', 6/39.0));
		source.add(new SourceSymbol('E', 5/39.0));
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

	public void testMultipleHuffman() {

		HuffmanCodingTree hf = new HuffmanCodingTree(source, 3);
		Coding coding = new Coding(hf);

		// A is 0, B is 100, C is 101, D is 110, E is 111.
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

		source = new ArrayList<SourceSymbol>();
		source.add(new SourceSymbol('A', 3/10.0));
		source.add(new SourceSymbol('B', 2/10.0));
		source.add(new SourceSymbol('C', 2/10.0));
		source.add(new SourceSymbol('D', 1/10.0));
		source.add(new SourceSymbol('E', 1/10.0));
		source.add(new SourceSymbol('F', 1/10.0));

		HuffmanCodingTree hf = new HuffmanCodingTree(source, 3);
		Coding coding = new Coding(hf);

		// A is 0, B is 100, C is 101, D is 110, E is 111.
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

		source = new ArrayList<SourceSymbol>();
		source.add(new SourceSymbol('A', 5/15.0));
		source.add(new SourceSymbol('B', 4/15.0));
		source.add(new SourceSymbol('C', 3/15.0));
		source.add(new SourceSymbol('D', 2/15.0));
		source.add(new SourceSymbol('E', 1/15.0));

		HuffmanCodingTree hf = new HuffmanCodingTree(source, 6);
		Coding coding = new Coding(hf);

		// A is 0, B is 100, C is 101, D is 110, E is 111.
		assertEquals("3", coding.encode('A'));
		assertEquals("2", coding.encode('B'));
		assertEquals("4", coding.encode('C'));
		assertEquals("1", coding.encode('D'));
		assertEquals("0", coding.encode('E'));
	}
}
