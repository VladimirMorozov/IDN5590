package me.vmorozov.letters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
		System.out.println("args:" + ArgsUtil.getParameterMap(args, Arrays.asList("-i")));
		Map<String, String> arguments = ArgsUtil.getParameterMap(args, Arrays.asList("-i"));
		
		String inputFilePath = arguments.get("-i");
		String outputFilePath = arguments.getOrDefault("-o", getDefaultOutputPath());
		String alphabetFilePath = arguments.get("-a");
		String sortArg = arguments.getOrDefault("-s", "parse");
		String caseSensitiveArg = arguments.getOrDefault("-c", "false");
		boolean fromWordBegginingOnly = Boolean.parseBoolean(arguments.getOrDefault("-b", "false"));
		boolean sort;
		switch (sortArg) {
		case "parse":
			sort = false;
			break;
		case "alphanumeric":
			sort = true;
			break;
		default:
			throw new RuntimeException("wrong -s argument");
		}
		
		boolean caseSensitive = Boolean.parseBoolean(caseSensitiveArg);
		
		List<Character> alphabet;
		if (alphabetFilePath == null) {
			alphabet = ESTONIAN_ALPHABET;
		} else {
			alphabet = new ArrayList<Character>();
			String alphabetFileText = FileUtil.readFile(alphabetFilePath).toUpperCase();
			for (Character letter : alphabetFileText.toCharArray()) {
				alphabet.add(letter);
			}
		}
		
		String text = FileUtil.readFile(inputFilePath);
		Letters letters = new Letters();
		long startTime = System.currentTimeMillis();
		LinkedHashMap<String, Integer> result = letters.getCharacterGroupMap(text, alphabet, caseSensitive, sort, fromWordBegginingOnly);
		System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + " ms");
		//System.out.println(result);
		
		String output = "";
		for( Entry<String, Integer> subWord : result.entrySet()) {
			output += subWord.getKey() + " " + subWord.getValue() + "\n";
		}
		
		FileUtil.write(output, outputFilePath);
	}

	private static String getDefaultOutputPath() {
		return new File("").getAbsolutePath()+"\\lettersOutput.txt";
	}
	
	/**
	 * Finds character groups in text
	 * @param text text to parse
	 * @param alphabet alphabet to use (all other chars are delimiters)
	 * @param caseSensitive 
	 * @param sort should it be sorted alphanumeric? in parse order otherwise
	 * @param fromWordBegginingOnly should character groups be found only in relation to beggining of the words
	 * @return [chargroup, timesEncountered]
	 */
	public LinkedHashMap<String, Integer> getCharacterGroupMap(
			String text, List<Character> alphabet, boolean caseSensitive, boolean sort, boolean fromWordBegginingOnly) {

		//linked hash map is used to keep order
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		//walk through all characters taking beginIndex as character group start
		for (int beginIndex = 0; beginIndex < text.length(); beginIndex++) {
			Character startChar = text.charAt(beginIndex);
			if (!alphabet.contains(Character.toUpperCase(startChar))) {
				continue;
			}
			//for each character walk through all next characters and form groups with them
			for (int endIndex = beginIndex + 1; endIndex <= text.length(); endIndex++) {
				Character endChar = text.charAt(endIndex - 1);
				if (!alphabet.contains(Character.toUpperCase(endChar))) {
					//if we form groups only relative to beginning of the word
					//when word end is reached we move beginIndex to its place 
					//(-1 is because ++ will be done when cycle ends)
					if (fromWordBegginingOnly) { 
						beginIndex = endIndex - 1;
					}
					break;
				}
				//get and add characterGroup to map
				String characterGroup = text.substring(beginIndex, endIndex);
				if (!caseSensitive) {
					characterGroup = characterGroup.toLowerCase();
				}
				incrementWordCountInMap(characterGroup, result);
			}
		}
		
		//sorting
		if (sort) {
			result = sortMap(result);
		}
		
		return result;
		
	}

	private LinkedHashMap<String, Integer> sortMap(LinkedHashMap<String, Integer> result) {
		
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
