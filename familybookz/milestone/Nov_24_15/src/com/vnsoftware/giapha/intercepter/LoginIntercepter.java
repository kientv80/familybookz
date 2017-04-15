package com.vnsoftware.giapha.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginIntercepter extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println(">>>>>>>>>>>> LoginIntercepter");
		if( !request.getRequestURI().contains("login") && !request.getRequestURI().contains("register")  && request.getSession(true).getAttribute("profile") == null){
			response.sendRedirect("/login");
			return false;
		}
		return true;
	}
}
