package compression;

import java.util.ArrayList;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.paukov.combinatorics.util.ComplexCombinationGenerator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A coding tree's node with a probability and successor nodes.
 */
public class CodingNode implements Comparable<CodingNode>{

	/**
	 * Splits the information source into equal parts as much as possible
	 * based on probability of symbols.
	 * 
	 * @param partsCount - number of parts to split into.
	 * @return a vector of vectors representing the equal parts.
	 */
	public static ICombinatoricsVector<ICombinatoricsVector<CodingNode>> equalSplit(ArrayList<CodingNode> source, int partsCount) {

		// Reduce parts count to the number of symbols, if there is not enough symbols.
		if (partsCount > source.size()) {
			partsCount = source.size();
		}

		double totalProbability = totalProbability(source);
		double target = totalProbability/partsCount; // Split the total probability in partsCount parts. 
		ICombinatoricsVector<ICombinatoricsVector<CodingNode>> minDifferenceSplit = null;
		double minDifference = totalProbability * partsCount; // Is always smaller than parts count * total probability.

		// For each possible split,
		ICombinatoricsVector<CodingNode> vector = Factory.createVector(source);
		Generator<ICombinatoricsVector<CodingNode>> allSplits = new ComplexCombinationGenerator<CodingNode>(vector, partsCount);
		for (ICombinatoricsVector<ICombinatoricsVector<CodingNode>> split : allSplits) {

			// sum its parts' difference from target squares.
			double difference = 0;
			for (ICombinatoricsVector<CodingNode> part : split) {

				double sum = 0;
				for (CodingNode codingNode : part) {
					sum += codingNode.getProbability();
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
	
	/**
	 * @return the sum of probabilities of the given coding nodes.
	 */
	public static double totalProbability(ArrayList<CodingNode> codingNodes) {
		
		double totalProbability = 0;
		for (CodingNode node : codingNodes) {
			totalProbability += node.getProbability();
		}
		
		return totalProbability;
	}
	
	private double probability;
	private ArrayList<CodingNode> successors;
	
	/**
	 * Construct a coding node with a probability 0.
	 */
	public CodingNode() {
		this(0);
	}
	
	/**
	 * Construct a coding node with the given probability 0.
	 */
	public CodingNode(double probability) {
		
		successors = new ArrayList<CodingNode>();
		this.probability = probability; 
	}

	/**
	 * Constructs a coding node with the given nodes as its successors.
	 */
	public CodingNode(ArrayList<CodingNode> codingNodes) {

		// Add all the nodes as successors and sum the probabilities.
		successors = new ArrayList<CodingNode>();
		for (CodingNode codingNode : codingNodes) {
			this.addSuccessor(codingNode);
			probability += codingNode.getProbability();
		}
	}

	/**
	 * Add the given coding node as a successor.
	 */
	public void addSuccessor(CodingNode successor) {
		successors.add(successor);
	}

	@Override
	public int compareTo(CodingNode codingNode) {
		return getProbability().compareTo(codingNode.getProbability());
	}

	/**
	 * @return the probability of this node -
	 * the total sum of probabilities of symbols in this node and its successors.
	 */
	public Double getProbability() {
		return probability;
	}

	/**
	 * @return successor nodes.
	 */
	public ArrayList<CodingNode> getSuccessors() {
		return successors;
	}
	
	/**
	 * @return the symbol encoded by this node.
	 * 
	 * @throws NotImplementedException if its not a leaf node.
	 */
	public char getSymbol() {
		throw new NotImplementedException();
	}

	/**
	 * @return true if this node is a leaf, false otherwise.
	 */
	public boolean isLeaf() {
		return false;
	}
}
