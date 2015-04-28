package me.vmorozov.cluster.calculation;

import java.util.Iterator;

import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.ListOfRows;
import me.vmorozov.cluster.data.Table;

public class MinusClustering extends PlusMinusClustering {

	@Override
	public void removeRowFromTable(ConformismTable table) {
		table.removeRowWithLowestSum();

	}
	
	@Override
	protected Table computeFrequenciesByColumns(ConformismTable table, int availableValuesCount) {
		int colCount = table.getColumnCount();
		int[][] result = new int[colCount][availableValuesCount];
		
		for (int columnIndex = 0; columnIndex < colCount; columnIndex++) {
			Iterator<Integer> rowValuesInColumn = table.getColumnIterator(columnIndex);
			//value as index, frequency as value
			int[] valueFrequencies = new int[availableValuesCount];
			while (rowValuesInColumn.hasNext()) {
				valueFrequencies[rowValuesInColumn.next()]++;
			}
			result[columnIndex] = valueFrequencies;
		}
		return new Table(result);
	}
	
	@Override
	protected void sort(ConformismTable table) {
		ListOfRows listOfRows = table.asListOfRows();
		
		listOfRows.sort( (row1, row2) -> { 
			if (row1.getRemovalOrder() < row2.getRemovalOrder()) {
				return 1;
			} else if (row1.getRemovalOrder() > row2.getRemovalOrder()) {
				return -1;
			}
			throw new RuntimeException("removal order cannot be equal");
		});
	}

}
