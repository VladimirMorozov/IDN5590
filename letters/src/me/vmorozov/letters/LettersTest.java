package me.vmorozov.letters;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Test;

public class LettersTest {
	
	
	@Test
	public void getMapTest() {
		Letters letters = new Letters();
		LinkedHashMap<String, Integer> result = letters.getCharacterGroupMap("MERI METS MIISU MERSU RIST RISU", Letters.ESTONIAN_ALPHABET, false, true, false);
		System.out.println(result.toString());
	}

}
