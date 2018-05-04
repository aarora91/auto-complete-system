/**
 * 
 */
package aarora.AutoCompleteSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * @author ashimaarora
 * Handles all text processing
 * for queries.
 */
public class TextProcessor {
	public Tokenizer tokenizer;
	public POSTaggerME posTagger;
	public DictionaryLemmatizer lemmatizer;

	/**
	 * 
	 */
	public TextProcessor() {
		createTokenizer();
		createPOSTagger();
		createLemmatizer();
	}

	/**
	 * 
	 */
	private void createPOSTagger() {
		InputStream modelIn;
		try {
			modelIn = new FileInputStream(AutoCompleteConstants.pathToModelsDir + "/en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			posTagger = new POSTaggerME(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void createTokenizer() {
		InputStream modelIn;
		try {
			modelIn = new FileInputStream(AutoCompleteConstants.pathToModelsDir  + "/en-token.bin");
			TokenizerModel model = new TokenizerModel(modelIn);
			tokenizer = new TokenizerME(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void createLemmatizer() {
		InputStream is;
		try {
			is = new FileInputStream(AutoCompleteConstants.pathToModelsDir  + "/en-lemmatizer.txt");
			// loading the lemmatizer with dictionary
			lemmatizer = new DictionaryLemmatizer(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param sentence
	 * @return
	 */
	private String[] normalize(String sentence) {
		String[] tokens = tokenizer.tokenize(sentence);
		String[] tags = posTagger.tag(tokens);
		return lemmatizer.lemmatize(tokens, tags);
	}

	/**
	 * @param query1
	 * @param query2
	 * @return
	 */
	public boolean areSimilar(String query1, String query2) {
		Set<String> set1 = new HashSet<String>(Arrays.asList(normalize(query1)));
		Set<String> set2 = new HashSet<String>(Arrays.asList(normalize(query2)));
		set1.retainAll(set2); // set1 now contains only elements in both sets
		return set1.size()>1; // super simple! may be enhanced to compute cosine similarity.
	}
}