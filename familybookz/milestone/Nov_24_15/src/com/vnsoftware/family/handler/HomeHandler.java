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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vnsoftware.facebook.FacebookService;
import com.vnsoftware.giapha.entirty.DumyDB;
import com.vnsoftware.giapha.entirty.Family;
import com.vnsoftware.giapha.entirty.Person;
import com.vnsoftware.giapha.entirty.Person.GENDER;
import com.vnsoftware.giapha.entirty.Relation;
import com.vnsoftware.giapha.entirty.RelationRequest;
import com.vnsoftware.giapha.entirty.ShortProfile;
import com.vnsoftware.util.JSONHelper;

@Controller
public class HomeHandler extends BasedHandler {

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
			root = DumyDB.getInstance().getFamilyTree(p, 2);
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

	@RequestMapping(value = "/addrelation", method = RequestMethod.POST)
	public void addRelation(String personId, String type, String firstname, String surname, String desc, @RequestParam("image") MultipartFile file, HttpServletResponse resp, HttpServletRequest req) throws Exception {
		Person p = (Person) req.getSession(true).getAttribute("profile");
		Person myP = null;
		long currentId = p.getId();
		long ownerId = p.getId();
		if (file.isEmpty()) {
			writeFailedResponse(resp, "Image is required");
			return;
		}
		if (personId != null && isNumber(personId)) {
			long id = Long.valueOf(personId);
			if (id != p.getId()) {
				myP = DumyDB.getInstance().getPerson(id, false);
				if (myP.getOwnerId() != p.getId()) {// some one cheat me
					writeFailedResponse(resp, "Invalid data");
					return;
				}
				p = myP;
			}
		}

		if (validateData(type, resp, p)) {
			String image = System.currentTimeMillis() + ".png";
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("D:\\Working\\projects\\familybookz\\webapps\\images\\tmp\\" + image)));
				stream.write(bytes);
				stream.close();

				Person relationPerson = new Person(0, firstname, surname, "1/1/2014", GENDER.FEMALE);
				relationPerson.setOwnerId(ownerId);
				relationPerson.setImage("/images/tmp/" + image);
				if ("kid".equals(type) && GENDER.MALE.equals(p.getGender())) {
					relationPerson.setDad(p.getId());
				} else if ("kid".equals(type) && GENDER.FEMALE.equals(p.getGender())) {
					relationPerson.setMom(p.getId());
				}
				if ("dad".equals(type) || "mom".equals(type)) {
					relationPerson.setChildIds(p.getId() + "");
				}
				if ("dad".equals(type)) {
					relationPerson.setGender(GENDER.MALE);
				}
				long id = DumyDB.getInstance().addPerson(relationPerson);
				relationPerson.setId(id);

