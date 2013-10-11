package compression;

import java.util.ArrayList;

import junit.framework.TestCase;

public class InformationSourceTest extends TestCase {
	
	public void testEntropy() {
		
		ArrayList<SourceSymbol> sourceSymbols = new ArrayList<SourceSymbol>();
		sourceSymbols.add(new SourceSymbol('a', 0.9));
		sourceSymbols.add(new SourceSymbol('b', 0.1));
		InformationSource source = new InformationSource(sourceSymbols);
		
		// The entropy is 0.469 when rounded to 3 decimal digits.
		assertEquals(0.469, ((int) (source.entropy() * 1000 + 0.5))/1000.0);
	}

}
