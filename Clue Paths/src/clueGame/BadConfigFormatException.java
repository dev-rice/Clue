package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Error: configuration file is formatted incorrectly.");
		PrintWriter out;
		try {
			out = new PrintWriter("log.txt");
			out.print((new Timestamp(System.currentTimeMillis())));
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
