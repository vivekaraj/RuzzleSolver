
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * A dictionary manages a collection of known, correctly-spelled words.
 */
public class Dictionary {
	private Set<String> set;
	private Set<String> prefixes;
 
  public Dictionary() throws IOException {
	  String filename = "dictionary.txt";
	  set = new HashSet<String>();
	  prefixes = new HashSet<String>();
	  File file = new File(filename);
	  Scanner read = new Scanner(file);
	  String temp = null;
	  while(read.hasNext()) {
		  temp = read.next();
		  if(temp.length() > 1 && Character.isLowerCase(temp.charAt(0))) {
			  temp = temp.toUpperCase();
			  this.set.add(temp);
			  addPrefixes(temp, prefixes);			  
		  }		  
	  }
	  read.close();	  
  }
  
  private void addPrefixes(String str, Set<String> set) {
	  for(int i = 0; i < str.length(); i++) {
		 set.add(str.substring(0, i+1));
	  }
  }
  
  public Set<String> getDict() {
	  return this.set;
  }
 
  public int getNumWords() throws IOException {
	return this.set.size();
  }

  public boolean isWord(Word word) throws IOException {
	String str = word.getString();
	return this.set.contains(str.toUpperCase().trim());
  }
  
  public boolean isWord(String word) throws IOException {
		return this.set.contains(word.toUpperCase().trim());
  }
  
  public boolean wordCanStart(Word w) throws FileNotFoundException {
		String s = w.getString().toUpperCase();
		return prefixes.contains(s);		
	}
}
