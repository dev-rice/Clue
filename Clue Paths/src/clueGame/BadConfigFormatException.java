package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Error: configuration file is corrupted.");
		PrintWriter out;
		try {
			out = new PrintWriter("log.txt");
			out.print((new Timestamp(System.currentTimeMillis())));
			out.print("\nCurrent config files:");
			// TODO, add stuff here
			
			
			
			
			//
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
