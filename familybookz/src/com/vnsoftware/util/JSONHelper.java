package com.vnsoftware.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONHelper {
	public static JSONObject toJSONObject(Object obj) throws JSONException{
		Gson gson =  new GsonBuilder().setPrettyPrinting().create();
		JSONObject json = new JSONObject(gson.toJson(obj));
		return json;
	}
	public static JSONArray toJSONArray(Collection<?> list) throws JSONException{
		Gson gson =  new GsonBuilder().setPrettyPrinting().create();
		JSONArray ja = new JSONArray(gson.toJson(list));
		return ja;
	}
	public static Object fromJSONObject(Class<?> clazz, JSONObject json){
		Gson gson = new Gson();
        Object obj = gson.fromJson(json.toString(), clazz);
        return obj;
	}
	
	public static List<?> fromJSONArray(Class<?> clazz, JSONArray json) throws JSONException{
		Gson gson = new Gson();
		List objs = new ArrayList();
		for(int i = 0;i<json.length();i++){
			objs.add(fromJSONObject(clazz, json.getJSONObject(i)));
		}
        return objs;
	}
}
