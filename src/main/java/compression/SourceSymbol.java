package compression;

/**
 * A symbol with a probability of appearance.
 */
public class SourceSymbol implements Comparable<SourceSymbol> {

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

	@Override
	public int compareTo(SourceSymbol sourceSymbol) {
		
		// Compare by probability.
		return ((Double) probability).compareTo(sourceSymbol.getProbability());
	}
}
