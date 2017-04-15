package com.vnsoftware.facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.vnsoftware.giapha.entirty.DumyDB;
import com.vnsoftware.giapha.entirty.Family;
import com.vnsoftware.giapha.entirty.Person;
import com.vnsoftware.giapha.entirty.ShortProfile;

public class FacebookService {
	private static FacebookService instance;
	private static final String clientId = "1489327961379891";
	private static final String key = "553e0a5ed5fa3c1ede22b7df3f5d7e43";
	Logger log = Logger.getLogger(FacebookService.class);
	public static FacebookService getInstance(){
		if(instance == null)
			instance = new FacebookService();
		return instance;
	}
	
	public void postFeedLink(FBLinkFeed feed, String accessToken, String pageId){
		try {
			List<Param> params = new ArrayList<>();
			params.add(new Param("link",feed.getLink()));
			params.add(new Param("picture", feed.getLinkThumb()));
			params.add(new Param("name", feed.getLinkTitle()));
			params.add(new Param("description", feed.getLinkDesc()));
			params.add(new Param("caption",feed.getLinkTitle()));
			params.add(new Param("access_token", accessToken));
			HTTPClient.executeHttpPost("https://graph.facebook.com/v2.4/"+pageId+"/feed", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void fblogincallback(HttpServletRequest req, HttpServletResponse resp) {
		try {
			log.info(">>>>>>>>>>>>>>> fblogincallback query string = " + req.getRequestURL());
			String code = req.getParameter("code");
			log.info(">>>>>>>>>>>>>>> fblogincallback code = " + code);
			if (code != null && !code.isEmpty()) {
				List<Param> params = new ArrayList<Param>();
				params.add(new Param("client_id", clientId));
				params.add(new Param("client_secret",key));
				params.add(new Param("code", code));
				params.add(new Param("redirect_uri", "http://familybookz.com/fblogincallback"));
				String response = HTTPClient.executeHttpGet("https://graph.facebook.com/v2.4/oauth/access_token", params);
				JSONObject jobj = (JSONObject) new JSONParser().parse(response);
				String accessToken = jobj.get("access_token").toString();
				log.info(">>>>>>>>>>>>>>> AccessToken = " + accessToken + " ======== " + response);

				response = HTTPClient.executeHttpGet("https://graph.facebook.com/v2.4/oauth/access_token?client_id="+clientId+"&client_secret="+key+"&grant_type=client_credentials", null);
				JSONObject appAccessToken = (JSONObject) new JSONParser().parse(response);
				log.info(">>>>>>>>>>>>>>> appAccessToken ========= " + appAccessToken.get("access_token").toString());

				response = HTTPClient.executeHttpGet("https://graph.facebook.com/v2.4/debug_token?method=get&access_token=" + appAccessToken.get("access_token").toString() + "&input_token=" + accessToken, null);
				log.info(">>>>>>>>>>>>>>> debug_token ========= " + response);
				JSONObject jobj2 = (JSONObject) new JSONParser().parse(response);
				JSONObject jobjData2 = (JSONObject) jobj2.get("data");
				if ("true".equals(jobjData2.get("is_valid").toString())) {
					String userId = jobjData2.get("user_id").toString();
					response = HTTPClient.executeHttpGet("https://graph.facebook.com/v2.4/" + userId + "?method=get&fields=id,name,email,cover,picture,gender,hometown,last_name,first_name&access_token=" + accessToken, null);
					log.info(">>>>>>>>>>>>>>> Profile ========= " + response);
					JSONObject profile = (JSONObject) new JSONParser().parse(response);
					Person p = jsontoPerson(accessToken, profile);
					Person fbProfile = DumyDB.getInstance().getPersonByFBId(Long.parseLong(p.getFaceBookId()), true);
					if(fbProfile == null){
						long id = DumyDB.getInstance().addPerson(p);
						fbProfile = DumyDB.getInstance().getPerson(id, true);
					}
					if(fbProfile != null){
						Family f = new Family(fbProfile, null, fbProfile.getChildren());
						req.getSession(true).setAttribute("family", f);
						req.getSession(true).setAttribute("profile", fbProfile);
						resp.sendRedirect("/home");
					}else{
						log.info(">>>>>>>>>>>>>>> Something wrong ========= ");
						resp.sendRedirect("/login");
					}

				} else {
					log.info(">>>>>>>>>>>>>>> is_valid is false ========= ");
					resp.sendRedirect("/login");
				}
			}else if(req.getParameter("error_code") != null){
				log.info(">>>>>>> error_code >>>>>>>> " + req.getParameter("error_code") + ":" + req.getParameter("error_message"));
				resp.sendRedirect("/login");
			} else {
				log.info(">>>>>>> User cancel login so no code >>>>>>>> ");
				resp.sendRedirect("/login");
			}
		} catch (Exception e) {
			log.error("",e);
			e.printStackTrace();
		}
	}

	private Person jsontoPerson(String accessToken, JSONObject profile) {
		long id = Long.valueOf(profile.get("id").toString());
		String name = profile.get("name").toString();
		JSONObject pic =  (JSONObject)profile.get("picture");
		JSONObject data =  (JSONObject)pic.get("data");
		String avatar = data.get("url").toString();
		JSONObject coverJson = (JSONObject)profile.get("cover");
		String cover = coverJson.get("source").toString();
		String email = null;
		Person p = new Person();
		if(profile.get("email") != null){
			email = profile.get("email").toString();
		}
		String gender = profile.get("gender").toString();
		p.setName(name);
		p.setEmail(email);
		p.setUserName(email);
		p.setImage(avatar);
		p.setCover(cover);
		p.setFirstName(profile.get("first_name").toString());
		p.setSurname(profile.get("last_name").toString());
		
		//facebook info
		p.setFaceBookId(id+"");
		p.setAccessToken(accessToken);
		
		if("male".equals(gender)){
			p.setGender(Person.GENDER.MALE);
		}else{
			p.setGender(Person.GENDER.FEMALE);
		}
		return p;
	}
	private ShortProfile jsonToShortProfile(JSONObject profile) {
		long id = Long.valueOf(profile.get("id").toString());
		String name = profile.get("name").toString();
		JSONObject pic =  (JSONObject)profile.get("picture");
		JSONObject data =  (JSONObject)pic.get("data");
		String avatar = data.get("url").toString();
		JSONObject coverJson = (JSONObject)profile.get("cover");
		String cover = coverJson.get("source").toString();
		String email = null;
		ShortProfile p = new ShortProfile();
		if(profile.get("email") != null){
			email = profile.get("email").toString();
		}
		String gender = profile.get("gender").toString();
		p.setName(name);
		p.setImage(avatar);
		//facebook info
		p.setFacebookId(id);
		return p;
	}
	public void fblogin(HttpServletRequest request, HttpServletResponse resp) {
		try {
			resp.sendRedirect("https://www.facebook.com/dialog/oauth?client_id=1489327961379891&response_type=code&scope=public_profile,user_friends,email&redirect_uri=http://familybookz.com/fblogincallback");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<ShortProfile> getFBFriends(Person user) throws Exception{
		
		if(user.getFaceBookId() != null && !user.getFaceBookId().isEmpty()){
			try{
				List<ShortProfile> friends = new ArrayList<>();
				String url = "https://graph.facebook.com/v2.4/"+user.getFaceBookId()+"/friends?method=get&fields=id,name,email,cover,picture,gender,hometown,last_name,first_name&access_token=" + user.getAccessToken();
				String response = HTTPClient.executeHttpGet(url, null);
				JSONObject jobj = (JSONObject) new JSONParser().parse(response);
				JSONArray jfriends = (JSONArray)jobj.get("data");
				for(int i=0;i<jfriends.size();i++){
					JSONObject friend = (JSONObject)jfriends.get(i);
					friends.add(jsonToShortProfile(friend));
				}
				return friends;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
//		
//		JSONObject jobj2 = (JSONObject) new JSONParser().parse("{\"data\":{\"app_id\":\"1489327961379891\",\"application\":\"familylink\",\"expires_at\":1447257556,\"is_valid\":true,\"issued_at\":1442073556,\"scopes\":[\"user_friends\",\"email\",\"public_profile\"],\"user_id\":\"10153492241103614\"}}");
//		JSONObject jobjData2 = (JSONObject) jobj2.get("data");
//		if ("true".equals(jobjData2.get("is_valid").toString())) {
//			System.out.println("Ga");
//		}
		try {
//			JSONObject jobj = JSONHelper.toJSONObject(p);
//			System.out.println(jobj.toString());
			String userId = "10153492241103614";
//			Person p = DumyDB.getInstance().getPerson(userId);
//			List<Person> friends = new ArrayList<>();
//			String response = HTTPClient.executeHttpGet("https://graph.facebook.com/v2.4/"+userId+"/friends?method=get&fields=id,name,email,cover,picture,gender,hometown,last_name,first_name&access_token=CAAVKiTAXlDMBAO2g9ossMic0iUAi1Cd9Su3xnS4ZCVZAn053t9agZBd6yXGFFL7jjwvYr1ZBZAUT99oLpzNgqeOWpd61MbN0Yau18aZBeXUVtbi4f0a0Umg0BqZBC7BZCVcZAAVZBVEoBELYf3jcHZCoEG9DqbZBiFwfR2kSDvPGFOiIGoZAyJLYIYZAv0ifDNqYrBv2mSONAMb2obrAZDZD " , null);
//			org.json.simple.JSONObject jobj = (org.json.simple.JSONObject) new JSONParser().parse(response);
//			JSONArray jfriends = (JSONArray)jobj.get("data");
//			for(int i=0;i<jfriends.size();i++){
//				JSONObject friend = (JSONObject)jfriends.get(i);
//				friends.add(jsontoPerson(null, friend));
//			}
			System.out.println(">>>>>>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
