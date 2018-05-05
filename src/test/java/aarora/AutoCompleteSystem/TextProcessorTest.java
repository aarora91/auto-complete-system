/**
 * 
 */
package aarora.AutoCompleteSystem;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author ashimaarora
 *
 */
public class TextProcessorTest {
	TextProcessor tp = new TextProcessor();	
	@Test
	public void testNormalizeBasic() {
		String[] expected = new String[] {"green", "tea", "packet"};
		String[] normalized = tp.normalize("green tea packets");
		assertTrue(normalized.length==3);
		assertArrayEquals(expected, normalized);
	}
	
	@Test
	public void testNormalizeAlreadyNormalized() {
		String[] expected = new String[] {"green", "tea", "packet"};
		String[] normalized = tp.normalize("green tea packet");
		assertTrue(normalized.length==3);
		assertArrayEquals(expected, normalized);
	}
	
	@Test
	public void testAreSimilarTrue() {
		boolean result = tp.areSimilar("green tea packet", "green teas");
		assertTrue(result);
	}
	
	@Test
	public void testAreSimilarFalse() {
		boolean result = tp.areSimilar("green tea packet", "green cushion");
		assertFalse(result);
	}
}
