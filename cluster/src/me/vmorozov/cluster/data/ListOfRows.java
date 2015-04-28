package me.vmorozov.cluster.data;

import java.util.AbstractList;

/**
 * List of rows. Highly tied to a table it wraps. Mirrors changes there.
 * Use for sorting. By sum or removal order for example. Changes in row contents are not mirrored!
 * @author Vova
 *
 */
public class ListOfRows extends AbstractList<TableRow>{

	private ConformismTable table;
	
	public ListOfRows(ConformismTable table) {
		this.table = table;
	}
	
	@Override
	public TableRow get(int index) {
		TableRow row = new TableRow(
				table.getRowSum(index), 
				table.getRowAsArray(index),
				table.getRowId(index),
				table.getRemovalOrderForRow(index));
		return row;
	}

	@Override
	public int size() {
		return table.getRowCount();
	}
	
	@Override
	public TableRow set(int index, TableRow element) {
		TableRow previous = get(index);
		table.setFromRow(element, index);
		return previous;
	}

}
