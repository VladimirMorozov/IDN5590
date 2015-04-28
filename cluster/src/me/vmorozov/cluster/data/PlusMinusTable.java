package me.vmorozov.cluster.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds class information. Holds indexes of rows/columns from which new classes start.
 * @author Vova
 *
 */
public class PlusMinusTable extends ConformismTable {

	/** used to display new classes*/
	private List<Integer> newRowClassIndexes = new ArrayList<Integer>();
	
	/** used to display new classes*/
	private List<Integer> newColumnClassIndexes = new ArrayList<Integer>();
	
	
	public PlusMinusTable(int[][] tableData) {
		super(tableData);
	}
	
	@Override
	public void transposeAndResetRemovedRows() {
		super.transposeAndResetRemovedRows();
		
		List<Integer> swap3 = newRowClassIndexes;
		newRowClassIndexes = newColumnClassIndexes;
		newColumnClassIndexes = swap3;
	}
	
	public void addNewRowClassIndex(int index) {
		newRowClassIndexes.add(index);
	}

	
	@Override
	public String toString() {
		return super.toString() + "\n" + "newRowClassIndexes=" + newRowClassIndexes
				+ ", newColumnClassIndexes=" + newColumnClassIndexes;
	}
	
	
	
	
	
	

}
