/**
 * 
 */
package aarora.AutoCompleteSystem;

import static org.junit.Assert.assertTrue;

/**
 * @author ashimaarora
 * Container to hold information pertaining
 * to a single log entry
 */
public class LogEntry {
	private String fullLogEntryString;
	private String queryString;
	private String origin;
	private String timeStamp;
	private String date;
	private String time;
	
	/**
	 * Constructs a log entry from it's string
	 */
	public LogEntry(String logEntryStr) {
		fullLogEntryString = logEntryStr;
		String[]fields = logEntryStr.trim().split("\\|");
		
		assertTrue("Invalid log format", fields.length==3);
		
		queryString = fields[0];
		origin = fields[1];
		timeStamp = fields[2];
		String[]dateTime = fields[2].trim().split(" ");
		date = dateTime[0];
		time = dateTime[1];
	}
	
	/**
	 * @return the fullLogEntryString
	 */
	public String getFullLogEntryString() {
		return fullLogEntryString;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

}
