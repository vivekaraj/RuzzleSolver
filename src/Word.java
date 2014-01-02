import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Word {
	private String str;
	private ArrayList<Tuple<Integer, Integer>> touched;
	public Word(String str, ArrayList<Tuple<Integer, Integer>> touched) {
		this.str = str;
		this.touched = touched;
	}
	
	/*public static boolean isWord(String s, Dictionary dict) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("dictionary.txt"));
		while(scan.hasNext()) {
			String temp = scan.next();
			if(temp.length() > 1 && Character.isLowerCase(temp.charAt(0))) {
				if(s.equals(temp)) {
					scan.close();
					return true;
				}
			}
		}
		scan.close();
		return false;
	}*/
	
	public String getString() {
		return str;
	}
	
	public ArrayList<Tuple<Integer, Integer>> getTouched() {
		return touched;
	}
	
	/*public static boolean wordCanStart(Word w) throws FileNotFoundException {
		String s = w.getString().toLowerCase();
		Scanner scan = new Scanner(new File("dictionary.txt"));
		while(scan.hasNext()) {
			String temp = scan.next();
			if(Character.isLowerCase(temp.charAt(0)) && temp.length() >= s.length())  {
				temp = temp.toLowerCase();
				boolean retTrue = true;
				for(int i = 0; i < s.length(); i++) {
					if(temp.charAt(i) != s.charAt(i)) {
						retTrue = false;
					}
				}
				if(retTrue) {
					scan.close();
					return true;
				}
			}
		}
		scan.close();
		return false;
	}*/
}
