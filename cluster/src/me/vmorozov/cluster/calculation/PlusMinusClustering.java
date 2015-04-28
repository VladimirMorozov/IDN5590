package me.vmorozov.cluster.calculation;

import java.util.Iterator;
import java.util.List;

import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.ListOfRows;
import me.vmorozov.cluster.data.PlusMinusTable;
import me.vmorozov.cluster.data.Table;
import me.vmorozov.cluster.data.TableRow;

/**
 * Common class for plus and minus clustering methods
 * @author Vova
 *
 */
public abstract class PlusMinusClustering implements Clustering {

	@Override
	public PlusMinusTable compute(int[][] data, int availableValuesCount) {
		PlusMinusTable table = new PlusMinusTable(data);

		//compute for rows, then transpose to compute for columns, 
		//then transpose again to get initial table form
		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		return table;
	}

	/**
	 * computes all required info and puts into table for rows only
	 * @param table
	 * @param availableValuesCount
	 */
	protected void computeForRows(PlusMinusTable table, int availableValuesCount) {
		while (table.getRowCount() != 0) {
			table.setFrequenciesByColumns(computeFrequenciesByColumns(table, availableValuesCount));
			table.setSumsForRows(calculateRowSums(table));
			removeRowFromTable(table);
		}
		table.resetRemovedRows();
		sort(table);
		findClasses(table);
	}
	
	/**
	 * Depending on methods rows to removed are selected differently
	 * @param table
	 */
	public abstract void removeRowFromTable(ConformismTable table);
	
	
	
	/**
	 * Computes value frequencies in columns
	 * Frequencies are computed differently in subclasses.
	 * @param table
	 * @param availableValuesCount
	 * @return frequencies as int[columnIndex][value]
	 */
	protected abstract Table computeFrequenciesByColumns(ConformismTable table, int availableValuesCount);
	
	/**
	 * Calculates sums. does frequency substitution step internally
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
	
	/**
	 * sorts table. takes into account row removal order
	 * @param table
	 */
	protected abstract void sort(ConformismTable table);
	
	/**
	 * Finds classes or clusters start row indexes in table. sets them.
	 * @param table
	 */
	protected void findClasses(PlusMinusTable table) {
		int previousSum = 0;
		ListOfRows listOfRows = table.asListOfRows();
		for (int rowIndex = 0; rowIndex < listOfRows.size(); rowIndex++) {
			TableRow row = listOfRows.get(rowIndex);
			 if (row.getSum() <= previousSum) {
				 table.addNewRowClassIndex(rowIndex);
			 }
			 previousSum = row.getSum();
		}
		
	}

}
