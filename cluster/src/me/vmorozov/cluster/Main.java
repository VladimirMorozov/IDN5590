package me.vmorozov.cluster;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.vmorozov.cluster.calculation.Clustering;
import me.vmorozov.cluster.calculation.ConformismClustering;
import me.vmorozov.cluster.calculation.ImpactClustering;
import me.vmorozov.cluster.calculation.MinusClustering;
import me.vmorozov.cluster.calculation.PlusClustering;
import me.vmorozov.cluster.calculation.PlusMinusClustering;
import me.vmorozov.cluster.data.ConformismTable;
import me.vmorozov.cluster.data.Table;

public class Main {

	public static void main(String[] args) {
		
		Map<String, String> argsMap = ArgsUtil.getParameterMap(args, Arrays.asList("-i", "-m"));
		String inputFilepath = argsMap.get("-i");
		String outputFilepath = argsMap.getOrDefault("-o", getDefaultOutputPath());
		String method = argsMap.get("-m");
		
		int[][] table = FileUtil.readTable(inputFilepath);
		
		Clustering clustering = getClusteringByName(method);
		long startTime = System.currentTimeMillis();
		Table result = clustering.compute(table, 10);
		System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + " ms");
		
		String output = "";
		if (method.equals("minus")) {
			output += "NOTE: not default sorting! Ordered by removal order descending  (rows, which were removed first are in the end. same for columns)";
		}
		output += "Result:\n";
		output += result.toString();
		
		FileUtil.write(output, outputFilepath);
		
	}
	
	private static Clustering getClusteringByName(String name) {
		switch (name) {
		case "plus":
			return new PlusClustering();
		case "minus":
			return new MinusClustering();
		case "impact":
			return new ImpactClustering();
		case "conformism":
			return new ConformismClustering();
		default:
			throw new RuntimeException("unknown method: " + name);
		}
	}
	
	private static String getDefaultOutputPath() {
		return new File("").getAbsolutePath()+"\\clusterOutput.txt";
	}

}
