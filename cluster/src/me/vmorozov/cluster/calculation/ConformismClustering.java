package me.vmorozov.cluster.calculation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.ListOfRows;
import me.vmorozov.cluster.data.Table;

//TODO move to one object??
public class ConformismClustering implements Clustering {
	
	@Override
	public ConformismTable compute(int[][] data, int availableValuesCount) {
		ConformismTable table = new ConformismTable(data);

		computeForRows(table, availableValuesCount);
		table.transpose();
		computeForRows(table, availableValuesCount);
		table.transpose();
		
		return table;
	}

	//TODO naming
	protected void computeForRows(ConformismTable table, int availableValuesCount) {
		table.setFrequenciesByColumns(computeFrequenciesByColumns(table, availableValuesCount));
		table.setSummsForRows(calculateRowSumms(table));
		sortByRowSumms(table);
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
	 * calculates summs. does frequency substitution step internally
	 * @param initialTable
	 * @param frequencies
	 * @return row for index, summ for value
	 */
	protected int[] calculateRowSumms(ConformismTable table) {
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
	
	protected void sortByRowSumms(ConformismTable table) {
		ListOfRows listOfRows = table.asListOfRows();
		listOfRows.sort( (row1, row2) -> { 
			if (row1.getSumm() < row2.getSumm()) {
				return 1;
			} else if (row1.getSumm() > row2.getSumm()) {
				return -1;
			}
			return 0;
		});
	}
}
 