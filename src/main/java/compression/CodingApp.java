package compression;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Entry point of the program. Reads information source and other parameters,
 * then produces a coding tree and a coding for it.
 */
public class CodingApp 
{
	public static final DecimalFormat DF = new DecimalFormat("#.##");
	
	public static void main( String[] args )
	{
		// Read the information source and print it.
		Scanner in = new Scanner(System.in);
		InformationSource source = InformationSource.readInformationSource(in);
		source.print();

		// Choose a number of code symbols, must have at least 2.
		System.out.println("Enter the size of the code alphabet.");
		int codeAlphabetSize = readInt(in, 2);
		in.nextLine();

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
		System.out.println();
		
		in.close();

		// Print it, its average length and the entropy of the source.
		System.out.println("Coding tree:");
		codingTree.print();
		double averageLength = codingTree.averageLength();
		System.out.println("Average length: " + DF.format(averageLength));
		System.out.println("Entropy:");
		double entropy = source.entropy(codeAlphabetSize); // Adjust entropy to codeAlphabetSize.
		System.out.println(DF.format(entropy));
		System.out.println("Average length/Entropy:");
		System.out.println(DF.format(averageLength/entropy));
		System.out.println();
		
		// Print the encoding.
		System.out.println("Encoding:");
		Coding coding = new Coding(codingTree);
		coding.printEncoding();
		System.out.println();
		
		// Generate a random message that is consistent with the information source.
		System.out.println("Random message:");
		String message = source.generateRandomMessage(20);
		System.out.println(message);
		System.out.println();

		// Encode and decode it.
		System.out.println("Encoded:");
		String encodedMessage = coding.encode(message);
		System.out.println(encodedMessage);
		System.out.println("Decoded:");
		String decodedMessage = coding.decode(encodedMessage);
		System.out.println(decodedMessage);
		
		// Check if decoded correctly.
		if (decodedMessage.equals(message)) {
			System.out.println("Decoded correctly.");
		}
		else {
			System.out.println("Decoded incorrectly.");
		}
	}
	
	/**
	 * @return an integer read from the given scanner
	 * that is larger or equal to the given lower bound - min.
	 */
	public static int readInt(Scanner in, int min) {
		
		// Keep reading while smaller than min.
		int readInt = Integer.MIN_VALUE;
		while (readInt < min) {

			// Check if integer.
			if (in.hasNextInt()) {
				readInt = in.nextInt();
			}
			
			if (readInt < min) {
				
				// Print error message on unsuccessful try and clear the input buffer.
				System.out.println("Invalid input, enter an integer larger or equal to " + min + ".");
				in.nextLine();
			}
		}
		
		return readInt;
	}
	
	/**
	 * @return a double read from the given scanner
	 * that is larger or equal to the given lower bound - min.
	 */
	public static double readDouble(Scanner in, double min) {
		
		// Keep reading while smaller than min.
		double readDouble = Double.NEGATIVE_INFINITY;
		while (readDouble < min) {

			// Check if double.
			if (in.hasNextDouble()) {
				readDouble = in.nextDouble();
			}
			
			if (readDouble < min) {
				
				// Print error message on unsuccessful try and clear the input buffer.
				System.out.println("Invalid input, enter a decimal larger or equal to " + min + ".");
				in.nextLine();
			}
		}
		
		return readDouble;
	}
	
	/**
	 * @return a character read from the given scanner that wasn't part of a longer string.
	 */
	public static char readSingleChar(Scanner in) {
		
		String input = in.next();
		while (input.length() != 1) {
			System.out.println("Invalid input - too long symbol, try again.");
			in.nextLine();
			input = in.next();
		}
		return input.charAt(0);
	}
}
