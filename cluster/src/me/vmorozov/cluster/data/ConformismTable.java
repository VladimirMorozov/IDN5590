package me.vmorozov.cluster.data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Adds frequencies and sums to Table. Also some convenience methods.
 * Despite the name is used in Impact(mõju) clustering too
 * @author Vova
 *
 */
public class ConformismTable extends Table {

	/** [value, frequency] */
	private Table frequenciesByColumns;
	/** [value, frequency] */
	private Table frequenciesByRows;
	
	/** [realRowIndex] */
	private int[] sumsForRows;
	
	/** [columnIndex] */
	private int[] sumsForColumns;
	
	
	public ConformismTable(int[][] tableData) {
		super(tableData);
		sumsForRows = new int[getRowCount()];
		sumsForColumns = new int[getColumnCount()];
	}
	
	@Override
	public void transposeAndResetRemovedRows() {
		super.transposeAndResetRemovedRows();
		
		Table swap = frequenciesByColumns;
		frequenciesByColumns = frequenciesByRows;
		frequenciesByRows = swap;
		
		int[] swap2 = sumsForRows;
		sumsForRows = sumsForColumns;
		sumsForColumns = swap2;
	}
	
	public int getFrequencyByColumnOfValue(int column, int value) {
		return frequenciesByColumns.get(column, value);
	}
	
	/**
	 * Get table as list of rows, convenient for sorting or iterating. 
	 * changes in list are mirrored to table (!NB changes in row contents are not)
	 * @return
	 */
	public ListOfRows asListOfRows() {
		return new ListOfRows(this);
	}
	
	/**
	 * Set table data from row
	 * @param tableRow
	 * @param rowIndex
	 */
	public void setFromRow(TableRow tableRow, int rowIndex) {
		tableData[rowIndex] = tableRow.getRow();
		sumsForRows[rowIndex] = tableRow.getSum();
		rowIds[rowIndex] = tableRow.getRowId();
	}
	
	public void removeRowWithLowestSum() {
		int lowestSum = Integer.MAX_VALUE;
		int indexWithLowestSum = 0;
		ListOfRows listOfRows = this.asListOfRows();
		for (int i = 0; i < listOfRows.size(); i++) {
			int currentSum = listOfRows.get(i).getSum();
			if (currentSum < lowestSum) {
				lowestSum = currentSum;
				indexWithLowestSum = i;
			}
		}
		removeRow(indexWithLowestSum);
	}
	
	public void removeRowWithHighestSum() {
		int highestSum = Integer.MIN_VALUE;
		int indexWithHighestSum = 0;
		ListOfRows listOfRows = this.asListOfRows();
		for (int i = 0; i < listOfRows.size(); i++) {
			int currentSum = listOfRows.get(i).getSum();
			if (currentSum > highestSum) {
				highestSum = currentSum;
				indexWithHighestSum = i;
			}
		}
		removeRow(indexWithHighestSum);
	}
	
	public int getRowSum(int row) {
		return sumsForRows[getRealRowIndex(row)];
	}

	public Table getFrequenciesByColumns() {
		return frequenciesByColumns;
	}

	public void setFrequenciesByColumns(Table frequenciesByColumns) {
		this.frequenciesByColumns = frequenciesByColumns;
	}

	public Table getFrequenciesByRows() {
		return frequenciesByRows;
	}

	public void setFrequenciesByRows(Table frequenciesByRows) {
		this.frequenciesByRows = frequenciesByRows;
	}

	public int[] getSummsForRows() {
		return sumsForRows;
	}

	public void setSumsForRows(int[] sumsForRows) {
		for (int i = 0; i < sumsForRows.length; i++) {
			this.sumsForRows[getRealRowIndex(i)] = sumsForRows[i];
		}
	}

	public int[] getSumsForColumns() {
		return sumsForColumns;
	}

	public void setSumsForColumns(int[] sumsForColumns) {
		this.sumsForColumns = sumsForColumns;
	}

	@Override
	public String toString() {
		return super.toString() + "\n" + 
				"frequenciesByColumns(unsorted)=\n" + frequenciesByColumns  + "\n"
				+ "frequenciesByRows(unsorted)=\n" + frequenciesByRows + "\n"
				+ "summsForRows(sorted with table)=\n" + Arrays.toString(sumsForRows) + "\n"
				+ "summsForColumns(sorted with table)=\n" + Arrays.toString(sumsForColumns);
	}

}
