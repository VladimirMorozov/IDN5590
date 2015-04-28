package me.vmorozov.cluster.calculation;

import java.util.Iterator;
import java.util.List;


import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.ListOfRows;
import me.vmorozov.cluster.data.Table;
import me.vmorozov.cluster.data.ValueAndIsRemoved;

public class PlusClustering extends PlusMinusClustering {

	@Override
	public void removeRowFromTable(ConformismTable table) {
		table.removeRowWithHighestSum();
	}
	
	@Override
	protected Table computeFrequenciesByColumns(ConformismTable table, int availableValuesCount) {
		int colCount = table.getColumnCount();
		int[][] result = new int[colCount][availableValuesCount];
		
		for (int columnIndex = 0; columnIndex < colCount; columnIndex++) {
			//goes through all values, even in deleted rows
			Iterator<ValueAndIsRemoved> rowValuesInColumn = table.getPlusColumnIterator(columnIndex);
			//value as index, frequency as value
			int[] valueFrequencies = new int[availableValuesCount];
			while (rowValuesInColumn.hasNext()) {
				ValueAndIsRemoved valueAndIsRemoved = rowValuesInColumn.next();
				int increment = 1;
				//instead of not contributing at all, 
				//removed values contribute double
				if (valueAndIsRemoved.isRemoved) {
					increment = 2;
				}
				valueFrequencies[valueAndIsRemoved.value] += increment ;
			}
			result[columnIndex] = valueFrequencies;
		}
		return new Table(result);
	}
	
	@Override
	protected void sort(ConformismTable table) {
		ListOfRows listOfRows = table.asListOfRows();
		
		listOfRows.sort( (row1, row2) -> { 
			if (row1.getRemovalOrder() > row2.getRemovalOrder()) {
				return 1;
			} else if (row1.getRemovalOrder() < row2.getRemovalOrder()) {
				return -1;
			}
			throw new RuntimeException("removal order cannot be equal");
		});
	}

}
