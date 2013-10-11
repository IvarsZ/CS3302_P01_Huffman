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

		// Add all the nodes as successors.
		successors = new ArrayList<CodingNode>();
		for (CodingNode codingNode : codingNodes) {
			this.addSuccessor(codingNode);
		}
	}

	/**
	 * Add the given coding node as a successor.
	 */
	public void addSuccessor(CodingNode successor) {

		// Update probability.
		probability += successor.getProbability();
		successors.add(successor);
	}

	/**
	 * @return the average length of the coding induced by this coding tree.
	 */
	public double averageLength() {

		// A special case, when there is only symbol,
		if (isLeaf()) {

			// so the average length is 1 * 1 = 1.
			return 1;
		}

		// Otherwise recursively calculate the average length.
		return averageLength(0);
	}

	/**
	 * @param preffixLength - length of this node's encoding in the whole tree.
	 * 
	 * @return the average length of the coding induced by this coding tree.
	 */
	private double averageLength(int preffixLength) {

		// For a leaf node,
		if (isLeaf()) {

			// return its average length.
			return preffixLength  * getProbability();
		}

		// For non-leaf node,
		else {

			// return the sum of its successors' average lengths.
			double averageLength = 0;
			for (CodingNode successor : getSuccessors()) {
				averageLength += successor.averageLength(preffixLength + 1);
			}

			return averageLength;
		}
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

	/**
	 * Prints the coding tree of this node to the system.out.
	 */
	public void print() {
		printIndented(0);
	}
	
	/**
	 * Update the probability with the sum of its successor probabilities.
	 */
	public void updateProbability() {
		probability = 0;
		for (CodingNode successor : getSuccessors()) {
			probability += successor.getProbability();
		}
	}

	/**
	 * Prints the coding tree of this node to the system.out, with
	 * all output indented by indentation spaces.
	 */
	private void printIndented(int indentation) {

		// Indent the output.
		for (int i = 0; i < indentation; i++) {
			System.out.print("  ");
		}

		// Print the node itself,
		if (isLeaf()) {
			System.out.println(getSymbol() + " " + CodingApp.DF.format(getProbability()));
		}
		else {
			System.out.println(CodingApp.DF.format(getProbability()));
		}

		// and then its successors recursively.
		for (CodingNode successor : getSuccessors()) {
			successor.printIndented(indentation + 1);
		}
	}
}
