/**
 * 
 */
package aarora.AutoCompleteSystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author ashimaarora
 *
 */
public class TrieTest {
	Trie trie;
	@Before
	public void before() {
		trie = new Trie('^', "");
		trie.children.put('g', new Trie('g', "g"));
		trie.children.get('g').children.put('o', new Trie('o', "go"));
	}
	@Test
	public void testContainsTrue() {
		assertTrue(trie.contains("go"));
	}

	@Test
	public void testContainsFalse() {
		assertFalse(trie.contains("game"));
	}
	
	@Test
	public void testGetChild() {
		Trie node = trie.getChild("g");
		assertNotNull(node);
		assertTrue(node.character == 'g');
		node = trie.getChild("game");
		assertNull(node);
	}
}
