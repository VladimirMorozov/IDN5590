package me.vmorozov.cluster.data;

import java.util.Arrays;

//TODO split to separate objects, reason: some of table interface should not be seen by computing
public class ConformismTable extends Table {

	/** [value, frequency] */
	private Table frequenciesByColumns;
	/** [value, frequency] */
	private Table frequenciesByRows;
	
	/** [rowIndex] */
	private int[] summsForRows;
	
	/** [columnIndex] */
	private int[] summsForColumns;
	
	
	public ConformismTable(int[][] tableData) {
		super(tableData);
	}
	
	@Override
	public void transpose() {
		super.transpose();
		
		Table swap = frequenciesByColumns;
		frequenciesByColumns = frequenciesByRows;
		frequenciesByRows = swap;
		
		int[] swap2 = summsForRows;
		summsForRows = summsForColumns;
		summsForColumns = swap2;
	}
	
	public int getFrequencyByColumnOfValue(int column, int value) {
		return frequenciesByColumns.get(column, value);
	}
	
	public ListOfRows asListOfRows() {
		return new ListOfRows(this);
	}
	
	public void setRowWithSum(RowWithSum rowWithSumm, int row) {
		tableData[row] = rowWithSumm.getRow();
		summsForRows[row] = rowWithSumm.getSumm();
		rowIds[row] = rowWithSumm.getRowId();
	}
	
	public int getRowSum(int row) {
		return summsForRows[row];
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
		return summsForRows;
	}

	public void setSummsForRows(int[] summsForRows) {
		this.summsForRows = summsForRows;
	}

	public int[] getSummsForColumns() {
		return summsForColumns;
	}

	public void setSummsForColumns(int[] summsForColumns) {
		this.summsForColumns = summsForColumns;
	}

	@Override
	public String toString() {
		return super.toString() + "\n" + 
				"frequenciesByColumns(unsorted)=\n" + frequenciesByColumns  + "\n"
				+ "frequenciesByRows(unsorted)=\n" + frequenciesByRows + "\n"
				+ "summsForRows(sorted with table)=\n" + Arrays.toString(summsForRows) + "\n"
				+ "summsForColumns(sorted with table)=\n" + Arrays.toString(summsForColumns);
	}
	
	
	
	

}
