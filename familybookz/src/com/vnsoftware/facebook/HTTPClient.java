package com.vnsoftware.facebook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HTTPClient {

	public static String executeHttpPost(String targetUrl, List<Param> postParams) throws IOException {
		URL url = new URL(targetUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Param p : postParams) {
			params.add(new BasicNameValuePair(p.getName(), p.getValue()));
		}
		connection.setDoInput(true);
		connection.setDoOutput(true);
		try {
			// Send request
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(getQuery(params));
			writer.flush();
			writer.close();
			os.close();
			BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String c2 = "";
			StringBuilder result = new StringBuilder();
			while ((c2 = r.readLine()) !=null) {
				result.append(c2);
			}
			r.close();
			System.out.println("Response = " + result.toString());
			return result.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return null;
	}

	public static String executeHttpGet(String targetUrl, List<Param> postParams) throws IOException {
		URL url = new URL(targetUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(postParams != null){
			for (Param p : postParams) {
				params.add(new BasicNameValuePair(p.getName(), p.getValue()));
			}
		}
		connection.setDoInput(true);
		connection.setDoOutput(true);

		// Send request
		OutputStream os = connection.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(getQuery(params));
		writer.flush();
		writer.close();
		os.close();
		
		BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		String c2 = "";
		StringBuilder result = new StringBuilder();
		while ((c2 = r.readLine()) !=null) {
			result.append(c2);
		}
		r.close();
		System.out.println("Response = " + result.toString());
		return result.toString();
	}

	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}
}
