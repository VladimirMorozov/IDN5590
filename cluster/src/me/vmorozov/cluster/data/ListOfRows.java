package me.vmorozov.cluster.data;

import java.util.AbstractList;

/**
 * List of rows is highly tied to a table it wraps and mirrors changes there.
 * Use for sorting.
 * @author Vova
 *
 */
public class ListOfRows extends AbstractList<RowWithSum>{

	private ConformismTable table;
	
	public ListOfRows(ConformismTable table) {
		this.table = table;
	}
	
	@Override
	public RowWithSum get(int index) {
		RowWithSum row = new RowWithSum(
				table.getRowSum(index), 
				table.getRowAsArray(index),
				table.getRowId(index));
		return row;
	}

	@Override
	public int size() {
		return table.getRowCount();
	}
	
	@Override
	public RowWithSum set(int index, RowWithSum element) {
		RowWithSum previous = get(index);
		table.setRowWithSum(element, index);
		return previous;
	}

}
