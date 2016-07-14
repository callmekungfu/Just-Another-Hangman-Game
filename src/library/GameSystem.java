package library;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.prefs.Preferences;


/**
 * <pre>The GameSystem Class defines the gameplay and AI behavior engine of the hangman game.</pre>
 * <p>This class utilizes the Yonglin Reference Library to create a capable system that manages
 * the core behaviors of the game. The system is created with intuition and efficiency in mind,
 * enabling other classes to utilize and benefit from this instance effortlessly.</p>
 * @author Yonglin Wang
 *
 */
public class GameSystem {
	ArrayUtil au = new ArrayUtil();
	
	private String builtInFile = "data.txt";
	public String userFile = "userData.txt";
	
	public Preferences prefs;
	public Files builtInDiction = new Files(builtInFile);
	public Files userDiction = new Files(userFile);
	public double percentage = 1;
	/**
	 * When created instance, the class retrieves the values of certain variables from the preference table
	 * stored in the windows registry. This allows the game to remember user preferences and adapt the gameplay
	 * accordingly.
	 */
	public GameSystem(){
		prefs = Preferences.userRoot().node(this.getClass().getName());
	}
	/**
	 * <pre>This method searches and stores the position of a substring in a Stack.</pre>
	 * This is a procedural method that utilizes the Stack utility class to store the position integer value 
	 * of a substring inside a string. The methods checks the entire string for the substring and stores all
	 * the found positions on the top of a stack predefined in the other class.
	 * 
	 * @param st The stack used to store the character positions
	 * @param sub The value of the substring that is being searched
	 * @param s the string used for searching
	 */
	public void findSubPosInString(Stack<Integer> st, String sub, String s){
		st.clear();
		for (int i = -1; (i = s.indexOf(sub, i + 1)) != -1; ) {
			st.push(i);
		}
	}
	/**
	 * <pre>This method retrieves a list of words randomly from the predefined dictionaries, stores the words in an ArrayList and sort it.</pre>
	 * <p>This is a procedural method that handles the construction of the word list used in principal gameplay.
	 * This method contains two local ArrayLists that temporarily stores the words accessed by the {@link Files#fileToArrayListRnd(ArrayList, int)}
	 * method for both the built-in dictionary as well as the user dictionary. The amount of words fetched by the
	 * system is defined by the user modifiable variable percentage, it determines the amount of words retrieved
	 * from the dictionaries by using a simple multiplication function.</p>
	 * <p>The method then combines the two arrays into one and applies a bubble sort on it for optimized
	 * performance and marks.</p>
	 * <p>List of Local Variables</p>
	 * <ul><li>wordsFromBuiltIn - temporarily store the words got from the built-in dictionary
	 * 	   <li>wordsFromUser - temporarily store the words got from the user dictionary</ul>
	 * @param alsGlobal The ArrayList strings will be written to 
	 * @param total The total amount of words generated per session
	 */
	public void getWordList(ArrayList<String> alsGlobal, int total){
		ArrayList<String> wordsFromBuiltIn = new ArrayList<String>();
		ArrayList<String> wordsFromUser = new ArrayList<String>();
		int numOfWordsBuiltIn = (int) (total * percentage + 0.5);
		builtInDiction.fileToArrayListRnd(wordsFromBuiltIn, numOfWordsBuiltIn);
		if(userDiction.length() < (total - numOfWordsBuiltIn)){
			throw new IllegalArgumentException();
		}
		alsGlobal.clear();
		userDiction.fileToArrayListRnd(wordsFromUser, (total - numOfWordsBuiltIn));
		alsGlobal.addAll(wordsFromBuiltIn);
		alsGlobal.addAll(wordsFromUser);
		au.sort(alsGlobal);
	}
	/**
	 * <pre>This method generates a set of random numbers limited by the parameter.</pre>
	 * <p>This is a functional method that returns an array of random numbers without duplicates. This is especially crucial
	 * to game design since you do not want to have repeating gameplay elements, which unbalanced the game and creates boredom.</p>
	 * <p>This is done by randomly generating the numbers into a set, which, 
	 * @param size This variable defines the number of terms to generate
	 * @return returns a Integer array filled with random numbers that doesn't repeat.
	 */
	public int[] rndOrder(int size){
		Random rnd = new Random();
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < size){
			Integer next = rnd.nextInt(size);
			generated.add(next);
		}
		Object[] rndIndexs = generated.toArray();
		int[] indexs = new int[rndIndexs.length];
		for (int i = 0; i < rndIndexs.length; i++) {
			indexs[i] = (int)rndIndexs[i];
		}
		return indexs;
	}
}