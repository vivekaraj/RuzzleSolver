import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;


public class Grid {
	private char[][] Grid = new char[4][4];
	private Dictionary dict;
	private Set<Word> validWords = new HashSet<Word>();
	ArrayList<String> sortedValidWords;

	public Grid() throws IOException {
		dict = new Dictionary();
		String disp = "";
		for(int i = 0; i < 4; i++) {
			String next = "";
			if(i == 0) {
				next = JOptionPane.showInputDialog("Please enter the four characters in row 1 with no spaces (example: sdfd)");
			} else {
				next = JOptionPane.showInputDialog(disp + "Please enter row " + (i+1));
			}
			String temp = "";
			boolean done = false;
			while(!done) {
				try {
					temp = "0";
					if(next.length() != 4) {
						temp = "create exception";
					} else {
						for(int j = 0; j < 4; j++) {
							if(!Character.isAlphabetic(next.charAt(j))) {
								temp = "create exception";
							}
						}
					}
					int temp2 = Integer.parseInt(temp);
					done = true;
				} catch(Exception e) {
					next = JOptionPane.showInputDialog(disp + "Please enter a valid 4-character entry (example: asdf) for row " + (i+1));
					done = false;
				}
			}
			next = next.toUpperCase();
			for(int j = 0; j < 4; j++) {
				Grid[i][j] = next.charAt(j);
				disp += next.charAt(j) + "\t";
			}
			disp += "\n";
		}
		scanGrid();
		sortSet();
	}
	
	public void printGrid() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(Grid[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public void dispGrid() {
		String disp = "";
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				disp += Grid[i][j] + "\t";
			}
			disp += "\n";
		}
		JOptionPane.showMessageDialog(null, disp);
	}	

	private ArrayList<Tuple<Integer,Integer>> listCopy(ArrayList<Tuple<Integer,Integer>> l) {
		ArrayList<Tuple<Integer,Integer>> retVal = new ArrayList<Tuple<Integer,Integer>>();
		for(int i = 0; i < l.size(); i++) {
			retVal.add(l.get(i));
		}
		return retVal;
	}
	
	private void process(Word curr, int newRow, int newCol) throws IOException {
		if(dict.wordCanStart(curr)) {
			String str = curr.getString() + Grid[newRow][newCol];
			ArrayList<Tuple<Integer, Integer>> newTouched = listCopy(curr.getTouched());
			newTouched.add(new Tuple<Integer, Integer>(newRow, newCol));
			if(dict.isWord(str)) {
				validWords.add(new Word(str, newTouched));
			}
			checkSurroundings(new Word(str, newTouched), newRow, newCol);
		} else {
			//do nothing
		}
	}
	
	public static boolean listContains(ArrayList<Tuple<Integer, Integer>> list, Tuple<Integer, Integer> tuple) {
		int fst = tuple.getFirst();
		int snd = tuple.getSecond();
		for(int i = 0; i < list.size(); i++) {
			int fstL = list.get(i).getFirst();
			int sndL = list.get(i).getSecond();
			if(fst == fstL && snd == sndL) {
				return true;
			}
		}
		return false;
	}
	
	public void checkSurroundings(Word curr, int row, int col) throws IOException {
		int newRow = -1;
		int newCol = -1;
		//check left-up diagonal
		newRow = row - 1;
		newCol = col - 1;
		if(row > 0 && col > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}
		//check up
		newRow = row;
		newCol = col - 1;
		if(col > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}		
		//check right-up diagonal
		newRow = row + 1;
		newCol = col - 1;
		if(row < 3 && col > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}
		//check right
		newRow = row + 1;
		newCol = col;
		if(row < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}
		//check right-down diagonal
		newRow = row + 1;
		newCol = col + 1;
		if(row < 3 && col < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}		
		//check down
		newRow = row;
		newCol = col + 1;
		if(col < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}
		//check left-down diagonal
		newRow = row - 1;
		newCol = col + 1;
		if(row > 0 && col < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}
		//check left
		newRow = row - 1;
		newCol = col;
		if(row > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(curr, newRow, newCol);
		}
	}
	
	private void scanGrid() throws IOException {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				ArrayList<Tuple<Integer, Integer>> touched = new ArrayList<Tuple<Integer, Integer>>();
				touched.add(new Tuple<Integer,Integer>(i,j));
				Word w = new Word(("" + Grid[i][j]), touched);
				checkSurroundings(w, i, j);
			}
		}
	}
	
	private int findMax(int[] arr) {
		int ret = -1;
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] > ret) {
				ret = arr[i];
			}
		}
		return ret;
	}
	
	public void sortSet() {
		Word[] temp = new Word[validWords.size()];
		Word[] validWordsSorted = validWords.toArray(temp);
		int[] points = new int[validWordsSorted.length];
		for(int i = 0; i < points.length; i++) {
			points[i] = validWordsSorted[i].getString().length();
		}
		sortedValidWords = new ArrayList<String>();
		int max = findMax(points);
		while(max > 1) {
			for(int i = 0; i < points.length; i++) {
				if(points[i] == max) {
					sortedValidWords.add(validWordsSorted[i].getString());
				}
			}
			max--;
		}
	}
	
	public void printValidWords() {
		for(int i = 0; i < sortedValidWords.size(); i++) {
			System.out.println(sortedValidWords.get(i));
		}
	}
	
	public void exportToTxt() throws FileNotFoundException {
		PrintStream output = new PrintStream(new File("solution.txt"));
		for(int i = 0; i < sortedValidWords.size(); i++) {
			output.println(sortedValidWords.get(i));
		}
	}
	
	
	/*private void process(ArrayList<Word> list, Word curr, int row, int col) {
		int newRow = -1;
		int newCol = -1;
		//check left-up diagonal
		newRow = row - 1;
		newCol = col - 1;
		if(row > 0 && col > 0) {
			ArrayList<Tuple<Integer,Integer>> l = listCopy(curr.getTouched());
			l.add(new Tuple<Integer,Integer>)
			list.add((curr.getString() + Grid[row-1][col-1]), );
		}
		
		if(row > 0 && col > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}
		//check up
		newRow = row;
		newCol = col - 1;
		if(col > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}		
		//check right-up diagonal
		newRow = row + 1;
		newCol = col - 1;
		if(row < 3 && col > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}
		//check right
		newRow = row + 1;
		newCol = col;
		if(row < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}
		//check right-down diagonal
		newRow = row + 1;
		newCol = col + 1;
		if(row < 3 && col < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}		
		//check down
		newRow = row;
		newCol = col + 1;
		if(col < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}
		//check left-down diagonal
		newRow = row - 1;
		newCol = col + 1;
		if(row > 0 && col < 3 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}
		//check left
		newRow = row - 1;
		newCol = col;
		if(row > 0 && !listContains(curr.getTouched(), new Tuple<Integer,Integer>(newRow, newCol))) {
			process(list, curr, newRow, newCol);
		}
	} 
	
	public ArrayList<Word> scanGrid() throws FileNotFoundException {
		ArrayList<Word> list = new ArrayList<Word>();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				ArrayList<Tuple<Integer, Integer>> touched = new ArrayList<Tuple<Integer, Integer>>();
				touched.add(new Tuple<Integer, Integer>(i,j));
				process(list, new Word(("" + Grid[i][j]), touched), i, j);
			}
		}
		
		return list;
	} */
}
