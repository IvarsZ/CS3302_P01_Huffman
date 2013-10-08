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
public class InformationSource implements Iterable <SourceSymbol>, Comparable<InformationSource>{
	
	private static final double NO_TOTAL_PROBABILITY = -1;
	
	private ArrayList<SourceSymbol> source;
	private double totalProbability;
	
	/**
	 * Construct an information source from the given source symbols.
	 * @param source
	 */
	public InformationSource(ArrayList<SourceSymbol> source) {
		this.source = source;
		totalProbability = NO_TOTAL_PROBABILITY;
	}
	
	// TODO a is useless, refactor the clash with constructor above.
	public InformationSource(ArrayList<InformationSource> informationSources, int a) {
		
		source = new ArrayList<SourceSymbol>();
		
		totalProbability = 0;
		for (InformationSource informationSource : informationSources) {
			for (SourceSymbol sourceSymbol : informationSource) {
				source.add(sourceSymbol);
			}
			
			totalProbability += informationSource.getTotalProbability();
		}
	}
	
	/**
	 * @return the size of the information source.
	 */
	public int getSize() {
		return source.size();
	}
	
	public double getTotalProbability() {
		
		// If the total probability has not been calculated,
		if (totalProbability == NO_TOTAL_PROBABILITY) {
			
			// sum all source symbol probabilities to get it.
			totalProbability = 0;
			for (SourceSymbol sourceSymbol : this) {
				totalProbability += sourceSymbol.getProbability();
			}
		}
		
		return totalProbability;
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

	@Override
	public int compareTo(InformationSource informationSource) {
		
		if (this.getTotalProbability() == informationSource.getTotalProbability()) {
			return 0;
		} else if (this.getTotalProbability() > informationSource.getTotalProbability()) {
			return 1;
		} else {
			return -1;
		}
	}
}
