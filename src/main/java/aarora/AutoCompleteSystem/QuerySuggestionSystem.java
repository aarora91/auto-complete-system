/**
 * 
 */
package aarora.AutoCompleteSystem;

import java.util.List;

/**
 * The "master" class which is responsible
 * for setting up the auto-complete system
 * and providing query suggestions.
 * @author ashimaarora
 *
 */
public class QuerySuggestionSystem {
	public QuerySuggestionSystem() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Main entry point
	 * @param args partial query string
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.err.println("You must pass only 1 argument, a partial query string.");
			return;
		}
		String queryString = args[0];
		long startTime = System.nanoTime();
		QueryProcessor queryProcessor = new QueryProcessor(10);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;
		System.out.println(duration + "ms");
		startTime = System.nanoTime();
		
		List<Suggestion> suggestions = queryProcessor.suggest(queryString);
		
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000;
		System.out.println(duration + "ms");
	}
}
