package compression;

/**
 * A leaf node of a coding tree with a symbol that is encoded by it.
 */
public class CodingLeaf extends CodingNode {
	
	private Character symbol;
	
	/**
	 * Construct a coding node with the given probability 0 and the given symbol.
	 */
	public CodingLeaf(Character symbol, double probability) {
		super(probability);
		
		this.symbol = symbol;
	}
	
	@Override
	public char getSymbol() {
		return symbol;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}
}
