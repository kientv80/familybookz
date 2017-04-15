package com.vnsoftware.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ObjectUtil {
	private static final String NEWLINE = "\n";
	@SuppressWarnings("rawtypes")
	public static Map<Class,Map<String, Method>> classDefs = new HashMap<>();
	public static String objectToJSONDataTable(List<Object> data, List<String> columns, String dataTableVar) throws Exception {
		if(data== null || data.size() == 0){
			System.out.println("Data table is empty");
			return "";
		}
		Map<String, Method> methodMap = getMethods(data.get(0));
		StringBuilder dataTable = new StringBuilder();
		dataTable.append("var "+dataTableVar +" = [").append(NEWLINE);
		for(Object obj: data){
			StringBuilder row = new StringBuilder();
			row.append("{").append(NEWLINE);
			for(String col: columns){
				Method m = methodMap.get("get"+col);
				if(m==null)
					throw new InvalidColumnException("get"+ col + " is not defined in class " + obj.getClass());
				
				row.append(col).append(":").append("'"+getValue(obj, m)).append("',");
			}
			dataTable.append(row.substring(0,row.length()-1)).append(NEWLINE).append("},").append(NEWLINE);
		}
		StringBuilder result = new StringBuilder(dataTable.substring(0,dataTable.length()-2)+"];"+NEWLINE);
		return result.toString();
	}
	private static Object getValue(Object obj, Method m) throws Exception{
		return m.invoke(obj, null);
	}
	private static Map<String, Method> getMethods(Object obj){
		Map<String, Method> methodMap = classDefs.get(obj.getClass());
		if(methodMap == null){
			methodMap = new HashMap<String, Method>();
			Method[] ms = obj.getClass().getDeclaredMethods();
			for (Method m : ms) {
				methodMap.put(m.getName(), m);
			}
			classDefs.put(obj.getClass(), methodMap);
		}
		return methodMap;
	}
	public static void main(String[] args) {
	}
}
