package me.vmorozov.cluster.calculation;

import me.vmorozov.cluster.data.ConformismTable;

public class MinusClustering extends PlusMinusClustering {

	@Override
	public void removeRowFromTable(ConformismTable table) {
		table.removeRowWithLowestSum();
	}

}
