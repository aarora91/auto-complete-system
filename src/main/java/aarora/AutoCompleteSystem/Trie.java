/**
 * 
 */
package aarora.AutoCompleteSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Trie data structure for maintaining a prefix tree
 * of query suggestions.
 * @author ashimaarora
 *
 */
public class Trie implements Serializable{
	private static final long serialVersionUID = 1L;

	public String prefix;
	public boolean isPhraseEnd;
	public Character character;
	public int frequency;
	public Map<Character,Trie> children;
	public Map<String,Integer> queryMap;
	public List<Suggestion> suggestions;
	
	/**
	 * Constructor for initializing
	 * a trie object.
	 */
	public Trie(Character ch, String pre) {
		prefix = pre;
		character = ch;
		frequency = 1;
		isPhraseEnd = false;
		queryMap = new HashMap<String,Integer>();
		children = new HashMap<Character,Trie>();
		suggestions = new ArrayList<Suggestion>();
	}
	
	/**
	 * Says if the trie contains a given prefix
	 * @param prefix
	 * @return
	 */
	public boolean contains(String prefix) {
		Trie node = this;
		for(int i=0; i<prefix.length(); i++) {
			Character ch = prefix.charAt(i);
			if(!node.children.containsKey(ch)) {
				return false;
			}
			node = node.children.get(ch);
		}
		return true;
	}
	
	/**
	 * Gets the node in the trie which
	 * has the given prefix
	 * @param prefix
	 * @return node with prefix
	 */
	public Trie getChild(String prefix) {
		Trie node = this;
		for(int i=0; i<prefix.length(); i++) {
			Character ch = prefix.charAt(i);
			if(!node.children.containsKey(ch)) {
				return null;
			}
			node = node.children.get(ch);
		}
		return node;
	}
	
	/**
	 * PP trie
	 */
	public void printTrie() {
		Queue<Trie> q = new LinkedList<Trie>();
		q.add(this);
		StringBuilder sb = new StringBuilder();
		Trie node;
		while(!q.isEmpty()) {
			int size = q.size();
			for(int i=0; i<size; i++) {
				node = q.remove();
				sb.append(node.prefix + "("+ node.frequency +")" + ",");
				for(Trie child:node.children.values()) {
					q.add(child);
				}
				if(node.isPhraseEnd)
					sb.append(" ~END~ ");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	/**
	 * @param suggestions the suggestions to set
	 */
	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}
}
