package com.vnsoftware.family.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vnsoftware.giapha.entirty.DumyDB;
import com.vnsoftware.giapha.entirty.OfflineEvent;
import com.vnsoftware.giapha.entirty.Person;
import com.vnsoftware.util.JSONHelper;

@Controller
public class OfflineEventHandler extends BasedHandler{
	
	@RequestMapping(value = "/offlineevent", method = RequestMethod.GET)
	@ResponseBody
	public void offlineEvent(HttpServletResponse resp, HttpServletRequest req) {
		Person p = (Person)req.getSession(true).getAttribute("profile");
		try {
			OfflineEvent oEvents = DumyDB.getInstance().getOfflineEvents(p.getId());
			writeJSONResponse(resp, JSONHelper.toJSONObject(oEvents));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/clearofflineFeeds", method = RequestMethod.GET)
	@ResponseBody
	public void clearofflineFeeds(HttpServletResponse resp, HttpServletRequest req) {
		Person p = (Person)req.getSession(true).getAttribute("profile");
		try {
			 DumyDB.getInstance().clearofflineFeeds(p.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
