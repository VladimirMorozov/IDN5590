package me.vmorozov.letters;

import org.junit.Test;

import java.util.LinkedHashMap;

public class LettersTest {
	
	
	@Test
	public void getMapTest() {
		Letters letters = new Letters();
		LinkedHashMap<String, Integer> result = letters.getCharacterGroupMap("MERI METS MIISU MERSU RIST RISU", Letters.ESTONIAN_ALPHABET, false, true, false);
		System.out.println(result.toString());
	}

}
