package me.vmorozov.cluster.calculation;

import java.util.Iterator;

import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.ListOfRows;
import me.vmorozov.cluster.data.Table;

/**
 * Class for impact (mõju) clustering.
 * @author Vova
 *
 */
public class ImpactClustering extends ConformismClustering {

	@Override
	public ConformismTable compute(int[][] data, int availableValuesCount) {
		ConformismTable table = new ConformismTable(data);

		//compute for rows, then transpose to compute for columns, 
		//then transpose again to get initial table form
		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		computeForRows(table, availableValuesCount);
		table.transposeAndResetRemovedRows();
		
		return table;
	}
	
	@Override
	protected Table computeFrequenciesByColumns(ConformismTable table, int availableValuesCount) {
		Table frequencies = super.computeFrequenciesByColumns(table, availableValuesCount);
		//additional step is added compared to conformism
		substituteFrequencies(frequencies);
		return frequencies;
	}
	
	/**
	 * Substitutes frequencies as impact method demands
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

	/**
	 * Substitutes frequency as impact method demands
	 * @param frequency
	 * @return
	 */
	private int substuteFrequency(int frequency) {
		return 2 * ((int)Math.pow(frequency, 2)) - 3 * frequency + 1;
	}
	
	

}
