package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * <pre>This class is a division of the Yonglin Utility Dictionary</pre>
 * <p>This class is designed to simplify the process of read/write data to 
 * text files. It contains several file related algorithms that allows quick
 * data fetching and information summary.
 * <p>The methods in this class strongly depends on ArrayLists to store data 
 * in the computer memories. That means the inheriting object must also support 
 * List interfaces.
 * <p>The class doesn't support automatic PrintWriter closing, so the command
 * close() is mandatory if there are any print writing needed.
 * <p>This class is a general application of Oracle's Java IO standard Library,
 * using multiple IO classes, to modify files and analyze them.
 * @version 1.0
 * @author Yonglin Wang
 */
public class Files {
	String fileP;
	BufferedReader inputF;
	PrintWriter write;
	PrintWriter clear;
	File f;
	/**
	 * When an instance is created, the Files class will open a {@link java.io.BufferedReader}
	 * as well as a {@link java.io.PrintWriter} to enable the proper functioning of the methods
	 * in this class.
	 * @param filePath The path to the text file
	 */
	public Files(String filePath){
		fileP = filePath;
		f = new File(filePath);
		try {
			inputF = new BufferedReader (new FileReader (fileP));
			write = new PrintWriter (new FileWriter(fileP, true));
		} catch (FileNotFoundException e) {
			System.err.println("The file doen't exist in the directory");
		} catch (IOException e1){
			System.err.println("an IO exception occured");
		}
	}
	/**
	 * 
	 * @param s The string to be written to file
	 */
	public void writeStringToFile(String s){
		write.print("\n" + s);
	}
	public void writeListToFile(ArrayList<String> arg0){
		try {
			clear = new PrintWriter (fileP);
			clear.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < arg0.size(); i++) {
			write.println(arg0.get(i));
		}
		write.close();
	}
	public void fileToArrayList(ArrayList<String> al) {
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileP);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				al.add(line);
			}   
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileP + "'");                
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + fileP + "'");                  
		}
	}
	public void fileToArrayListRnd(ArrayList<String> al, int now) {
		ArrayList<String> listOfWords = new ArrayList<String>();
		Random rnd = new Random();
		Set<Integer> generated = new LinkedHashSet<Integer>();
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileP);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				listOfWords.add(line);
			}   
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileP + "'");                
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + fileP + "'");                  
		}
		while (generated.size() < now){
			Integer next = rnd.nextInt(listOfWords.size());
			// As we're adding to a set, this will automatically do a containment check
			generated.add(next);
		}
		Object[] rndIndexs = generated.toArray(); 
		for (int i = 0; i < now; i++) {
			al.add(listOfWords.get((int)rndIndexs[i]));
		}
	}

	/**
	 * <pre>This method counts the amount of lines a file has then returns it.</pre>
	 * <p>This is a functional method that specializes in counting lines a file
	 * has. This is done by scanning each line of a file until the BufferedReader
	 * returns a null value. When a null is returned, it means the program has reached  
	 * @return The amount of lines the text file has
	 */
	public int length(){
		String line = null;
		int count = 0;
		try {
			line = inputF.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}  //Read a String.
		while (line != null){  //File is terminated by a null.
			try {
				line = inputF.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			count++;
		}
		return count;
	}
	public void close(){
		write.close();
	}

	/**
	 * @return the absolute path of the file
	 */
	public String getFileDirectory() {
		// TODO Auto-generated method stub
		return f.getAbsolutePath();
	}
}
