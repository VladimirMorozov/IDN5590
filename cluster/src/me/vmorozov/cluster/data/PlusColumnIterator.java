package me.vmorozov.cluster.data;

import java.util.Iterator;


/**
 * Iterates through real, not removed rows. Ugly way to make Plus Method work.
 * @author Vova
 *
 */
public class PlusColumnIterator implements Iterator<ValueAndIsRemoved> {

	protected Table table;
	protected int rowIndex;
	protected final int columnIndex;
	
	public PlusColumnIterator(Table table, int columnIndex) {
		this.table = table;
		this.columnIndex = columnIndex;
	}
	

	@Override
	public boolean hasNext() {
		if (rowIndex  < table.getRealRowCount()) {
			return true;
		}
		return false;
	}
	
	@Override
	public ValueAndIsRemoved next() {
		Integer nextValue = table.getReal(rowIndex, columnIndex);
		ValueAndIsRemoved next = new ValueAndIsRemoved(nextValue, table.isRowRemoved(rowIndex));
		rowIndex++;
		return next;
	}
}
