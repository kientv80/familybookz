package com.vnsoftware.util;

import javax.servlet.http.HttpServletRequest;



public class TargetingUtil {
	enum DEVICE_TYPE{MOBILE,}
	public static boolean isMobilePhone(HttpServletRequest req){
		try {
			Object deviceType = req.getSession(true).getAttribute("deviceType");
			if(deviceType!=null && "mobile".equals(deviceType)){
				return true;
			}else if(deviceType == null){
				UAgentInfo u = new UAgentInfo(req.getHeader("user-agent"), req.getHeader("accept"));
				if(u.isMobilePhone){
					req.getSession(true).setAttribute("deviceType", "mobile");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
