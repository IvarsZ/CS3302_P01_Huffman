package compression;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A coding tree from Huffman's algorithm.
 */
public class HuffmanCodingTree extends CodingNode {

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

		// No merging necessary if there are more code alphabet symbols.
		if (nodesToMerge.size() > codeAlphabetSize) {
			
			// TODO explain better, check formula.
			// At the first stage merge k nodes, so that after the merge,
			// the number of the nodes left is congruent to 1 by codeAlphabetSize - 1.
			mergeNodes(codeAlphabetSize - (nodesToMerge.size() - 1) % (codeAlphabetSize - 1), nodesToMerge);

			// While there are nodes to merge, merge them.
			while (nodesToMerge.size() > codeAlphabetSize) {	
				mergeNodes(codeAlphabetSize, nodesToMerge);
			}
		}

		// Add the remaining nodes as successors of the head node.
		for (CodingNode successor : nodesToMerge) {
			this.addSuccessor(successor);
		}
	}

	private void mergeNodes(int numberOfNodesToMerge, PriorityQueue<CodingNode> nodesToMerge) {
		ArrayList<CodingNode> codingNodes = new ArrayList<CodingNode>();
		for (int i  = 0; i < numberOfNodesToMerge ; i++) {
			codingNodes.add(nodesToMerge.poll());
		}

		// Add the merged node back to the priority queue.
		nodesToMerge.add(new CodingNode(codingNodes));
	}
}
