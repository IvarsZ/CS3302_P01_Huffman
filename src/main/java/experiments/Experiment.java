package experiments;

import java.util.ArrayList;
import java.util.Random;

import compression.Coding;
import compression.CodingApp;
import compression.CodingNode;
import compression.HuffmanCodingTree;
import compression.InformationSource;
import compression.ShannonFanoCodingTree;
import compression.SourceSymbol;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class Experiment {
	
	public static StandardDeviation standartDeviation = new StandardDeviation();

	public static void main(String[] args) {

		nonoptimalSFExample();
		
		long[] seeds = {25897325899436302L, 902357289359L, 904538923589L, 47529029834L, 892345894869L, 82340856289L, 35436346237L, 35824572316L,
						9082368943689L, 283467890635L, 890239752348956L, 2357895346048L, 9043673498215L, 236363L, 23463499032L, 89839857L,
						235927560L, 9972357L, 23325L, 89723579235978L, 89723589L, 892348976L, 9823985L, 236823587L, 372375L, 7823685L, 723475897L,
						8235829L, 8923489673L, 789237969L};
		
		for (int i = 4; i <= 7; i++) {
			compareMany(1000, i, 2, seeds[i - 4], false); // No heuristic.
		}
		
		for (int i = 4; i <= 20; i++) {
			compareMany(1000, i, 2, seeds[i - 4], true); // Heuristic.
		}
	}

	/**
	 * Coding comparison where Shannon-Fano isn't optimal.
	 */
	private static void nonoptimalSFExample() {

		ArrayList<SourceSymbol> sourceSymbols = new ArrayList<SourceSymbol>();
		sourceSymbols.add(new SourceSymbol('a', 4));
		sourceSymbols.add(new SourceSymbol('b', 1));
		sourceSymbols.add(new SourceSymbol('c', 1));
		sourceSymbols.add(new SourceSymbol('d', 1));
		sourceSymbols.add(new SourceSymbol('e', 1));
		sourceSymbols.add(new SourceSymbol('f', 1));
		sourceSymbols.add(new SourceSymbol('g', 1));
		InformationSource source = new InformationSource(sourceSymbols);

		compare(source, 2);
	}

	private static void compare(InformationSource source, int codeAlphabetSize) {

		source.print();
		double entropy = source.entropy(codeAlphabetSize); // Adjust entropy to codeAlphabetSize.
		System.out.println("Entropy: " + CodingApp.DF.format(entropy));

		CodingNode codingTreeOfSF = new ShannonFanoCodingTree(source, codeAlphabetSize, false);
		CodingNode codingTreeOfHM = new HuffmanCodingTree(source, codeAlphabetSize);

		// Print Huffman, its average length and the ratio with entropy.
		System.out.println("****************");
		System.out.println("Huffman coding tree:");
		codingTreeOfHM.print();
		System.out.println();

		// Print the encoding.
		System.out.println("Encoding:");
		Coding coding = new Coding(codingTreeOfHM);
		coding.printEncoding();
		System.out.println();

		double averageLengthOfHM = codingTreeOfHM.averageLength();
		System.out.println("Average length: " + CodingApp.DF.format(averageLengthOfHM));
		System.out.println("Average length/Entropy: " + CodingApp.DF.format(averageLengthOfHM/entropy));
		System.out.println();

		// Print Shannon-Fano, its average length and the ratio with entropy.
		System.out.println("****************");
		System.out.println("Shannon-Fano coding tree:");
		codingTreeOfSF.print();
		System.out.println();

		// Print the encoding.
		System.out.println("Encoding:");
		coding = new Coding(codingTreeOfSF);
		coding.printEncoding();
		System.out.println();

		double averageLengthOfSF = codingTreeOfSF.averageLength();
		System.out.println("Average length: " + CodingApp.DF.format(averageLengthOfSF));
		System.out.println("Average length/Entropy: " + CodingApp.DF.format(averageLengthOfSF/entropy));
		System.out.println();

		// Compare them.
		System.out.println("***COMPARISON***");
		System.out.println("Huffman length vs Shannon-Fano length: " +
				CodingApp.DF.format(averageLengthOfHM/averageLengthOfSF));
	}

	private static void compareMany(int numberOfTimes, int sourceSize, int codeAlphabetSize, long seed, boolean useHeuristicForSF) {
		
		System.out.println();
		System.out.println("Comparing Huffman with Shannon-Fano " + numberOfTimes + " times.");
		System.out.println("For random sources with size " + sourceSize + " and code alphabet size " + codeAlphabetSize);
		System.out.println("The used seed is " + seed);

		Random generator = new Random(seed); // Random generator with a seed.
		double totalAverageLengthOfHM = 0;
		double totalAverageLengthOfSF = 0;
		int sFIsWorseCount = 0;
		for (int i = 0; i < numberOfTimes; i++) {

			// Generate a random source of source size.
			ArrayList<SourceSymbol> sourceSymbols = new ArrayList<SourceSymbol>();
			double[] probabilities = new double[numberOfTimes];
			for (int j = 0; j < sourceSize; j++) {
				
				double probability = generator.nextInt(1000);
				sourceSymbols.add(new SourceSymbol((char) ('a' + j), probability));
				probabilities[0] = probability;
			}
			InformationSource source = new InformationSource(sourceSymbols);

			// Calculate average lengths and compare them.
			CodingNode codingTreeOfSF = new ShannonFanoCodingTree(source, codeAlphabetSize, useHeuristicForSF);
			CodingNode codingTreeOfHM = new HuffmanCodingTree(source, codeAlphabetSize);
			double averageLengthOfHM = codingTreeOfHM.averageLength();
			double averageLengthOfSF = codingTreeOfSF.averageLength();

			double ratio = averageLengthOfHM/averageLengthOfSF;
			if (ratio <= 0.85) {
				System.out.println();
				System.out.println(i + ": length ratio (HM/SF) is " + ratio);
				codingTreeOfSF.print();
				System.out.println();
			}

			// Add to the totals.
			totalAverageLengthOfHM += averageLengthOfHM;
			totalAverageLengthOfSF += averageLengthOfSF;

			// Update is worse count.
			if (averageLengthOfHM < averageLengthOfSF) {
				sFIsWorseCount++;
			}
			else if (averageLengthOfHM < averageLengthOfSF) {
				System.out.println("!!!BUG ALERT!!!");
			}
			
			// Caluclate standard deviatiom of probabilities.
			/*
			 * double std = standartDeviation.evaluate(probabilities);
			 * System.out.println(CodingApp.DF.format(std) + " " + CodingApp.DF.format(ratio));
			 */
		}

		System.out.println("The average length ratio (HM/SF) is " + CodingApp.DF.format(totalAverageLengthOfHM/totalAverageLengthOfSF));
		System.out.println("Ratio when Shannon-Fano is worse " + CodingApp.DF.format((sFIsWorseCount * 1.0)/numberOfTimes));
	}

}
