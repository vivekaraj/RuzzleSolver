import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
	public static void main(String[] args) throws IOException {
		Grid gr = new Grid();
		gr.printGrid();
		gr.printValidWords();
		gr.exportToTxt();
	}
}
