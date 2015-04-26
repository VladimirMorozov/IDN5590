package me.vmorozov.cluster;

import java.util.Arrays;

import me.vmorozov.cluster.calculation.Clustering;
import me.vmorozov.cluster.calculation.ConformismClustering;
import me.vmorozov.cluster.calculation.ImpactClustering;
import me.vmorozov.cluster.calculation.MinusClustering;
import me.vmorozov.cluster.calculation.PlusMinusClustering;
import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.Table;

public class Main {
	
	private static final String DEFAULT_PATH = "D:\\input.txt";

	public static void main(String[] args) {
		String path;
		if (args.length == 1) {
			path = args[0];
		} else {
			path = DEFAULT_PATH;
		}
		
		int[][] table = FileUtil.readTable(path);
		
		Clustering clustering = new MinusClustering();
		Table result = clustering.compute(table, 10);
		
		String output = "Result:\n";
		output += result.toString();
		
		FileUtil.write(output);
		
	}

}
