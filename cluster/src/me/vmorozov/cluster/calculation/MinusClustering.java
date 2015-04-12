package me.vmorozov.cluster.calculation;

import java.util.Iterator;

import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.ListOfRows;
import me.vmorozov.cluster.data.Table;
import me.vmorozov.cluster.data.TableRow;

public class MinusClustering implements Clustering {

	@Override
	public ConformismTable compute(int[][] data, int availableValuesCount) {
		ConformismTable table = new ConformismTable(data);

		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		//TODO find clusters i guess
		return table;
	}

	protected void computeForRows(ConformismTable table, int availableValuesCount) {
		while (table.getRowCount() != 0) {
			table.setFrequenciesByColumns(computeFrequenciesByColumns(table, availableValuesCount));
			table.setSumsForRows(calculateRowSums(table));
			table.removeRowWithLowestSum();
		}
		table.resetRemovedRows();
		sortByRowRemovalOrder(table);
	}
	
	
	
	/**
	 * @param table
	 * @param availableValuesCount
	 * @return frequencies as int[columnIndex][value]
	 */
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
	
	/**
	 * calculates sums. does frequency substitution step internally
	 * @param initialTable
	 * @param frequencies
	 * @return row for index, sum for value
	 */
	protected int[] calculateRowSums(ConformismTable table) {
		int[] summs = new int[table.getRowCount()];
		
		for (int row = 0; row < table.getRowCount(); row++) {
			for (int column = 0; column < table.getColumnCount(); column++) {
				int attributeValue = table.get(row, column);
				//substitution happens here:
				summs[row] += table.getFrequencyByColumnOfValue(column, attributeValue);
			}
		}
		return summs;
	}
	
	protected void sortByRowRemovalOrder(ConformismTable table) {
		ListOfRows listOfRows = table.asListOfRows();
		int tempId = listOfRows.get(5).getRowId();
		
		System.out.println("----");
		for (TableRow row : listOfRows) {
			System.out.println(row.getRemovalOrder());
		}
		
		listOfRows.sort( (row1, row2) -> { 
			if (row1.getRemovalOrder() < row2.getRemovalOrder()) {
				return 1;
			} else if (row1.getRemovalOrder() > row2.getRemovalOrder()) {
				return -1;
			}
			return 0;
		});
		
		boolean temp = tempId == listOfRows.get(5).getRowId();
		if(temp){}
	}

}
