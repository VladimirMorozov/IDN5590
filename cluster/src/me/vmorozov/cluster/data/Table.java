package me.vmorozov.cluster.data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import me.vmorozov.cluster.Util;

public class Table {
	
	/**
	 * [row][column]
	 */
	protected int[][] tableData;
	protected int[] rowIds;
	protected int[] columnIds;
	
	public Table(int[][] tableData) {
		this.tableData = tableData;
		createIds();
	}
	
	public Table(int rows, int columns) {
		this.tableData = new int[rows][columns];
		createIds();
	}
	
	private void createIds() {
		rowIds = new int[getRowCount()];
		for (int i = 0; i < getRowCount(); i++) {
			rowIds[i] = i + 1;
		}
		
		columnIds = new int[getColumnCount()];
		for (int i = 0; i < getColumnCount(); i++) {
			columnIds[i] = i + 1;
		}
	}
	
	/**
	 * Swaps rows with columns
	 */
	public void transpose() {
		int[][] inverted = new int[getColumnCount()][getRowCount()];
		for (int row = 0; row < getRowCount(); row++) {
			for (int col = 0; col < getColumnCount(); col++) {
				inverted[col][row] = tableData[row][col];
			}
		}
		tableData = inverted;
		
		int[] swap = rowIds;
		rowIds = columnIds;
		columnIds = swap;
	}
	
	public int get(int row, int column) {
		return tableData[row][column];
	}
	
	public void set(int value, int row, int column) {
		tableData[row][column] = value;
	}
	
	public int[] getRowAsArray(int row) {
		return tableData[row];
	}
	
	public int getRowCount() {
		return tableData.length;
	}
	
	public int getColumnCount() {
		return tableData[0].length;
	}
	
	public Iterator<Integer> getRowIterator(int rowIndex) {
		return new TableRowIterator(this, rowIndex);
	}
	
	public Iterator<Integer> getColumnIterator(int columnIndex) {
		return new TableColumnIterator(this, columnIndex);
	}
	
	public void setTableData(int[][] tableData) {
		this.tableData = tableData;
	}
	
	public int getRowId(int rowIndex) {
		return rowIds[rowIndex];
	}

	@Override
	public String toString() {
		return Util.twoDimensionalArrayToMultilineString(tableData)
				+ "\nrowIds: " + Arrays.toString(rowIds)
				+ "\ncolumnIds: " + Arrays.toString(columnIds);
				
	}
	
	

}
