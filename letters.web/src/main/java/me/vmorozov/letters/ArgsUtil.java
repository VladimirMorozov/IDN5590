package me.vmorozov.letters;

import java.util.*;

public class ArgsUtil {

	
	public static Map<String, String> getParameterMap(String[] args, List<String> mandatoryArgs) {
		Map<String, String> result = new TreeMap<String, String>();
		List<String> argsList = Arrays.asList(args);
		Iterator<String> iterator = argsList.iterator();
		while(iterator.hasNext()) {
			String argName = iterator.next();
			if (!iterator.hasNext()) {
				throw new RuntimeException("cannot parse arguments");
			}
			String argValue = iterator.next();
			if (argValue.startsWith("\"")) {
				if (argValue.endsWith("\"")) {
					argValue = argValue.substring(1, argValue.length());
				} else {
					if (!iterator.hasNext()) {
						throw new RuntimeException("cannot parse arguments");
					}
					String nextValuePart = iterator.next();
					while (!nextValuePart.endsWith("\"")) {
						if (!iterator.hasNext()) {
							throw new RuntimeException("cannot parse arguments");
						}
						nextValuePart += " " + iterator.next();
					}
					argValue += nextValuePart;
					argValue = argValue.substring(1, argValue.length());
				}
				
			}
		
		result.put(argName, argValue);
		}
		
		for (String mandatoryArgName : mandatoryArgs) {
			if (!result.containsKey(mandatoryArgName)) {
				throw new RuntimeException("argument " + mandatoryArgName + " is mandatory");
			}
		}
		
		return result;
	}
}
