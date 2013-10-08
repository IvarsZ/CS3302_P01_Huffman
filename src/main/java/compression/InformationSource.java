package compression;

import java.util.ArrayList;
import java.util.Iterator;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.paukov.combinatorics.util.ComplexCombinationGenerator;

/**
 * Information source consisting of source symbols.
 *
 */
public class InformationSource implements Iterable <SourceSymbol>{
	
	private ArrayList<SourceSymbol> source;
	
	/**
	 * Construct an information source from the given source symbols.
	 * @param source
	 */
	public InformationSource(ArrayList<SourceSymbol> source) {
		this.source = source;
	}
	
	/**
	 * @return the size of the information source.
	 */
	public int getSize() {
		return source.size();
	}
	
	/**
	 * @return the first symbol.
	 */
	public Character getFirstSymbol() {
		return source.get(0).getSymbol();
	}
	
	@Override
	public Iterator<SourceSymbol> iterator() {
		return source.iterator();
	}

	/**
	 * Splits the information source into equal parts as much as possible
	 * based on probability of symbols.
	 * 
	 * @param partsCount - number of parts to split into.
	 * @return a vector of vectors representing the equal parts.
	 */
	public ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> equalSplit(int partsCount) {
		
		// Reduce parts count to the number of symbols, if there is not enough symbols.
		if (partsCount > source.size()) {
			partsCount = source.size();
		}
		
		double target = 1.0/partsCount; // Is equal split of 1 unit in partsCount parts. 
		ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> minDifferenceSplit = null;
		double minDifference = partsCount; // Is always smaller than parts count.
		
		// For each possible split,
		ICombinatoricsVector<SourceSymbol> vector = Factory.createVector(source);
		Generator<ICombinatoricsVector<SourceSymbol>> allSplits = new ComplexCombinationGenerator<SourceSymbol>(vector, partsCount);
		for (ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> split : allSplits) {

			// sum its parts' difference from target squares.
			double difference = 0;
			for (ICombinatoricsVector<SourceSymbol> part : split) {

				double sum = 0;
				for (SourceSymbol sourceSymbol : part) {
					sum += sourceSymbol.getProbability();
				}
				difference += Math.abs(sum - target) * Math.abs(sum - target);
			}

			// If the total difference is smaller than the current minimal difference, 
			if (difference < minDifference) {
				
				// update it and its split.
				minDifference = difference;
				minDifferenceSplit = split;
			}
		}

		return minDifferenceSplit;
	}
}
