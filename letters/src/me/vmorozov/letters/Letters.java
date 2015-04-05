package me.vmorozov.letters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Letters {
	
	
	public static final List<Character> ESTONIAN_ALPHABET = Arrays.asList(
			'A', 'B', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
			'O', 'P', 'R', 'S', 'Š', 'Z', 'Ž', 'T', 'U', 'V', 'Õ', 'Ä', 'Ö', 'Ü'
			);
	
	public static void main(String[] args) throws IOException {
		String text = FileUtil.readFile("C:\\10megane.txt");
		Letters letters = new Letters();
		LinkedHashMap<String, Integer> result = letters.getMap(text, ESTONIAN_ALPHABET, true);
		
		System.out.println(result);
		
		String output = "";
		for( Entry<String, Integer> subWord : result.entrySet()) {
			output += subWord.getKey() + " " + subWord.getValue() + "\n";
		}
		
		FileUtil.write(output);
	}
	
	public LinkedHashMap<String, Integer> getMap(String text, List<Character> alphabet, boolean caseSensitive) {
		//TODO in relation to word beginning only
		//TODO decide on sorting
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		for (int beginIndex = 0; beginIndex < text.length(); beginIndex++) {
			Character startChar = text.charAt(beginIndex);
			if (!alphabet.contains(Character.toUpperCase(startChar))) {
				continue;
			}
			for (int endIndex = beginIndex + 1; endIndex <= text.length(); endIndex++) {
				Character endChar = text.charAt(endIndex - 1);
				if (!alphabet.contains(Character.toUpperCase(endChar))) {
					break;
				}
				String subword = text.substring(beginIndex, endIndex);
				if (!caseSensitive) {
					subword = subword.toLowerCase();
				}
				incrementWordCountInMap(subword, result);
			}
		}
		
		//sorting
		result = result.entrySet().stream()
	    .sorted(Map.Entry.comparingByKey())
	    .collect(Collectors.toMap(
	    		entry -> entry.getKey(), 
	    		entry -> entry.getValue(), 
	    		(v1, v2) -> { throw new RuntimeException("key collision"); }, 
	    		LinkedHashMap::new));
		
		
		return result;
		
	}
	
	private void incrementWordCountInMap(String word, Map<String, Integer> wordMap) {
		if (wordMap.get(word) == null) {
			wordMap.put(word, 1);
		} else {
			wordMap.put(word, wordMap.get(word) + 1);
		} 
	}

}
