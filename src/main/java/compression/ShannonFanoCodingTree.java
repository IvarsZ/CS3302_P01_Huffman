package compression;

import java.util.ArrayList;

import org.paukov.combinatorics.ICombinatoricsVector;

/**
 * A coding tree from the Shannon-Fano's algorithm.
 */
public class ShannonFanoCodingTree extends CodingNode {

	private int codeAlphabetSize;

	/**
	 * Construct a Huffman coding tree with the given code alphabet size
	 * of the given source symbols.
	 */
	public ShannonFanoCodingTree(ArrayList<SourceSymbol> source, int codeAlphabetSize) {
		
		this.codeAlphabetSize = codeAlphabetSize;
		
		// Create leaf nodes from the source symbols.
		ArrayList<CodingNode> nodes = new ArrayList<CodingNode>();
		for (SourceSymbol sourceSymbol : source) {
			nodes.add(new CodingLeaf(sourceSymbol.getSymbol(), sourceSymbol.getProbability()));
		}
		
		expandNode(this, nodes);
	}

	private void expandNode(CodingNode node, ArrayList<CodingNode> successors) {

		// If there are too many successors,
		if (successors.size() > codeAlphabetSize) {

			// split them in code alphabet size parts and for each split part,
			ICombinatoricsVector<ICombinatoricsVector<CodingNode>> combination = CodingNode.equalSplit(successors, codeAlphabetSize);
			for (ICombinatoricsVector<CodingNode> splitPart : combination) {

				// if it consists of one node, add it as a leaf to the current node.
				ArrayList<CodingNode> nextSuccessors = new ArrayList<CodingNode>(splitPart.getVector());
				if (nextSuccessors.size() == 1) {
					node.addSuccessor(nextSuccessors.get(0));
				}
				
				// Otherwise,
				else {
					
					// create a new node that is a successor of the current node
					// and expand it with the nodes of the split part.
					CodingNode nextNode = new CodingNode(totalProbability(nextSuccessors));
					node.addSuccessor(nextNode);
					expandNode(nextNode, nextSuccessors);
				}
			}
		}
		
		// Otherwise,
		else if (successors.size() > 1) {
			
			// just add them as successors of this node.
			for (CodingNode successor : successors) {
				node.addSuccessor(successor);
			}
		}
	}
}
