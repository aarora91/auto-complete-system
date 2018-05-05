package aarora.AutoCompleteSystem;

import org.junit.Test;

public class SuggestionTest {

	@Test
	public void testCompareToBasic() {
		Suggestion s1 = new Suggestion("christmas gifts", 50);
		Suggestion s2 = new Suggestion("christmas games", 30);
		assert(s1.compareTo(s2)<0);
	}
	
	@Test
	public void testCompareToEqual() {
		Suggestion s1 = new Suggestion("christmas gifts", 50);
		Suggestion s2 = new Suggestion("christmas games", 50);
		assert(s1.compareTo(s2)==0);
	}

}
