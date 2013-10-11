package compression;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A coding function mapping information source's symbols to code alphabet symbols.
 * it encodes and decodes messages.
 */
public class Coding {

	private CodingNode codingTree;
	private Map<Character, String> encoding;

	/**
	 * Constructs a coding from the given coding tree.
	 */
	public Coding(CodingNode codingTree) {

		encoding = new HashMap<Character, String>();
		this.codingTree = codingTree;

		// Special case when the tree is just one node,
		if (codingTree.isLeaf()) {
			
			// so its code is always 0.
			encoding.put(codingTree.getSymbol(), "0");
		}
		else {
			makeEncodingFromTree(codingTree, "");
		}
	}

	/**
	 * @return The message of the given code.
	 */
	public String decode(String code) {

		// Go trough all symbols in the code and traverse the code tree.
		String message = "";
		int codeSymbolIndex = 0;
		CodingNode currentNode = codingTree;
		while(codeSymbolIndex < code.length()) {

			if (currentNode.isLeaf()) {
				
				// Decode the leaf node and restart at the root.
				message += currentNode.getSymbol();
				currentNode = codingTree;
			}
			else {
				
				// Select the next node depending on the code symbol, if it's leaf stay on the same symbol.
				currentNode = currentNode.getSuccessors().get(code.charAt(codeSymbolIndex) - '0');
				if (currentNode.isLeaf()) {
					codeSymbolIndex--;
				}
			}
			
			codeSymbolIndex++;
		}

		return message;
	}

	/**
	 * @return The code of the given symbol.
	 */
	public String encode(char symbol) {
		return encoding.get(symbol);
	}

	/**
	 * @return The code of the given message.
	 */
	public String encode(String message) {

		// Encodes the message character by character.
		String result = "";
		for (int i = 0; i < message.length(); i++) {
			result += encoding.get(message.charAt(i));
		}

		return result;
	}

	/**
	 * Recursively creates the encoding function from a coding tree.
	 * 
	 * @param node - root node of the coding tree.
	 * @param code - the code of the root node.
	 */
	private void makeEncodingFromTree(CodingNode node, String code) {

		// If the node is a leaf,
		if (node.isLeaf()) {
			
			// encode its symbol.
			encoding.put(node.getSymbol(), code);
		}
		else {

			// Recursively encode all successor nodes.
			int i = 0;
			for (CodingNode successor : node.getSuccessors()) {
				
				char digit = (char) ('0' + i); // Uses ascii chars as digits starting from 0.		
				makeEncodingFromTree(successor, code + digit);
				i++;
			}
		}
	}
	
	/**
	 * Prints the encoding to the system.out..
	 */
	public void printEncoding() {
		
		for (Entry<Character, String> encodedSymbol : encoding.entrySet()) {
			System.out.print(encodedSymbol.getKey() + "->" + encodedSymbol.getValue() + " "); 
		}
		System.out.println();
	}
}
