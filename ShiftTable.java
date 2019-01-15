import java.util.HashMap;

/**
 * 
 * 
 * @author Blake Parsons
 * @since 2018-11-27
 */
public class ShiftTable {

	// Use a hash map for the shift table, mapping characters to a shift
	private HashMap<Character, Integer> ShiftTable;
	private char[] alphabet;
	int j = 1;

	/**
	 * 
	 * @param pattern
	 * @param alphabet
	 */
	public ShiftTable(char[] pattern, char[] alphabet) {
		this.alphabet = alphabet;
		ShiftTable = new HashMap<Character, Integer>();
		
		
		for(int i = alphabet.length-1; i >=0; i--) {
			ShiftTable.put(alphabet[i], pattern.length);
		}
		for(int j = pattern.length-2;j >=0; j--) {
			if(ShiftTable.get(pattern[j]) == pattern.length) {
				ShiftTable.put(pattern[j], pattern.length-1-j);
			}
			
		}
		
	}

	/**
	 * Get the shift for a particular character.
	 * 
	 * @param c A character in the shift table 
	 * @return The shift associated with the given character.
	 */
	public int getShiftByChar(char c) {
		return ShiftTable.get(c);
	}

	
	public void dumpShiftTable() {
		System.out.println("-----");
		for(int j = 0; j < alphabet.length; j++) {
			System.out.println(alphabet[j] + ": " + ShiftTable.get(alphabet[j]));
		}
		System.out.println("-----\n");
	}
	
}
