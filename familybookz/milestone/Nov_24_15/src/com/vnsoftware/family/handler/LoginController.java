package com.vnsoftware.family.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vnsoftware.facebook.FacebookService;
import com.vnsoftware.giapha.entirty.DumyDB;
import com.vnsoftware.giapha.entirty.Family;

@Controller
public class LoginController {

	Logger log = Logger.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse resp) {
		if (request.getSession(true).getAttribute("profile") != null) {
			return "redirect:/home";
		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, HttpServletRequest request, HttpServletResponse resp) throws Exception {
		Family f = DumyDB.getInstance().login(username, password);
		if (f != null) {
			request.getSession(true).setAttribute("family", f);
			request.getSession(true).setAttribute("profile", f.getMe());
			return "redirect:/home";
		}
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse resp) {
		request.getSession(true).removeAttribute("profile");
		return "login";
	}

	@RequestMapping(value = "/fblogin", method = RequestMethod.GET)
	public void fblogin(HttpServletRequest request, HttpServletResponse resp) {
		FacebookService.getInstance().fblogin(request, resp);
	}

	@RequestMapping(value = "/fblogincallback", method = RequestMethod.GET)
	public void fblogincallback(HttpServletRequest req, HttpServletResponse resp) {
		FacebookService.getInstance().fblogincallback(req, resp);
	}
}
