package compression;

import java.util.HashMap;
import java.util.Map;

/**
 * A coding function mapping information source's symbols to code alphabet symbols,
 * it encodes and decodes messages.
 */
public class Coding {

	private Map<Character, String> encoding;
	private CodingNode codingTree;

	/**
	 * Constructs a coding from the given coding tree.
	 */
	public Coding(CodingNode codingTree) {

		encoding = new HashMap<Character, String>();
		this.codingTree = codingTree;

		// Special case when the tree is just one node,
		if (codingTree.isLeaf()) {
			
			// so its code is always 0.
			encoding.put(codingTree.getSource().getFirstSymbol(), "0");
		}
		else {
			makeEncodingFromTree(codingTree, "");
		}
	}

	/**
	 * Recursively creates the encoding function from a coding tree.
	 * 
	 * @param node - root node of the coding tree.
	 * @param code - the code of the root node.
	 */
	private void makeEncodingFromTree(CodingNode node, String code) {

		if (node.isLeaf()) {

			// A leaf node should have only one symbol, so encode that with the code.
			encoding.put(node.getSource().getFirstSymbol(), code);
		}
		else {

			// Recursively encode all successor nodes.
			int i = 0;
			for (CodingNode successor : node.getSuccessors()) {
				makeEncodingFromTree(successor, code + i);
				i++;
			}
		}
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
				message += currentNode.getSource().getFirstSymbol();
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
}
