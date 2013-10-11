package compression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * An information source consisting of source symbols
 * with the total probability being 1.
 *
 */
public class InformationSource implements Iterable<SourceSymbol> {

	private ArrayList<SourceSymbol> sourceSymbols;
	
	public static InformationSource readInformationSource(Scanner in) {

		// Read the number of symbols to read, at least 1.
		System.out.println("Enter the number of symbols in the information source.");
		int symbolsToRead = CodingApp.readInt(in, 1);

		System.out.println("Enter the information source");
		System.out.println("as a list of symbol (single character) and probability (decimal) pairs");
		System.out.println("separated by spaces, e.g. a 0.3 b 0.3 c 0.4");

		ArrayList<SourceSymbol> source = new ArrayList<SourceSymbol>();

		while (symbolsToRead > 0) {

			// Read a single character.
			String input = in.next();
			while (input.length() != 1) {
				System.out.println("Invalid input - too long symbol, try again.");
				in.nextLine();
				input = in.next();
			}
			char symbol = input.charAt(0);

			// Read the probability, it must be non-negative.
			double probability = CodingApp.readDouble(in, 0);

			source.add(new SourceSymbol(symbol, probability));
			symbolsToRead--;
		}

		in.nextLine();
		return new InformationSource(source);
	}
	
	/**
	 * Construct an information source from the given source symbols.
	 */
	public InformationSource(ArrayList<SourceSymbol> sourceSymbols) {
		this.sourceSymbols = sourceSymbols;
		
		normalize();
	}
	
	/**
	 * Normalize probabilities so that the total is 1.
	 */
	private void normalize() {
		
		// Get the total probability.
		double totalProbability = 0;
		for (SourceSymbol sourceSymbol : this) {
			totalProbability += sourceSymbol.getProbability();
		}
		
		// Divide all probabilities by the total.
		for (SourceSymbol sourceSymbol : this) {
			sourceSymbol.setProbability(sourceSymbol.getProbability()/totalProbability);
		}
	}
	
	/**
	 * @return the entropy of the information source.
	 */
	public double entropy() {
		
		double entropy = 0;
		for (SourceSymbol sourceSymbol : this) {
			double probability = sourceSymbol.getProbability();
			entropy -= probability * Math.log(probability)/Math.log(2);
		}
		
		return entropy;
	}
	
	/**
	 * @return a random message consistent with the information source of the given length.
	 */
	public String generateRandomMessage(int length) {
		
		String message = "";
		for (int i = 0; i < length; i++) {
			double r = Math.random();
			double s = 0;
			for (SourceSymbol sourceSymbol : this) {
				s += sourceSymbol.getProbability();
				if (r <= s) {
					message += sourceSymbol.getSymbol();
				}
			}
		}
		
		return message;
	}
	
	@Override
	public Iterator<SourceSymbol> iterator() {
		return sourceSymbols.iterator();
	}
	
	/**
	 * Print to the system.out
	 */
	public void print() {
		for (SourceSymbol sourceSymbol : this) {
			System.out.print(sourceSymbol.getSymbol() + " " + CodingApp.DF.format(sourceSymbol.getProbability()) + " ");
		}
		System.out.println();
	}
}
