package me.vmorozov.cluster;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class FileUtil {
	
	public static int[][] readTable(String path) {
		try {
			
			int[][] result;	
			result = Files.lines(Paths.get(path))
				.skip(2L) //ignore col/row number
				.map(FileUtil::intArrayFromLine)
				.toArray(int[][]::new);
			
			return result;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static int[] intArrayFromLine(String line) {
		return Stream.of(line.split(" "))
		.mapToInt(stringNum -> Integer.parseInt(stringNum))
		.toArray();
	}
	
	public static void write(String text) {
		try (PrintWriter out = new PrintWriter("D:\\output.txt")){
			out.write(text);
			//Files.write(Paths.get("D:\\output.txt"), text.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	


}
