package compression;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

// TODO comment.
public class CodingApp 
{
	private static final DecimalFormat DF = new DecimalFormat("#.##");
	
	public static void main( String[] args )
	{
		// Read the information source.
		Scanner in = new Scanner(System.in);
		ArrayList<SourceSymbol> source = readInformationSource(in);

		// Choose a number of code symbols. TODO limit?
		System.out.println("Enter the size of the code alphabet.");
		int codeAlphabetSize = readPositiveInt(in);

		// Choose compression algorithm - Shannon-Fano or Huffman.
		System.out.println("Enter S to use Shannon-Fano coding or H for Huffman coding.");
		String algorithmChoice = in.next();
		while (!algorithmChoice.equals("S") && !algorithmChoice.equals("H")) {
			System.out.println("Invalid input. Enter S or H.");
			in.nextLine();
			algorithmChoice = in.next();
		}

		// Compute the coding tree.
		CodingNode codingTree = null;
		if (algorithmChoice.equals("S")) {
			codingTree = new ShannonFanoCodingTree(source, codeAlphabetSize);
		}
		if (algorithmChoice.equals("H")) {
			codingTree = new HuffmanCodingTree(source, codeAlphabetSize);
		}

		// Print it, its average length and the entropy of the source.
		System.out.println("Coding tree:");
		codingTree.print();
		double averageLength = codingTree.averageLength();
		System.out.println("Average length: " + DF.format(averageLength));
		System.out.println("Entropy:");
		double entropy = entropy(source);
		System.out.println(DF.format(entropy)); // TODO fix for non-binary sources.
		System.out.println("Average length/Entropy:");
		System.out.println(DF.format(averageLength/entropy));
		
		// Generate a random message that is consistent with the information source.
		System.out.println("Random message:");
		String message = generateRandomMessage(source, 20);
		System.out.println(message);

		// Encode and decode it.
		Coding coding = new Coding(codingTree);
		System.out.println("Encoded:");
		String encodedMessage = coding.encode(message);
		System.out.println(encodedMessage);
		System.out.println("Decoded:");
		String decodedMessage = coding.decode(encodedMessage);
		System.out.println(decodedMessage);
		
		if (decodedMessage.equals(message)) {
			System.out.println("Decoded correctly.");
		}
		else {
			System.out.println("Decoded incorrectly.");
		}

		in.close(); // TODO where.
	}
	
	public static int readPositiveInt(Scanner in) {
		
		int readInt = 0;
		while (readInt < 1) {

			if (in.hasNextInt()) {
				readInt = in.nextInt();
			}
			
			if (readInt < 1) {
				System.out.println("Invalid input, enter a positive integer.");
				in.nextLine();
			}
		}
		
		in.nextLine();
		return readInt;
	}

	public static ArrayList<SourceSymbol> readInformationSource(Scanner in) {

		// Read the number of symbols to read.
		System.out.println("Enter the number of symbols in the information source.");
		int symbolsToRead = readPositiveInt(in);

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

			// Read a double.
			// TODO ensure is positive.
			while (!in.hasNextDouble()) {
				System.out.println("Invalid input - nondecimal probability, try again.");
				in.nextLine();
			}
			double probability = in.nextDouble();

			source.add(new SourceSymbol(symbol, probability));
			symbolsToRead--;
		}

		in.nextLine();
		return source;
	}
	
	public static double entropy(ArrayList<SourceSymbol> source) {
		
		double entropy = 0;
		for (SourceSymbol sourceSymbol : source) {
			double probability = sourceSymbol.getProbability();
			entropy -= probability * Math.log(probability)/Math.log(2);
		}
		
		return entropy;
	}
	
	public static String generateRandomMessage(ArrayList<SourceSymbol> source, int length) {
		
		String message = "";
		for (int i = 0; i < length; i++) {
			double r = Math.random();
			double s = 0;
			for (SourceSymbol sourceSymbol : source) {
				s += sourceSymbol.getProbability();
				if (r <= s) {
					message += sourceSymbol.getSymbol();
				}
			}
		}
		
		return message;
	}
}
