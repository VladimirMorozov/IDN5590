package me.vmorozov.cluster;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/*public class ClusteringTest {
	
	private int[][] testData = 
		{
			{1, 0, 0},
			{1, 1, 1}, 
			{0, 1, 0}
		};
	
	int[][] frequencies = 
		{
			{2, 1},
			{0, 3},
			{2, 1}
		};
	
	int[][] substituted = {
			{1, 2, 2},
			{3, 3, 3},
			{2, 1, 2}
	};
	
	int[] summs = {5, 9, 5};
	
	int[] objectOrder = {1, 0, 2};
	
	@Test
	public void computeFrequency() {
		ClusteredTable table = new ClusteredTable(testData);
		int[][] result = Clustering.computeAttributeFrequencyForRow(table, 2);
		
		
		
		System.out.println(Util.twoDimensionalArrayToString(result));
		TestUtil.assertEqualsTwoDimArrays("frequency is wrong", frequencies, result);
	} 
	 
	@Test
	public void calculateSumms() {
		ClusteredTable table = new ClusteredTable(testData);
		table.setRowFrequencies(frequencies);
		int[] result = Clustering.calculateSumms(table);
		assertArrayEquals("summs are wrong", summs, result);
	}
	
	@Test
	public void sort() {
//		ObjectWithSumms[] objectsWithSumms = Clustering.sort(testData, summs);
//		for (int i = 0; i < objectsWithSumms.length; i++) {
//			ObjectWithSumms objectWithSumms = objectsWithSumms[i];
//			assertEquals("wrong sort order", objectOrder[i], objectWithSumms.getId());
//		}
		ClusteredTable table = new ClusteredTable(testData);
		table.setRowSumms(summs);
		table.sortByRowSumms();
//		for (int i = 0; i < table.getTable().length; i++) {
//			assertEquals("wrong sort order", objectOrder[i], objectWithSumms.getId());
//		}
		System.out.println(Util.twoDimensionalArrayToString(table.getTable()));
	}
	

	


}*/
