package com.vnsoftware.family.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.vnsoftware.giapha.entirty.Person;
import com.vnsoftware.giapha.entirty.Person.GENDER;
import com.vnsoftware.giapha.entirty.Relation;
import com.vnsoftware.giapha.entirty.RelationRequest;
import com.vnsoftware.giapha.entirty.ShortProfile;
import com.vnsoftware.util.JSONHelper;

@Controller
public class RelationHandler extends BasedHandler {


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
				relationPerson.setShortDetail(desc);
				if ("dad".equals(type)) {
					relationPerson.setGender(GENDER.MALE);
				}
				long id = DumyDB.getInstance().addPerson(relationPerson);
				DumyDB.getInstance().addRelation(p.getId(), id, type, relationPerson.getGender());
				
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
