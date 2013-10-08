package compression;

import java.util.ArrayList;

/**
 * A coding tree from Huffman's algorithm.
 */
public class HuffmanCodingTree extends CodingNode {
	
	private int codeAlphabetSize;
	
	public HuffmanCodingTree(ArrayList<SourceSymbol> source, int codeAlphabetSize) {
		super(source);
		
		this.codeAlphabetSize = codeAlphabetSize;
	}
}
