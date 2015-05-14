package me.vmorozov.letters;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileUtil {
	
	public static String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file, "UTF-8");
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	    	boolean firstLine = true;
	        while(scanner.hasNextLine()) {  
	        	if (firstLine) {
	        		fileContents.append(scanner.nextLine());
	        	} else {
	        		fileContents.append(lineSeparator + scanner.nextLine());
	        	}
	            
	            firstLine = false;
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
	
	public static void write(String text, String filePath) {
		try {
			Files.write(Paths.get(filePath), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

}
