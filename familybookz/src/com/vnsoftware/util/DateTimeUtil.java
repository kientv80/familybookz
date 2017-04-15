package com.vnsoftware.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {
	public static final SimpleDateFormat yy = new SimpleDateFormat("YY");
	public static final SimpleDateFormat yyyyMM = new SimpleDateFormat("YY-MM");
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("YY-MM-dd");
	public static final SimpleDateFormat yyyyMMWW = new SimpleDateFormat("YY-MM-WW");
	public static final SimpleDateFormat yyyyMMddHH = new SimpleDateFormat("YY-MM-dd:HH");
	public static final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	public static List<String> getHoursOfToday(){
		String today = DateTimeUtil.yyyyMMdd.format(Calendar.getInstance().getTime());
		List<String> hours = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				hours.add(today + ":0" + i);
			} else {
				hours.add(today + ":" + i);
			}
		}
		return hours;
	}
	public static List<String> getHoursOfYesterDay(){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, day - 1);
		String yesterday = DateTimeUtil.yyyyMMdd.format(c.getTime());
		List<String> hours = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			hours.add(yesterday + ":" + i);
		}
		return hours;
	}
	public static List<String> getDaysOfThisWeek(){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		List<String> days = new ArrayList<>();
		while (weekDay > 0) {
			c.set(Calendar.DAY_OF_MONTH, day - (weekDay - 1));
			String tmp = DateTimeUtil.yyyyMMdd.format(c.getTime());
			days.add(tmp);
			weekDay--;
		}
		return days;
	}
	public static List<String>  getDayOfLastWeek(){
		Calendar c = Calendar.getInstance();
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		c.setTimeInMillis(c.getTimeInMillis() - (((weekDay - 1) * 24 * 60 * 60 * 1000) + (7 * 24 * 60 * 60 * 1000)));
		List<String> days = new ArrayList<>();
		String tmp = DateTimeUtil.yyyyMMdd.format(c.getTime());
		days.add(tmp);
		for (int i = 1; i < 7; i++) {
			c.setTimeInMillis(c.getTimeInMillis() + 24 * 60 * 60 * 1000);
			tmp = DateTimeUtil.yyyyMMdd.format(c.getTime());
			days.add(tmp);
		}
		System.out.println(days);
		return days;
	}
	public static List<String> getDayOfThisMonth(){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, 0);
		List<String> days = new ArrayList<>();
		for (int i = 0; i < day; i++) {
			c.setTimeInMillis(c.getTimeInMillis() + 24 * 60 * 60 * 1000);
			String tmp = DateTimeUtil.yyyyMMdd.format(c.getTime());
			days.add(tmp);
		}
		System.out.println(days);
		return days;
	}
	public static List<String> getDayOfLastMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
		c.set(Calendar.DAY_OF_MONTH,0);
		int daysOfLastMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(daysOfLastMonth);
		List<String> days = new ArrayList<>();
		String tmp = DateTimeUtil.yyyyMMdd.format(c.getTime());
		for(int i=0;i<daysOfLastMonth;i++){
			c.setTimeInMillis(c.getTimeInMillis() + 24*60*60*1000);
			tmp = DateTimeUtil.yyyyMMdd.format(c.getTime());
			days.add(tmp);
		}
		System.out.println(days);
		return days;
	}
	public static List<String> getMonthsOfThisYear(){
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		String year = DateTimeUtil.yy.format(new Date(System.currentTimeMillis()));
		List<String> days = new ArrayList<>();
		for (int i = 1; i < month + 2; i++) {
			if (i < 10) {
				days.add(year + "-0" + i);
			} else {
				days.add(year + "-" + i);
			}
		}
		return days;
	}
	public List<String> getMonthsOfLastYear(){
		return null;
	}
	public static void main(String[] args) {
//		System.out.println(DateTimeUtil.yyyyMM.format(new Date(System.currentTimeMillis())));
//		System.out.println(DateTimeUtil.yyyyMMWW.format(new Date(System.currentTimeMillis())));
//		System.out.println(DateTimeUtil.yyyyMMdd.format(new Date(System.currentTimeMillis())));
//		System.out.println(DateTimeUtil.yyyyMMddHH.format(new Date(System.currentTimeMillis())));
		System.out.println(DateTimeUtil.getDayOfThisMonth());
	}
}
