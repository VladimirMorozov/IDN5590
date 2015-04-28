package me.vmorozov.cluster.data;

import java.util.Iterator;

/**
 * Iterates through row values on a fixed column
 * @author Vova
 *
 */
public class TableColumnIterator implements Iterator<Integer> {

	protected Table table;
	protected int rowIndex;
	protected final int columnIndex;
	
	public TableColumnIterator(Table table, int columnIndex) {
		this.table = table;
		this.columnIndex = columnIndex;
	}
	
	@Override
	public boolean hasNext() {
		if (rowIndex  < table.getRowCount()) {
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
