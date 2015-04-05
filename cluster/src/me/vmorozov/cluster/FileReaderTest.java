package me.vmorozov.cluster;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

public class FileReaderTest {
	
	private int[][] expected = 
		{
			{1, 2, 5},
			{7, 6, 1},
			{9, 1, 2}
		};
	
	@Test
	public void test() throws IOException {
		FileUtil fileReader = new FileUtil();
		int[][] content = fileReader.readTable("D:\\input.txt");
		TestUtil.assertEqualsTwoDimArrays("file content should be correct", content, expected);
	}

}
