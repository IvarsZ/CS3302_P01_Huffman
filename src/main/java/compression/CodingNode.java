package compression;

import java.util.ArrayList;

/**
 * A coding tree's node a  with an information source that is
 * encoded by it and its successors.
 */
public class CodingNode {
	
	private InformationSource source;
	private ArrayList<CodingNode> successors;
	
	/**
	 * Constructs a coding node.
	 */
	public CodingNode() {
		successors = new ArrayList<CodingNode>();
	}
	
	/**
	 * Constructs a coding node encoding the given source symbol.
	 */
	public CodingNode(SourceSymbol sourceSymbol) {
		this();
		
		ArrayList<SourceSymbol> source = new ArrayList<SourceSymbol>();
		source.add(sourceSymbol);
		this.source = new InformationSource(source);
	}
	
	/**
	 * Constructs a coding node encoding the given information source.
	 */
	public CodingNode(ArrayList<SourceSymbol> source) {
		this();
		
		this.source = new InformationSource(source);
	}
	
	/**
	 * Add the given coding node as a successor.
	 */
	public void addSuccessor(CodingNode successor) {
		successors.add(successor);
	}
	
	/**
	 * @return successor nodes.
	 */
	public ArrayList<CodingNode> getSuccessors() {
		return successors;
	}
	
	/**
	 * @return the information source that is encoded by this node and its successors.
	 */
	public InformationSource getSource() {
		return source;
	}
	
	/**
	 * @return true if this node is a leaf, false otherwise.
	 */
	public boolean isLeaf() {
		return successors.size() == 0;
	}
}
