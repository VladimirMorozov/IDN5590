package me.vmorozov.cluster;

import java.util.Arrays;

public class Util {
	
	public static String twoDimensionalArrayToString(int[][] array) {
		String result = "[";
		for (int[] innerArray : array) {
			result += Arrays.toString(innerArray)+",";
		}
		return result+"]";
	}
	
	public static String twoDimensionalArrayToMultilineString(int[][] array) {
		String result = "";
		for (int[] innerArray : array) {
			for (int number : innerArray) {
				result += number+" ";
			}
			result +="\n";
			
		}
		return result;
	}

}
