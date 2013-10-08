package compression;

import java.util.ArrayList;

import org.paukov.combinatorics.ICombinatoricsVector;

/**
 * A coding tree from the Shannon-Fano's algorithm.
 */
public class ShannonFanoCodingTree extends CodingNode {

	private int codeAlphabetSize;

	public ShannonFanoCodingTree(ArrayList<SourceSymbol> source, int codeAlphabetSize) {
		super(new InformationSource(source));
		
		this.codeAlphabetSize = codeAlphabetSize;
		expandNode(this);
	}

	private void expandNode(CodingNode node) {

		if (node.getSource().getSize() > codeAlphabetSize) {

			ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> combination = node.getSource().equalSplit(codeAlphabetSize);
			for (ICombinatoricsVector<SourceSymbol> split : combination) {

				// TODO ugly.
				CodingNode successor = new CodingNode(new InformationSource(new ArrayList<SourceSymbol>(split.getVector())));
				node.addSuccessor(successor);
				expandNode(successor);
			}
		}
		else if (node.getSource().getSize() > 1) {
			
			for (SourceSymbol sourceSymbol : node.getSource()) {
				CodingNode successor = new CodingNode(sourceSymbol);
				node.addSuccessor(successor);
			}
		}
	}
}
