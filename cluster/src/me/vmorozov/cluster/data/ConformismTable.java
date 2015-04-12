package me.vmorozov.cluster.data;

import java.util.Arrays;
import java.util.Iterator;

//TODO split to separate objects, reason: some of table interface should not be seen by computing
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
	
	public ListOfRows asListOfRows() {
		return new ListOfRows(this);
	}
	
	public void setRowWithSum(TableRow rowWithSumm, int row) {
		tableData[row] = rowWithSumm.getRow();
		sumsForRows[row] = rowWithSumm.getSum();
		rowIds[row] = rowWithSumm.getRowId();
	}
	
	public void removeRowWithLowestSum() {
		int lowestSum = Integer.MAX_VALUE;
		int indexWithLowesSum = 0;
		ListOfRows listOfRows = this.asListOfRows();
		for (int i = 0; i < listOfRows.size(); i++) {
			int currentSum = listOfRows.get(i).getSum();
			if (currentSum < lowestSum) {
				lowestSum = currentSum;
				indexWithLowesSum = i;
			}
		}
		removeRow(indexWithLowesSum);
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
	
	private int compareBySum(TableRow row1, TableRow row2) {
		if (row1.getSum() > row2.getSum()) {
			return 1;
		} else if (row1.getSum() < row2.getSum()) {
			return -1;
		}
		return 0;
	}
	
	

}
