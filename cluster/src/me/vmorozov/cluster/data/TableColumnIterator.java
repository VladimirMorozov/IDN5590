package me.vmorozov.cluster.data;

import java.util.Iterator;

public class TableColumnIterator implements Iterator<Integer> {

	private Table table;
	private int rowIndex;
	private final int columnIndex;
	
	public TableColumnIterator(Table table, int columnIndex) {
		this.table = table;
		this.columnIndex = columnIndex;
	}
	
	@Override
	public boolean hasNext() {
		if (rowIndex + 1 <= table.getRowCount()) {
			return true;
		}
		return false;
	}

	@Override
	public Integer next() {
		Integer nextValue = table.get(rowIndex, columnIndex);
		rowIndex++;
		return nextValue;
	}

}
