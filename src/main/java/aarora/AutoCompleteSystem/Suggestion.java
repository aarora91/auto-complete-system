/**
 * 
 */
package aarora.AutoCompleteSystem;

import java.io.Serializable;

/**
 * Container for query suggestion
 * @author ashimaarora
 *
 */
public class Suggestion implements Serializable, Comparable<Suggestion> {
	private static final long serialVersionUID = 1L;

	public String suggestion;
	public int frequency;

	public Suggestion(String suggest, int freq) {
		suggestion = suggest;
		frequency = freq;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Suggestion s) {
		return s.frequency - this.frequency;
	}
	
	@Override
	public String toString() {
		return this.suggestion + "(" + this.frequency + ")\n";
	}
}
