package compression;

/**
 * A symbol with a probability of appearance.
 */
public class SourceSymbol {

	private char symbol;
	private double probability;

	/**
	 * Construct a source symbol with the given symbol and probability.
	 */
	public SourceSymbol(char symbol, double probability) {
		this.symbol = symbol;
		this.probability = probability;
	}

	/**
	 * @return the symbol.
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * @return the probability.
	 */
	public double getProbability() {
		return probability;
	}
	
	/**
	 * Setter for probability.
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (object instanceof SourceSymbol) {
			
			// Equality by the symbol, ignoring probability.
			SourceSymbol sourceSymbol = (SourceSymbol) object;
			return symbol == sourceSymbol.symbol;
		}
		
		return false;
	}
}
