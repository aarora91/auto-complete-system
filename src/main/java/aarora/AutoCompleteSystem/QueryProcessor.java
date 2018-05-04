/**
 * 
 */
package aarora.AutoCompleteSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author ashimaarora
 *
 */
public class QueryProcessor {
	private final static Logger LOGGER = Logger.getLogger(QueryProcessor.class.getName());
	private Trie trie;
	private int k;
	private TextProcessor textProcessor;
	/**
	 * 
	 */
	public QueryProcessor(int maxTopResults) {
		String trieDumpFile = AutoCompleteConstants.trieDumpFile;
		File f = new File(trieDumpFile);
		if(f.exists() && !f.isDirectory()) { 
			trie = (Trie) UtilityFunctions.loadFromFile(trieDumpFile); //Load the trie from previously cached file.
			LOGGER.info("Loaded trie from dump.");
		} else {
			k = maxTopResults;
			
			textProcessor = new TextProcessor();
			LOGGER.info("Created text processor.");

			trainWithLogFile(AutoCompleteConstants.logFile);
			LOGGER.info("Trained with log file.");

			cacheTopKQueries();
			LOGGER.info("Cached top k queries.");
			
			UtilityFunctions.writeToFile(AutoCompleteConstants.trieDumpFile, trie);
			LOGGER.info("Wrote trie to dump.");
		}
	}
	
	/**
	 * @param prefix
	 */
	public List<Suggestion> suggest(String prefix) {
		Trie root = trie;
		for(int i=0; i<prefix.length(); i++) {
			char ch = prefix.charAt(i);
			if(!root.children.containsKey(ch)){
				System.out.println("NO SUGGESTIONS...");
				return null;
			}
			root = root.children.get(ch);
		}
		System.out.println(root.suggestions.toString());
		return root.suggestions;
	}
	
	/**
	 * 
	 */
	public void cacheTopKQueries() {
		Queue<Trie> q = new LinkedList<Trie>();
		q.add(trie);
		Trie node;
		while(!q.isEmpty()) {
			int size = q.size();
			for(int i=0; i<size; i++) {
				node = q.remove();
				//Get top k suggestions for the prefix for this node.
				node.suggestions = getTopKSuggestions(node);
				for(Trie child:node.children.values()) {
					q.add(child);
				}
			}
		}
	}
	
	/**
	 * @param pathToLogFile
	 */
	public void trainWithLogFile(String pathToLogFile) {
		//Load the logs from resources/logs.txt
		System.out.println(new File(".").getAbsoluteFile());
		File file = new File(pathToLogFile);

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			LogEntry logEntry;
			trie = new Trie('^', "");
			while ((line = br.readLine()) != null) {
				logEntry = new LogEntry(line);
				addLogEntryToTrie(logEntry);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param logEntry
	 */
	public void addLogEntryToTrie(LogEntry logEntry){
		String queryStr = logEntry.getQueryString();
		Trie node = this.trie;
		for(int i=0; i<queryStr.length(); i++) {
			Character ch = queryStr.charAt(i);
			if(node.children.containsKey(ch)) {
				node.frequency = node.frequency + 1; //prefix has been seen again.
			} else {
				node.children.put(ch, new Trie(ch, queryStr.substring(0, i+1))); //prefix seen for the first time.
			}
			node = node.children.get(ch);
			String qStr = logEntry.getQueryString();
			//Add the logEntry query to the map of seen queries for this prefix.
			//If it's already there in the map, then increment it's count.
			node.queryMap.put(qStr, node.queryMap.getOrDefault(qStr, 0) + 1);
		}
		node.isPhraseEnd=true;
	}
	
	/**
	 * @param node
	 * @return
	 */
	public List<Suggestion> getTopKSuggestions(Trie node) {
		PriorityQueue<Suggestion> q = new PriorityQueue<Suggestion>();
		
		for(String option: node.queryMap.keySet()) {
			q.add(new Suggestion(option, node.queryMap.get(option))); //Add this possibility to the P queue.
		}
		Suggestion s;
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		
		Set<String> addedQueries = new HashSet<String>(); //keeps track of the queries added to the queue. We need this to check for similarity.
		int i=0;
		while(i<k && !q.isEmpty()) {
			s = q.remove();
			String suggestedQuery = s.suggestion;
			if(isUniqueQuery(suggestedQuery, addedQueries)) {
				suggestions.add(s);
				addedQueries.add(suggestedQuery);
				i++;
			}
		}
		return suggestions;
	}
	
	/**
	 * @param query
	 * @param added
	 * @return
	 */
	public boolean isUniqueQuery(String query, Set<String> added) {
		Iterator<String> iter = added.iterator();
		while(iter.hasNext()) {
			String addedQuery = iter.next();
			if(textProcessor.areSimilar(query, addedQuery))
				return false;
		}
		return true;
	}
}