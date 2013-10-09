package compression;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A coding tree from Huffman's algorithm.
 */
public class HuffmanCodingTree extends CodingNode {
	
	// TODO multiple Huffman.
	
	/**
	 * Construct a Huffman coding tree with the given code alphabet size
	 * of the given source symbols.
	 */
	public HuffmanCodingTree(ArrayList<SourceSymbol> source, int codeAlphabetSize) {
		
		// Add all source symbols as leafs to a priority queue.
		PriorityQueue<CodingNode> nodesToMerge = new PriorityQueue<CodingNode>();
		for (SourceSymbol sourceSymbol : source) {
			nodesToMerge.add(new CodingLeaf(sourceSymbol.getSymbol(), sourceSymbol.getProbability()));
		}
		
		// While there are nodes to merge,
		while (nodesToMerge.size() > 1) {
		
			// merge code alphabet size nodes,
			ArrayList<CodingNode> codingNodes = new ArrayList<CodingNode>();
			for (int i  = 0; i < codeAlphabetSize; i++) {
				codingNodes.add(nodesToMerge.poll());
			}
			
			// and add the merged node back to the priority queue.
			CodingNode mergedNode = new CodingNode(codingNodes);
			nodesToMerge.add(mergedNode);
		}
		
		// The last node is the head of the tree.
		CodingNode head = nodesToMerge.poll();
		for (CodingNode successor : head.getSuccessors()) {
			this.addSuccessor(successor);
		}
	}
}
