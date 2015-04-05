package me.vmorozov.cluster;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestUtil {
	
	public static void assertEqualsTwoDimArrays(String message, int[][] array1, int[][] array2) {
		if (array1.length != array2.length) {
			assertEquals("arrays have different sizes. " + message, false, true);
		}
		for (int i = 0; i < array1.length; i++) {
			int[] innerArray1 = array1[i];
			int[] innerArray2 = array2[i];
			assertArrayEquals(message, innerArray1, innerArray2);
		}
	}

}
