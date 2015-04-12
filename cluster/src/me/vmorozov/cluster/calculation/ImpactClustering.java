package me.vmorozov.cluster.calculation;

import java.util.Iterator;

import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.ListOfRows;
import me.vmorozov.cluster.data.Table;

public class ImpactClustering extends ConformismClustering {

	@Override
	public ConformismTable compute(int[][] data, int availableValuesCount) {
		ConformismTable table = new ConformismTable(data);

		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		
		return table;
	}
	
	@Override
	protected Table computeFrequenciesByColumns(ConformismTable table, int availableValuesCount) {
		Table frequencies = super.computeFrequenciesByColumns(table, availableValuesCount);
		substituteFrequencies(frequencies);
		return frequencies;
	}
	
	/**
	 * @param frequencies [columnIndex][value]
	 */
	private void substituteFrequencies(Table frequencies) {
		for (int row = 0; row < frequencies.getRowCount(); row++) {
			for (int column = 0; column < frequencies.getColumnCount(); column++) {
				int frequency = frequencies.get(row, column);
				frequency = substuteFrequency(frequency);
				frequencies.set(frequency, row, column);
			}
		}
	}

	private int substuteFrequency(int frequency) {
		return 2 * ((int)Math.pow(frequency, 2)) - 3 * frequency + 1;
	}
	
	

}
