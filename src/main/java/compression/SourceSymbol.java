package compression;

public class SourceSymbol {

	char symbol;
	double probability;

	public char getSymbol() {
		return symbol;
	}

	public double getProbability() {
		return probability;
	}

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
