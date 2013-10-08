package compression;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.paukov.combinatorics.ICombinatoricsVector;

/**
 * Unit test for simple InformationSource.
 */
public class InformationSourceTest extends TestCase
{

	// Information source to split.
	private ArrayList<SourceSymbol> source;
	
	// Symbols in the source.
	private  SourceSymbol a;
	private  SourceSymbol b;
	private  SourceSymbol c;
	private  SourceSymbol d;
	private  SourceSymbol e;

	@Override
	public void setUp() {
		
		a = new SourceSymbol('A', 7/32.0);
		b = new SourceSymbol('B', 4/32.0);
		c = new SourceSymbol('C', 2/32.0);
		d = new SourceSymbol('D', 11/32.0);
		e = new SourceSymbol('E', 8/32.0);
		
		source = new ArrayList<SourceSymbol>();
		source.add(a);
		source.add(b);
		source.add(c);
		source.add(d);
		source.add(e);
	}

	public void testEqualSplitIn2()
	{

		ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> equalHalves = new InformationSource(source).equalSplit(2);

		// First half should contain A and E
		ICombinatoricsVector<SourceSymbol> equalHalf = equalHalves.getValue(0);
		assertEquals(2, equalHalf.getSize());
		assertTrue(equalHalf.contains(b));
		assertTrue(equalHalf.contains(d));

		// Second half should contain B, C, D.
		equalHalf = equalHalves.getValue(1);
		assertEquals(equalHalf.getSize(), 3);
		assertTrue(equalHalf.contains(a));
		assertTrue(equalHalf.contains(c));
		assertTrue(equalHalf.contains(e));
	}

	public void testEqualSplit1In2()
	{
		// When splitting an information source with one element,
		ArrayList<SourceSymbol> source = new ArrayList<SourceSymbol>();
		SourceSymbol a = new SourceSymbol('A', 1);
		source.add(a);
		ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> part = new InformationSource(source).equalSplit(2);

		// it should be split in one part containing only the element.
		assertEquals(1, part.getSize());
		assertEquals(1, part.getValue(0).getSize());
		assertEquals(a, part.getValue(0).getValue(0));
	}

	public void testEqualSplitIn3() {

		ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> equalThirds = new InformationSource(source).equalSplit(3);

		// First third should contain A and B
		ICombinatoricsVector<SourceSymbol> equalThird = equalThirds.getValue(0);
		assertEquals(2, equalThird.getSize());
		assertTrue(equalThird.contains(a));
		assertTrue(equalThird.contains(b));

		// Second third should contain C and E.
		equalThird = equalThirds.getValue(2);
		assertEquals(equalThird.getSize(), 2);
		assertTrue(equalThird.contains(c));
		assertTrue(equalThird.contains(e));

		// Third third should contain D. 
		equalThird = equalThirds.getValue(1);
		assertEquals(equalThird.getSize(), 1);
		assertTrue(equalThird.contains(d));
	}
	
	public void testEqualSplitIn3_2() {
		
		a = new SourceSymbol('A', 15/41.0);
		b = new SourceSymbol('B', 7/41.0);
		c = new SourceSymbol('C', 6/41.0);
		d = new SourceSymbol('D', 6/41.0);
		e = new SourceSymbol('E', 5/41.0);
		
		source = new ArrayList<SourceSymbol>();
		source.add(a);
		source.add(b);
		source.add(c);
		source.add(d);
		source.add(e);
		
		ICombinatoricsVector<ICombinatoricsVector<SourceSymbol>> equalThirds = new InformationSource(source).equalSplit(3);

		// First third should contain A
		ICombinatoricsVector<SourceSymbol> equalThird = equalThirds.getValue(0);
		assertEquals(1, equalThird.getSize());
		assertTrue(equalThird.contains(a));

		// Second third should contain B and E.
		equalThird = equalThirds.getValue(2);
		assertEquals(equalThird.getSize(), 2);
		assertTrue(equalThird.contains(b));
		assertTrue(equalThird.contains(e));

		// Third third should contain C and D
		equalThird = equalThirds.getValue(1);
		assertEquals(equalThird.getSize(), 2);
		assertTrue(equalThird.contains(c));
		assertTrue(equalThird.contains(d));
	}
}
