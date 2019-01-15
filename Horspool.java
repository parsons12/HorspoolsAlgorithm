import java.util.HashMap;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

/**
 * A collection of methods to support string matches.
 * 
 * @author Blake Parsons
 * @since 2018-11-27
 */

public class Horspool {

	public static final String CHARACTER_POOL = "ABCDEFGHIJLKMNOPQRSTUVWXYZ ,!?\"'/\\-()";

	public static void main(String[] args) throws IOException {

		Scanner scnr = new Scanner(new File("98-0.txt"));
		String[] patternBag = { "HELLO", "WORK", "SHALL", "ROB", "MOB" };
		String corpus = "";
		char[] alphabet;
		char[] text;
		char[] pattern;

		for (int i = 0; i < 100 && scnr.hasNextLine(); i++) {
			scnr.nextLine();

		}

		for (int i = 0; i < 100 && scnr.hasNextLine(); i++) {
			corpus += " " + scnr.nextLine().toUpperCase();
		}

		alphabet = ExtractAlphabet(corpus);
		text = corpus.toCharArray();

		System.out.printf("%-15s %-10s %-4s %-12s %-4s\n-------\n", "Pattern", "Horspool", "%", "Brute Force", "%");
		for (int i = 0; i < patternBag.length; i++) {
			pattern = patternBag[i].toCharArray();
			ShiftTable st = new ShiftTable(pattern, alphabet);

			int HorspoolCompCount = HorspoolMatch(text, pattern, st);
			int BruteCompCount = BruteForceMatch(text, pattern);

			System.out.printf("%-15s %-10d %-4.2f %-12d %-4.2f\n", patternBag[i], HorspoolCompCount,
					(float) HorspoolCompCount / text.length, BruteCompCount, (float) BruteCompCount / text.length);

		}
	

	}

	/**
	 * Extract all of the characters in a provided string.
	 * 
	 * @param text A string representing a source text.
	 * @return An array containing all characters present in the string.
	 */
	public static char[] ExtractAlphabet(String text) {
		String alphabet = "";

		for (int i = 0; i < text.length(); i++) {
			if (alphabet.indexOf(text.charAt(i)) == -1) {
				alphabet += text.charAt(i);
			}
		}

		return alphabet.toCharArray();

	}

	/**
	 * Creates an alphabet that is a randomly selected subset of of the
	 * CHARACTER_POOL.
	 * 
	 * @param size The number of characters to select for the alphabet.
	 * @return An array of characters that represents the alphabet.
	 */
	public static char[] CreateRandomAlphabet(int size) {
		Random r = new Random();
		String alphabet = "";

		int i = 0;
		while (i < size) {

			int idx = r.nextInt(CHARACTER_POOL.length());
			char c = CHARACTER_POOL.charAt(idx);
			if (alphabet.indexOf(c) == -1) {
				alphabet += c;
				i++;
			}

		}

		return alphabet.toCharArray();
	}

	/**
	 * Create a random text of a specified size using a provided alphabet.
	 * 
	 * @param alphabet Source of characters that can be used to construct a text.
	 * @param size     The length of the text to construct.
	 * @return An array of characters representing the text.
	 */
	public static char[] CreateRandomText(char[] alphabet, int size) {

		Random r = new Random();

		String text = "";

		for (int i = 0; i < size; i++) {
			text += alphabet[r.nextInt(alphabet.length)];
		}

		return text.toCharArray();
	}

	/**
	 * Searches for a pattern in a text using Horspool's algorithm. It steps through
	 * the text, shifting the pattern to the left using an offset from a shift
	 * table.
	 * 
	 * THE METHOD SHOULD COUNT THE NUMBER OF COMPARISIONS AND RETURN THIS VALUE. DO
	 * NOT RETURN THE LOCATION OF THE MATCH OR -1 IF A MATCH DOES NOT EXIST.
	 * 
	 * @param text       The source text to search
	 * @param pattern    The pattern to search for in the source text.
	 * @param shiftTable A ShiftTable constructed specifically for provided pattern.
	 * @return The number of comparisons performed to find a match of the pattern in
	 *         the next or determine that the pattern is not present.
	 */
	public static int HorspoolMatch(char[] text, char[] pattern, ShiftTable shiftTable) {

		int comparisonCount = 0;
		int i = pattern.length - 1;

		while (i < text.length) {//continue until end of text
			int k = 0;//set number of matches to zero every shift

			//continue until end of pattern or pattern doesn't matche text
			while (k <= pattern.length - 1 && pattern[pattern.length - 1 - k] == text[i - k]) {

				k++;//increment k to look at next value
				comparisonCount++;//add a comparison

			}
			if (k == pattern.length) {//if match is found
				break;//stop searching
			} else {
				comparisonCount++;//add a comparison
				
				//increment i by shift table values
				i = i + shiftTable.getShiftByChar(text[i]);
			}

		}

		return comparisonCount;
	}

	/**
	 * A brute force text match algorithm. Aligns the pattern to the text and steps
	 * through the text, shifting the pattern to the left by one character until a
	 * match is made or no more alignments exist.
	 * 
	 * THE METHOD SHOULD COUNT THE NUMBER OF COMPARISIONS AND RETURN THIS VALUE. DO
	 * NOT RETURN THE LOCATION OF THE MATCH OR -1 IF A MATCH DOES NOT EXIST.
	 * 
	 * @param text    The source text to search
	 * @param pattern The pattern to search for in the source text.
	 * @return The number of comparisons performed to find a match of the pattern in
	 *         the next or determine that the pattern is not present.
	 */
	public static int BruteForceMatch(char[] text, char[] pattern) {
		int comparisonCount = 0;
		int k = 0;

		for (int i = 0; i < text.length; i++) {

			if (pattern.length == k) {// if match is found
				break;// quit matching
			}
			k = 0;
			for (int j = 0; j < pattern.length; j++) {

				if (pattern[j] == text[i + k]) {
					k++;
					comparisonCount++;
				} else {// if character doesn't match

					comparisonCount++;
					break;// break and move string over
				}

			}

		}

		return comparisonCount;

	}

}
