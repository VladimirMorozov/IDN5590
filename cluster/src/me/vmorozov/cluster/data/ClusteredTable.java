package me.vmorozov.cluster.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @deprecated not used. can be removed. left for reference
 * @author Vova
 *
 */
@Deprecated
public class ClusteredTable {
	
	private int[][] table;
	private int[] columnSumms;
	private int[] rowSumms;
	private int[][] rowFrequencies;
	private int[][] columnFrequencies;
	private int[] rowIds;
	private int[] colIds;
	
	public void invert() {
		int[][] inverted = new int[table[0].length][table.length];
		for (int row = 0; row < table.length; row++) {
			for (int col = 0; col < table[0].length; col++) {
				inverted[col][row] = table[row][col];
			}
		}
		table = inverted;
		
		int[][] swap = rowFrequencies;
		rowFrequencies = columnFrequencies;
		columnFrequencies = swap;
		
		int[] swap2 = rowSumms;
		rowSumms = columnSumms;
		columnSumms = swap2;
		
		swap2 = rowIds;
		rowIds = colIds;
		colIds = swap2;
	}
	
	
	
	
	
	public int[][] getTable() {
		return table;
	}



	public void setTable(int[][] table) {
		this.table = table;
	}




	public int[] getColumnSumms() {
		return columnSumms;
	}



	public void setColumnSumms(int[] columnSumms) {
		this.columnSumms = columnSumms;
	}



	public int[] getRowSumms() {
		return rowSumms;
	}



	public void setRowSumms(int[] rowSumms) {
		this.rowSumms = rowSumms;
	}



	public int[][] getRowFrequencies() {
		return rowFrequencies;
	}



	public void setRowFrequencies(int[][] rowFrequencies) {
		this.rowFrequencies = rowFrequencies;
	}



	public int[][] getColumnFrequencies() {
		return columnFrequencies;
	}



	public void setColumnFrequencies(int[][] columnFrequencies) {
		this.columnFrequencies = columnFrequencies;
	}



	public ClusteredTable(int[][] table) {
		this.table = table;
	}
	
	public int[] getRow(int row) {
		return table[row];
	}
	
	public int[] getColumn(int columnIndex) {
		int[] column = new int[getRowCount()];
		for (int i = 0; i < getRowCount(); i++ ) {
			column[i] = getElement(i, columnIndex);
		}
		return column;
	}
	
	public int getElement(int x, int y) {
		return table[x][y];
	}
	
	public int getColumnCount() {
		return table[0].length;
	}
	
	public int getRowCount() {
		return table.length;
	}

	public int[] getRowIds() {
		return rowIds;
	}

	public void setRowIds(int[] rowIds) {
		this.rowIds = rowIds;
	}

	public int[] getColIds() {
		return colIds;
	}

	public void setColIds(int[] colIds) {
		this.colIds = colIds;
	}

}
