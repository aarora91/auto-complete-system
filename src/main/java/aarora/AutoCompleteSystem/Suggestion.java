/**
 * 
 */
package aarora.AutoCompleteSystem;

import java.io.Serializable;

/**
 * @author ashimaarora
 *
 */
public class Suggestion implements Serializable, Comparable<Suggestion> {
	private static final long serialVersionUID = 1L;

	public String suggestion;
	public int frequency;
	/**
	 * 
	 */
	public Suggestion(String suggest, int freq) {
		suggestion = suggest;
		frequency = freq;
	}

	@Override
	public int compareTo(Suggestion s) {
		return s.frequency - this.frequency;
	}
	
	@Override
	public String toString() {
		return this.suggestion + "(" + this.frequency + ")\n";
	}
}
