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

		// No merging is necessary if there are more code alphabet symbols.
		if (nodesToMerge.size() > codeAlphabetSize) {
			
			/* 
			 * To make sure that after the merging there are codeAlphabetSize nodes,
			 * "add" k imaginary nodes to the queue, so that
			 * k + n = 1 (mod c - 1),
			 * where c is codeAlphabetSize and n is source size (nodesToMerge.size()).
			 * 
			 * The k imaginary nodes can be ignored when merging,
			 * so actually merge c - k nodes at the first step.
			 * That is c - n - 1 mod (c - 1) nodes.
			 */ 
			mergeNodes(codeAlphabetSize - (nodesToMerge.size() - 1) % (codeAlphabetSize - 1), nodesToMerge);

			// Now merging by c nodes will leave c nodes at the end.
			while (nodesToMerge.size() > codeAlphabetSize) {	
				mergeNodes(codeAlphabetSize, nodesToMerge);
			}
		}

		// Add the remaining c nodes as successors of the head node.
		for (CodingNode successor : nodesToMerge) {
			this.addSuccessor(successor);
		}
	}

	/**
	 * Merges numberOfNodesToMerge nodes from the nodesToMerge as successors of
	 * a single node that is added back to nodesToMerge.
	 */
	private void mergeNodes(int numberOfNodesToMerge, PriorityQueue<CodingNode> nodesToMerge) {
		ArrayList<CodingNode> codingNodes = new ArrayList<CodingNode>();
		for (int i  = 0; i < numberOfNodesToMerge ; i++) {
			codingNodes.add(nodesToMerge.poll());
		}

		nodesToMerge.add(new CodingNode(codingNodes));
	}
}
