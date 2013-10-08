package compression;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A coding tree from Huffman's algorithm.
 */
public class HuffmanCodingTree extends CodingNode {
	
	public HuffmanCodingTree(ArrayList<SourceSymbol> source, int codeAlphabetSize) {
		
		PriorityQueue<CodingNode> pq = new PriorityQueue<CodingNode>();
		for (SourceSymbol sourceSymbol : source) {
			pq.add(new CodingNode(sourceSymbol));
		}
		
		while (pq.size() > 1) {
			
			ArrayList<CodingNode> codingNodes = new ArrayList<CodingNode>();
			for (int i  = 0; i < codeAlphabetSize; i++) {
				codingNodes.add(pq.poll());
			}
			
			CodingNode mergedNode = new CodingNode(codingNodes);
			pq.add(mergedNode);
		}
		
		CodingNode head = pq.poll();
		for (CodingNode successor : head.getSuccessors()) {
			this.addSuccessor(successor);
		}
	}
}
