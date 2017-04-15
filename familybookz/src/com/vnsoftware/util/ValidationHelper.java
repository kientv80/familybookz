package com.vnsoftware.util;

import java.text.SimpleDateFormat;



public class ValidationHelper {
	public static boolean isNumber(String number){
		try {
			Long.parseLong(number);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	public static boolean isDouble (String text){
		try {
			Double.parseDouble(text);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");
	public static boolean isValidyyyyMMddDate (String date){
		try {
			System.out.println(yyyyMMdd.parse(date));
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	public static boolean isValidChars(String text){
		return true;
	}
	public static void main(String[] args) {
//		System.out.println(ValidationHelper.isNumber("123.00"));
//		System.out.println(ValidationHelper.isDouble("123.00"));
//		System.out.println(ValidationHelper.isValidyyyyMMddDate("2013/10/283"));
		Integer.parseInt("10153492241103614");
	}
}
