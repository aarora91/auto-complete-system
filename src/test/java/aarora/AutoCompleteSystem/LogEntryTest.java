package aarora.AutoCompleteSystem;

import org.junit.Test;

public class LogEntryTest {

	@Test
	public void testBasicLogEntryCreation() {
		assert(new LogEntry("christmas tree|en-US|2017-11-29 00:35:06")!=null);
	}
	
	@Test(expected = AssertionError.class)
	public void testInvalidLogThrowsError() {
		new LogEntry("christmas tree");
	}
}
