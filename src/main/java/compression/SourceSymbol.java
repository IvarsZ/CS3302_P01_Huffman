package compression;

public class SourceSymbol implements Comparable<SourceSymbol> {

	private char symbol;
	private double probability;

	public SourceSymbol(char symbol, double probability) {
		this.symbol = symbol;
		this.probability = probability;
	}

	public char getSymbol() {
		return symbol;
	}

	public double getProbability() {
		return probability;
	}

	@Override
	public int compareTo(SourceSymbol sourceSymbol) {
		
		if (this.getProbability() == sourceSymbol.getProbability()) {
			return 0;
		} else if (this.getProbability() > sourceSymbol.getProbability()) {
			return 1;
		} else {
			return -1;
		}
	}
}
