package com.eactive.eai.batch.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParamListGenerator {

	public ParamListGenerator() {
		
	}

	public static void main(String[] args) {
		List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
		for(int i=0; i<10; i++) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("param1", "value" +i);
			paramList.add(map);
		}
		String paramName = "code1";
		String[] valueList = "123,456,789".split(",");
		paramList = addParameter(paramList, paramName, valueList);
		printParamList(paramList);
		
		paramName = "date1";
		valueList = "20210827,20210828".split(",");
		paramList = addParameter(paramList, paramName, valueList);
		printParamList(paramList);
		
		paramName = "date2";
		valueList = "20210927,20210928".split(",");
		paramList = addParameter(paramList, paramName, valueList);
		printParamList(paramList);
	}
	
	public static List<Map<String, String>> addParameter(List<Map<String, String>> paramList, String paramName, String[] valueArray) {
		List<Map<String, String>> addedParamList = new ArrayList<Map<String, String>>();
		for(Map<String, String> map : paramList) {
			for(String value : valueArray) {
				Map<String, String> newMap = new LinkedHashMap<String, String>();
				newMap.putAll(map);
				newMap.put(paramName, value);
				addedParamList.add(newMap);
			}
		}
		return addedParamList;
	}
	
	public static void printParamList(List<Map<String, String>> paramList) {
		int i = 0;
		System.out.println( "\n>> printParamList");
		for(Map<String, String> map: paramList) {
			System.out.println( i + " -> " + map );
			i++;
		}
	}
}