				if ("mom".equals(type)) {
					p.setMom(relationPerson.getId());
				} else if ("dad".equals(type)) {
					p.setDad(relationPerson.getId());
				} else if ("wife".equals(type)) {
					if (p.getWifeorhusbandIds().isEmpty())
						p.setWifeorhusbandIds(relationPerson.getId() + "");
					else
						p.setWifeorhusbandIds(p.getWifeorhusbandIds() + "," + relationPerson.getId() + "");
				} else if ("husband".equals(type)) {
					if (p.getWifeorhusbandIds().isEmpty())
						p.setWifeorhusbandIds(relationPerson.getId() + "");
					else
						p.setWifeorhusbandIds(p.getWifeorhusbandIds() + "," + relationPerson.getId() + "");
				} else if ("kid".equals(type)) {
					if (p.getChildIds() == null || p.getChildIds().isEmpty())
						p.setChildIds(relationPerson.getId() + "");
					else
						p.setChildIds(p.getChildIds() + "," + relationPerson.getId() + "");
				}
				DumyDB.getInstance().updateRelation(p);
				req.getSession(true).setAttribute("profile", DumyDB.getInstance().getPerson(currentId, true));
				// JSon to update on family tree
				Relation r = new Relation(p.getId(), relationPerson);
				r.setType(type);
				writeJSONResponse(resp, JSONHelper.toJSONObject(r));

			} catch (Exception e) {
				e.printStackTrace();
				writeFailedResponse(resp, "System error please contact admin");
				return;
			}
		}

	}

	private boolean validateData(String type, HttpServletResponse resp, Person p) {
		if (!"mom".equals(type) && !"dad".equals(type) && !"kid".equals(type) && !"wife".equals(type) && !"husband".equals(type)) {
			writeFailedResponse(resp, "Invalid type");
			return false;
		}
		if ("mom".equals(type) && p.getMom() > 0) {
			// Already have mom
			writeFailedResponse(resp, "Already have mom");
			return false;
		}
		if ("dad".equals(type) && p.getDad() > 0) {
			// Already have dad
			writeFailedResponse(resp, "Already have dad");
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/addrelation", method = RequestMethod.GET)
	public void addRelation(String personId, String relationId, String type, HttpServletResponse resp, HttpServletRequest req) throws Exception {
		Person p = (Person) req.getSession(true).getAttribute("profile");
		if (relationId != null && isNumber(relationId)) {
			Person myP = null;
			if (personId != null && isNumber(personId)) {
				long id = Long.valueOf(personId);
				if (id != p.getId()) {
					myP = DumyDB.getInstance().getPerson(id, false);
					if (myP.getOwnerId() != p.getId()) {// some one cheat me
						writeFailedResponse(resp, "Invalid data");
						return;
					}
				}
			}

			if (validateData(type, resp, p)) {
//				Person relation = DumyDB.getInstance().getPersonByFBId(Long.valueOf(relationId), false);
				Person relation = DumyDB.getInstance().getPerson(Long.valueOf(relationId), false);
				if (relation == null) {
					writeFailedResponse(resp, "Invalid data");
					return;
				} else {
					DumyDB.getInstance().createRelationRequest(new RelationRequest(0, p.getId(), relation.getId(), p.getFirstName() + " " + p.getSurname() + " want to add you as a " + type, type, "requesting", p.getFirstName(), p.getImage()));
					// JSon to update on family tree
					writeSuccessResponse(resp, "Successfully send request");
				}
			}
		}

	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public void search(String searchText, HttpServletResponse resp) throws Exception {
		List<Person> result = DumyDB.getInstance().search(searchText);
		if (result != null && !result.isEmpty()) {
			Person p = result.get(0);
			ShortProfile sp = new ShortProfile(p.getId(), p.getName(), p.getImage());
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

	@RequestMapping(value = "/relationrequest", method = RequestMethod.GET)
	public String getRelationRequests(HttpServletRequest req, ModelMap map) throws Exception {
		Person p = (Person) req.getSession(true).getAttribute("profile");
		List<RelationRequest> requests = DumyDB.getInstance().getRetationRequests(p.getId());
		map.put("requests", requests);
		return "relationrequest";
	}

	@RequestMapping(value = "/rconfirm", method = RequestMethod.GET)
	public String confirmRelationRequests(String id, HttpServletRequest req, ModelMap map) throws Exception {
		Person p = (Person) req.getSession(true).getAttribute("profile");
		if (isNumber(id)) {
			RelationRequest requests = DumyDB.getInstance().getRetationRequest(Integer.valueOf(id));
			if (requests != null) {
				DumyDB.getInstance().confirmRequest(requests.getId());
			}
		}
		List<RelationRequest> requests = DumyDB.getInstance().getRetationRequests(p.getId());
		map.put("requests", requests);
		return "relationrequest";
	}

	@RequestMapping(value = "/rdelete", method = RequestMethod.GET)
	public String deleteRelationRequests(String id, HttpServletRequest req, ModelMap map) throws Exception {
		Person p = (Person) req.getSession(true).getAttribute("profile");
		if (isNumber(id)) {
			RelationRequest requests = DumyDB.getInstance().getRetationRequest(Integer.valueOf(id));
			if (requests != null) {
				DumyDB.getInstance().updateRequestStatus(requests.getId(), "delete");
			}
		}
		List<RelationRequest> requests = DumyDB.getInstance().getRetationRequests(p.getId());
		map.put("requests", requests);
		return "relationrequest";
	}
	@Async
	@ResponseBody
	@RequestMapping(value = "/fbfriends", method = RequestMethod.GET)
	public void fbfriends(HttpServletRequest req,HttpServletResponse resp, ModelMap map) throws Exception {
		try {
			Person p = (Person) req.getSession(true).getAttribute("profile");
			List<ShortProfile> friends = FacebookService.getInstance().getFBFriends(p);
			writeJSONResponse(resp, JSONHelper.toJSONArray(friends));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
