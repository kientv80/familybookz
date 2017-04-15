package com.vnsoftware.family.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vnsoftware.giapha.entirty.DumyDB;
import com.vnsoftware.giapha.entirty.Family;
import com.vnsoftware.giapha.entirty.Person;
import com.vnsoftware.giapha.entirty.Person.GENDER;
import com.vnsoftware.giapha.entirty.ShortProfile;
import com.vnsoftware.util.JSONHelper;

@Controller
public class FamilyBookzHandler extends BasedHandler {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(String id, HttpServletRequest req, ModelMap map) {
		Person p = (Person) req.getSession(true).getAttribute("profile");
		map.put("person", p);
		return "family";
	}

	@Async
	@RequestMapping(value = "/findMyRoot", method = RequestMethod.GET)
	public void findMyRoot(HttpServletResponse resp, HttpServletRequest req) {
		try {
			Person p = (Person) req.getSession(true).getAttribute("profile");
			Person root = null;
			root = DumyDB.getInstance().getMyFamilyTree(p, 2);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			JSONObject jobj = JSONHelper.toJSONObject(root);
			out.write(jobj.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	@RequestMapping(value = "/loadchildren", method = RequestMethod.GET)
	public void home(String id, HttpServletResponse resp) {
		long pid = Long.parseLong(id);
		try {
			Person p = DumyDB.getInstance().getPerson(pid, true);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			JSONObject jobj = JSONHelper.toJSONObject(p);
			out.write(jobj.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Async
	@RequestMapping(value = "/loadParent", method = RequestMethod.GET)
	public void loadParents(String id, HttpServletResponse resp) {
		long pid = Long.parseLong(id);
		try {
			Person p = DumyDB.getInstance().getPerson(pid, false);
			Person parents = null;
			if(p.getDad() > 0){
				parents = DumyDB.getInstance().getPerson(p.getDad(), true);
			}else if(p.getMom() > 0){
				parents = DumyDB.getInstance().getPerson(p.getMom(), true);
			}
			resp.setContentType("application/json");
			resp.setCharacterEncoding("utf-8");
			PrintWriter out = resp.getWriter();
			if(parents != null){
				JSONObject jobj = JSONHelper.toJSONObject(parents);
				out.write(jobj.toString());
				out.flush();
				out.close();
			}else{
				writeFailedResponse(resp, "No parent found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(String type, String firstname, String surname, String email, String username, String password, @RequestParam("image") MultipartFile file, HttpServletResponse resp, HttpServletRequest req) throws Exception {
		if (firstname == null || firstname.isEmpty() || surname == null || surname.isEmpty() || username == null || username.isEmpty() || password == null || password.isEmpty() || file == null) {
			return "redirect:/login";
		}

		String image = System.currentTimeMillis() + ".png";
		try {
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("D:\\Working\\projects\\familybookz\\webapps\\images\\tmp\\" + image)));
			stream.write(bytes);
			stream.close();

			Person p = new Person(0, firstname, surname, "1/1/2014", GENDER.FEMALE);
			p.setUserName(username);
			p.setPassWorld(password);
			if (file != null)
				p.setImage("/images/tmp/" + image);
			p.setEmail(email);
			long id = DumyDB.getInstance().addPerson(p);
			p = DumyDB.getInstance().getPerson(id, false);
			if (p != null) {
				Family f = new Family(p, null, p.getChildren());
				req.getSession(true).setAttribute("family", f);
				req.getSession(true).setAttribute("profile", f.getMe());
				return "redirect:/home";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}

	

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public void search(String searchText, HttpServletResponse resp) throws Exception {
		List<Person> result = DumyDB.getInstance().search(searchText);
		if (result != null && !result.isEmpty()) {
			Person p = result.get(0);
			ShortProfile sp = new ShortProfile(p.getId(), p.getName(),p.getUserName(), p.getImage());
			if (p != null) {
				try {
					JSONObject json = JSONHelper.toJSONObject(sp);
					writeJSONResponse(resp, json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			writeFailedResponse(resp, "Not found");
		}
	}

	@RequestMapping(value = "/family", method = RequestMethod.GET)
	public String family(String searchText, HttpServletResponse resp, HttpServletRequest req, ModelMap map) {
		Person p = (Person) req.getSession(true).getAttribute("profile");
		if (req.getSession(true).getAttribute("profile") != null) {
			map.put("loginUser", req.getSession(true).getAttribute("profile"));
		}
		map.put("person", p);
		return "family";
	}

}
