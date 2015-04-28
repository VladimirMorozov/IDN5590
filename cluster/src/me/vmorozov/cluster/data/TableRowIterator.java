package me.vmorozov.cluster.data;

import java.util.Iterator;

/**
 * Iterates through column values on a fixed row
 * @author Vova
 *
 */
public class TableRowIterator implements Iterator<Integer> {

	private Table table;
	private int columnIndex;
	private final int rowIndex;
	
	public TableRowIterator(Table table, int rowIndex) {
		this.table = table;
		this.rowIndex = rowIndex;
	}
	
	@Override
	public boolean hasNext() {
		if (columnIndex + 1 <= table.getColumnCount()) {
			return true;
		}
		return false;
	}

	@Override
	public Integer next() {
		Integer nextValue = table.get(rowIndex, columnIndex);
		columnIndex++;
		return nextValue;
	}

}
