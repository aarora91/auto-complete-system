package aarora.AutoCompleteSystem;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class QueryProcessorTest {
	QueryProcessor qp;
	List<Suggestion> expected = Arrays.asList(new Suggestion[] {
			new Suggestion("christmas tree", 3), 
			new Suggestion("christ figures", 1), 
			new Suggestion("christmas games", 1)});
	@Before
	public void runBefore() {
		qp = new QueryProcessor(new Trie('^', ""), 3, new TextProcessor());
	}

	@Test
	public void testSuggest() {
		qp.addLogEntryToTrie(new LogEntry("christmas tree|en-US|2017-11-29 00:35:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas tree|en-US|2017-11-30 00:35:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas tree|en-US|2017-11-23 00:35:06"));
		qp.addLogEntryToTrie(new LogEntry("christ figures|en-US|2017-11-29 00:36:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas games|en-US|2017-11-29 00:38:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas gifts|en-US|2017-11-29 00:39:06"));

		qp.getTrie().getChild("christ").setSuggestions(expected);
		List<Suggestion> s = qp.suggest("christ");
		assertNotNull(s);
		assertTrue(s.size()==3);
		assertEquals("Wrong result", "christmas tree", s.get(0).suggestion);
		assertEquals("Wrong result", "christ figures", s.get(1).suggestion);
		assertEquals("Wrong result", "christmas games", s.get(2).suggestion);
	}

	@Test
	public void testAddLogEntryToTrieBasic() {
		qp.addLogEntryToTrie(new LogEntry("christmas tree|en-US|2017-11-29 00:35:06"));
		assert(qp.getTrie().contains("christmas tree"));
	}

	@Test
	public void testGetTopKSuggestions() {
		qp.addLogEntryToTrie(new LogEntry("christmas tree|en-US|2017-11-29 00:35:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas tree|en-US|2017-11-30 00:35:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas tree|en-US|2017-11-23 00:35:06"));
		qp.addLogEntryToTrie(new LogEntry("christ figures|en-US|2017-11-29 00:36:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas games|en-US|2017-11-29 00:38:06"));
		qp.addLogEntryToTrie(new LogEntry("christmas gifts|en-US|2017-11-29 00:39:06"));
		
		qp.getTrie().getChild("christ").setSuggestions(expected);
		List<Suggestion> actual = qp.getTopKSuggestions(qp.getTrie().getChild("christ")); //All suggestions for the prefix tree rooted at "christ".
		assertNotNull(actual);
		assertEquals(expected.size(), actual.size());
		assertEquals("Wrong result", "christmas tree", actual.get(0).suggestion);
		assertEquals("Wrong result", "christmas gifts", actual.get(1).suggestion);
		assertEquals("Wrong result", "christ figures", actual.get(2).suggestion);
	}

	@Test
	public void testIsUniqueQuery() {
		Set<String> added = new HashSet<String>();
		assertTrue(qp.isUniqueQuery("christ figures", added));
		added.add("christ figures");
		assertTrue(qp.isUniqueQuery("christmas gift", added));
		added.add("christmas gift");
		assertFalse(qp.isUniqueQuery("christmas gifts", added)); //not unique
	}

}
