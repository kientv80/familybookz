package com.vnsoftware.family.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vnsoftware.util.ValidationHelper;

public class BasedHandler {
	enum RESULT{SUCCESS,FAILED};
	public void writeSuccessResponse(HttpServletResponse resp, String message){
		try {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			JSONObject jobj = new JSONObject();
			jobj.put("errorCode", RESULT.SUCCESS.ordinal());
			jobj.put("msg", message);
			out.write(jobj.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeFailedResponse(HttpServletResponse resp, String message){
		try {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			JSONObject jobj = new JSONObject();
			jobj.put("errorCode", RESULT.FAILED.ordinal());
			jobj.put("msg", message);
			out.write(jobj.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeJSONResponse(HttpServletResponse resp, JSONObject json){
		try {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeJSONResponse(HttpServletResponse resp, JSONArray json){
		try {
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isNumber(String num){
		return ValidationHelper.isNumber(num);
	}
}
