package me.vmorozov.cluster.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import me.vmorozov.cluster.Util;

/**
 * Used for holding table consisting of ints. Has row removing feature. 
 * Rows can be removed from this table and then restored. 
 * While rows are removed table behaves as if they do not exist.
 * !NB Methods/variables containing "Real" word mean that table is manipulated as if there are no removed rows.
 * In addition to data this class also creates and stores ids of columns and rows.
 * @author Vova
 *
 */
public class Table {
	
	/**
	 * [row][column]
	 */
	protected int[][] tableData;
	protected int[] rowIds;
	protected int[] columnIds;
	/** [rowIndex] -> underlying index in tableData. maps rowIndex to realRowIndex	 */
	protected List<Integer> notRemovedRowIndexes;
	
	/** [rowIndex] */
	protected int[] rowRemovalOrder;
	protected int currentRemovalOrder = 1;
	
	public Table(int[][] tableData) {
		if (tableData.length == 0) {
			throw new RuntimeException("cannot create empty table");
		}
		this.tableData = tableData;
		createIds();
		resetRemovedRowsAndOrder();
	}
	
	public Table(int rows, int columns) {
		this.tableData = new int[rows][columns];
		createIds();
		resetRemovedRowsAndOrder();
	}
	
	private void createIds() {
		rowIds = new int[getRealRowCount()];
		for (int i = 0; i < getRealRowCount(); i++) {
			rowIds[i] = i + 1;
		}
		
		columnIds = new int[getColumnCount()];
		for (int i = 0; i < getColumnCount(); i++) {
			columnIds[i] = i + 1;
		}
	}

	/**
	 * creates/resets removed rows indexes
	 */
	private void initNotRemovedRowIndexes() {
		notRemovedRowIndexes = new ArrayList<Integer>();
		for (int i = 0; i < getRealRowCount(); i++) {
			notRemovedRowIndexes.add(i);
		}
	}
	
	/**
	 * Swaps rows with columns. "Removed" rows become reset (table grows to former size)
	 */
	public void transposeAndResetRemovedRows() {
		initNotRemovedRowIndexes();//TODO
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
		
		resetRemovedRowsAndOrder();
	}

	private void resetRemovedRowsAndOrder() {
		initNotRemovedRowIndexes();
		rowRemovalOrder = new int[getRealRowCount()];
	}
	
	public void resetRemovedRows() {
		initNotRemovedRowIndexes();
	}
	
	public int get(int row, int column) {
		int realRowIndex = notRemovedRowIndexes.get(row);
		return tableData[realRowIndex][column];
	}
	
	public void set(int value, int row, int column) {
		tableData[getRealRowIndex(row)][column] = value;
	}
	
	protected int getRealRowIndex(int row) {
		return notRemovedRowIndexes.get(row);
	}
	
	public void removeRow(int rowIndex) {
		int realRowIndex = getRealRowIndex(rowIndex);
		boolean elementExisted = notRemovedRowIndexes.remove(Integer.valueOf(realRowIndex));
		if (!elementExisted) {
			throw new RuntimeException("removed row does not exist");
		}
		rowRemovalOrder[realRowIndex] = currentRemovalOrder;
		currentRemovalOrder++;
	}
	
	public int getRemovalOrderForRow(int rowIndex) {
		return rowRemovalOrder[getRealRowIndex(rowIndex)];
	}
	
	public int[] getRowAsArray(int row) {
		return tableData[getRealRowIndex(row)];
	}
	
	/**
	 * @return count of table rows. removed rows excluded.
	 */
	public int getRowCount() {
		return notRemovedRowIndexes.size();
	}
	
	/**
	 * @return count of all table rows. removed included.
	 */
	protected int getRealRowCount() {
		return tableData.length;
	}
	
	public int getColumnCount() {
		return tableData[0].length;
	}
	
	/**
	 * Get iterator for column values in specified row
	 */
	public Iterator<Integer> getRowIterator(int rowIndex) {
		return new TableRowIterator(this, notRemovedRowIndexes.get(rowIndex));
	}
	
	public Iterator<Integer> getColumnIterator(int columnIndex) {
		return new TableColumnIterator(this, columnIndex);
	}
	
	/**
	 * Get iterator which iterates through all rows and has isRemoved variable in next() value
	 */
	public Iterator<ValueAndIsRemoved> getPlusColumnIterator(int columnIndex) {
		return new PlusColumnIterator(this, columnIndex);
	}
	
	public void setTableData(int[][] tableData) {
		this.tableData = tableData;
	}
	
	public int getRowId(int rowIndex) {
		return rowIds[getRealRowIndex(rowIndex)];
	}

	@Override
	public String toString() {
		return Util.twoDimensionalArrayToMultilineString(tableData)
				+ "\nrowIds: " + Arrays.toString(rowIds)
				+ "\ncolumnIds: " + Arrays.toString(columnIds);
				
	}

	/**
	 * Get element ignoring removed rows
	 */
	public Integer getReal(int rowIndex, int columnIndex) {
		return tableData[rowIndex][columnIndex];
	}
	
	public boolean isRowRemoved(int realRowIndex) {
		return !notRemovedRowIndexes.contains(Integer.valueOf(realRowIndex));
	}
	
	

}
