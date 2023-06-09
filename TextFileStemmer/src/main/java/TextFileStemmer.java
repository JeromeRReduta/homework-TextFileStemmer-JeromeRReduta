import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

/**
 * Utility class for parsing and stemming text and text files into collections
 * of stemmed words.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Fall 2020
 *
 * @see TextParser
 */
public class TextFileStemmer {
	/** The default stemmer algorithm used by this class. */
	public static final SnowballStemmer.ALGORITHM DEFAULT = SnowballStemmer.ALGORITHM.ENGLISH;

	/**
	 * Returns a list of cleaned and stemmed words parsed from the provided line.
	 *
	 * @param line the line of words to clean, split, and stem
	 * @param stemmer the stemmer to use
	 * @return a list of cleaned and stemmed words
	 *
	 * @see Stemmer#stem(CharSequence)
	 * @see TextParser#parse(String)
	 */
	public static ArrayList<String> listStems(String line, Stemmer stemmer) {
		return getStems(line, stemmer, new ArrayList<String>());
	}

	/**
	 * Returns a list of cleaned and stemmed words parsed from the provided line
	 * using the default stemmer.
	 *
	 * @param line the line of words to clean, split, and stem
	 * @return a list of cleaned and stemmed words
	 *
	 * @see SnowballStemmer
	 * @see #DEFAULT
	 * @see #listStems(String, Stemmer)
	 */
	public static ArrayList<String> listStems(String line) {
		return listStems(line, new SnowballStemmer(DEFAULT));
	}

	/**
	 * Reads a file line by line, parses each line into cleaned and stemmed words,
	 * and then adds those words to a set.
	 *
	 * @param inputFile the input file to parse
	 * @return a sorted set of stems from file
	 * @throws IOException if unable to read or parse file
	 *
	 * @see #uniqueStems(String)
	 * @see TextParser#parse(String)
	 */
	public static ArrayList<String> listStems(Path inputFile) throws IOException {
		
		ArrayList<String> stems = new ArrayList<>();
		
		try (Scanner scan = new Scanner(inputFile, StandardCharsets.UTF_8)) {
		
			while (scan.hasNext()) 
				stems.addAll(listStems(scan.next()));
			
			return stems;
		}
		
		catch (IOException e) {
			System.out.println("listStems() file version - error - IOException; returning null");
			return null;
		}
		
		catch (Exception e) {
			System.out.println("listStems() file version - error - unknown Exception; returning null");
			return null;
		}
	}

	/**
	 * Returns a set of unique (no duplicates) cleaned and stemmed words parsed
	 * from the provided line using the default stemmer.
	 *
	 * @param line the line of words to clean, split, and stem
	 * @return a sorted set of unique cleaned and stemmed words
	 *
	 * @see SnowballStemmer
	 * @see #DEFAULT
	 * @see #uniqueStems(String, Stemmer)
	 */
	public static TreeSet<String> uniqueStems(String line) {	
		return uniqueStems(line, new SnowballStemmer(DEFAULT));
	}

	/**
	 * Returns a set of unique (no duplicates) cleaned and stemmed words parsed
	 * from the provided line.
	 *
	 * @param line the line of words to clean, split, and stem
	 * @param stemmer the stemmer to use
	 * @return a sorted set of unique cleaned and stemmed words
	 *
	 * @see Stemmer#stem(CharSequence)
	 * @see TextParser#parse(String)
	 */
	public static TreeSet<String> uniqueStems(String line, Stemmer stemmer) {
		return getStems(line, stemmer, new TreeSet<String>());
	}

	/**
	 * Reads a file line by line, parses each line into cleaned and stemmed words,
	 * and then adds those words to a set.
	 *
	 * @param inputFile the input file to parse
	 * @return a sorted set of stems from file
	 * @throws IOException if unable to read or parse file
	 *
	 * @see #uniqueStems(String)
	 * @see TextParser#parse(String)
	 */
	public static TreeSet<String> uniqueStems(Path inputFile) throws IOException {
		
		TreeSet<String> stems = new TreeSet<>();
		
		try (Scanner scan = new Scanner(inputFile, StandardCharsets.UTF_8)) {
			
			while (scan.hasNext()) 
				stems.addAll(uniqueStems(scan.next()));
			
			return stems;
		}
		
		catch (IOException e) {
			System.out.println("uniqueStems() file version - error - IOException; returning null");
			return null;
		}
		
		catch (Exception e) {
			System.out.println("uniqueStems() file version - error - unknown Exception; returning null");
			return null;
		}
	}
	
	/**
	 * Converts a line to stems and stores stems in a given collection.
	 * 
	 * @param line the line to be stemmed
	 * @param stemmer the stemmer to use
	 * @param stems the Object that holds the stems
	 * 
	 * @return a list of stems, sorted based on what type of Object stems is
	 * 
	 */
	
	private static <C extends Collection<String>> C getStems(String line, Stemmer stemmer, C stems) {
		
		String[] words = TextParser.parse(line);
		
		for (String word : words)
			stems.add(stemmer.stem(word).toString());
		
		return stems;
	}

	/**
	 * A simple main method that demonstrates this class.
	 *
	 * @param args unused
	 * @throws IOException if an I/O error occurs
	 */
	
	public static void main(String[] args) throws IOException {
		String text = "practic practical practice practiced practicer practices "
				+ "practicing practis practisants practise practised practiser "
				+ "practisers practises practising practitioner practitioners";

		//System.out.println(uniqueStems(text));
		//System.out.println(listStems(text));

		Path inputPath = Path.of("src", "test", "resources", "animals.text");
		Set<String> actual = TextFileStemmer.uniqueStems(inputPath);
		//System.out.println(actual);
	}
}
