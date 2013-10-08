package compression;

import java.util.ArrayList;

/**
 * A coding tree's node a  with an information source that is
 * encoded by it and its successors.
 */
public class CodingNode implements Comparable<CodingNode>{
	
	private InformationSource source;
	private ArrayList<CodingNode> successors;
	
	/**
	 * Constructs a coding node.
	 */
	public CodingNode() {
		successors = new ArrayList<CodingNode>();
	}
	
	public CodingNode(ArrayList<CodingNode> codingNodes) {
		this();
		
		// TODO ugly.
		ArrayList<InformationSource> sources = new ArrayList<InformationSource>();
		for (CodingNode codingNode : codingNodes) {
			sources.add(codingNode.source);
			this.addSuccessor(codingNode);
		}
		source = new InformationSource(sources, 0);
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
	
	public CodingNode(InformationSource informationSource) {
		this();
		
		this.source = informationSource;
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

	@Override
	public int compareTo(CodingNode codingNode) {
		return source.compareTo(codingNode.source);
	}
}
