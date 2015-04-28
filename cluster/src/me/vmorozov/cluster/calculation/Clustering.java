package me.vmorozov.cluster.calculation;

import me.vmorozov.cluster.data.Table;

/**
 * Main interface for finding data clusters
 * @author Vova
 *
 */
public interface Clustering {
	
	/**
	 * @param data
	 * @param availableValuesCount number of available values in table. for example 10
	 * @return Table containing all computed information. Specific class can be defined by sublass of clustering
	 */
	Table compute(int[][] data, int availableValuesCount);

}
