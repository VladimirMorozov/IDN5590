package me.vmorozov.cluster.calculation;

import me.vmorozov.cluster.data.Table;

public interface Clustering {
	
	Table compute(int[][] data, int availableValuesCount);

}
