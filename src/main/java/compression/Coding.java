package compression;

import java.util.Map;

public abstract class Coding {
	
	private Map<Character, String> encoding;
	private Map<Character, String> decoding;
	
	public String encode(String message) {
		
		// Encodes the message character by character.
		String result = "";
		for (int i = 0; i < message.length(); i++) {
			result += encoding.get(message.charAt(i));
		}
		
		return result;
	}
	
	public String decode(String message) {
		
		// Decodes the message character by character.
		String result = "";
		for (int i = 0; i < message.length(); i++) {
			result += decoding.get(message.charAt(i));
		}
		
		return result;
	}
}
