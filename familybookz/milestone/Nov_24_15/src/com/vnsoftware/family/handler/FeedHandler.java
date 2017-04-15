package com.vnsoftware.family.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vnsoftware.giapha.entirty.Action;
import com.vnsoftware.giapha.entirty.Action.ACTION_TYPE;
import com.vnsoftware.giapha.entirty.Comment;
import com.vnsoftware.giapha.entirty.DumyDB;
import com.vnsoftware.giapha.entirty.Emoticon;
import com.vnsoftware.giapha.entirty.Feed;
import com.vnsoftware.giapha.entirty.Notify;
import com.vnsoftware.giapha.entirty.Person;
import com.vnsoftware.giapha.entirty.ShortProfile;
import com.vnsoftware.util.HTMLLinkParser;
import com.vnsoftware.util.JSONHelper;
import com.vnsoftware.util.LinkInfo;

@Controller
public class FeedHandler extends BasedHandler {
	@Autowired
	SimpMessagingTemplate template;

	@RequestMapping(value = "/postnewfeed", method = RequestMethod.POST)
	public String postNewFeed(String status, @RequestParam("image") List<MultipartFile> files, HttpServletResponse resp, HttpServletRequest req) {
		try {
			Person p = (Person) req.getSession(true).getAttribute("profile");
			String url = HTMLLinkParser.getHttpLinkInText(status);
			if (status == null || status.isEmpty()) {
				writeFailedResponse(resp, "Status is required");
			} else {
				Feed f = new Feed();
				f.setOwnerId(p.getId());
				f.setFeedId(System.currentTimeMillis());
				f.setContent(status);
				f.setOwnerInfo(new ShortProfile(p.getId(), p.getName(), p.getImage()));
				List<String> images = new ArrayList<>();
				f.setImages(images);
				if (url != null && !url.isEmpty()) {
					// port feed link
					LinkInfo info = HTMLLinkParser.parseLink(url);
					if (info != null) {
						f.setUrl(url);
						f.setTitle(info.getTitle());
						images.add(info.getImage());
						f.setDesc(info.getDesc());
						f.setWebsite(info.getWebsite());
						f.setType(Feed.TYPE.LINK.ordinal());
						if (status != null && status.equals(url)) {
							f.setContent("");
						} else if (status != null && status.endsWith(url)) {
							f.setContent(status.replace(url, ""));
						} else if (url != null && url.length() > 60) {
							f.setContent(status.replace(url, "<a href='#' onclick=\"openLink('" + url + "')\">" + url.substring(0, 60) + " ..."));
						}
					}
				} else {
					// post normal feed
					String path = "/kientv/familybookz/webapps/images/feed/";
					if (System.getProperty("os.name").equals("Windows 7"))
						path = "D:\\Working\\projects\\familybookz\\webapps\\images\\feed\\";
					String image = System.currentTimeMillis() + ".png";
					if (files != null && files.size() > 0) {
						byte[] bytes;
						for (MultipartFile file : files) {
							bytes = file.getBytes();
							if (bytes.length > 0) {
								image = System.currentTimeMillis() + ".png";
								BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + image)));
								stream.write(bytes);
								stream.close();
								images.add("/images/feed/" + image);
							}
						}
					}
					f.setType(Feed.TYPE.STATUS.ordinal());
				}
				DumyDB.getInstance().postFeed(f, p);

				JSONObject jobj = JSONHelper.toJSONObject(new Notify(f.getFeedId(), f.getOwnerId(), "feed", f.getContent()));
				template.convertAndSend("/topic/newFeedNotify", jobj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeFailedResponse(resp, "Post failed, please try later.");
		}
		return "redirect:/feeds";
	}

	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public String timeline(HttpServletRequest req, ModelMap map) {
		try {
			Person p = (Person) req.getSession(true).getAttribute("profile");
			map.put("feeds", DumyDB.getInstance().getFeeds(p.getId()));
			map.put("emoticons", DumyDB.getInstance().getEmoticons());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "timeline";
	}

	@RequestMapping(value = "/feeds", method = RequestMethod.GET)
	public String myFeed(String id, HttpServletRequest req, ModelMap map) {
		try {
			long ownerId;
			if (id != null && isNumber(id)) {
				ownerId = Long.valueOf(id);
			} else {
				Person p = (Person) req.getSession(true).getAttribute("profile");
				ownerId = p.getId();
			}

			map.put("feeds", DumyDB.getInstance().getMyFeeds(ownerId));
			map.put("emoticons", DumyDB.getInstance().getEmoticons());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "timeline";
	}

	@Async
	@ResponseBody
	@RequestMapping(value = "/parselink", method = RequestMethod.GET)
	public void timeline(String url, HttpServletResponse resp, ModelMap map) {
		try {
			LinkInfo info = HTMLLinkParser.parseLink(url);
			writeJSONResponse(resp, JSONHelper.toJSONObject(info));
		} catch (Exception e) {
			e.printStackTrace();
			writeFailedResponse(resp, "parse failed");
		}
	}

	@Async
	@ResponseBody
	@RequestMapping(value = "/action", method = RequestMethod.GET)
	public void action(String itemId, String type,  HttpServletRequest req, HttpServletResponse resp, ModelMap map) {
		try {
			if (!isNumber(itemId))
				return;
			Person p = (Person) req.getSession(true).getAttribute("profile");
			Action act = null;
			if ("l".equals(type)) {
				act = new Action(ACTION_TYPE.LIKE, p.getId(), Long.valueOf(itemId));
			} else if ("s".equals(type)) {
				act = new Action(ACTION_TYPE.SHARE, p.getId(), Long.valueOf(itemId));
			}
			if (act != null) {
				DumyDB.getInstance().likeShareComment(act);
				writeSuccessResponse(resp, "successfully");
			} else {
				writeFailedResponse(resp, "invalid data");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String validText = "0123456789,";

	@Async
	@ResponseBody
	@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	public void profiles(String profileIds, HttpServletRequest req, HttpServletResponse resp, ModelMap map) {
		try {
			if (!checkValidText(profileIds))
				return;
			if (profileIds != null && !profileIds.isEmpty() && profileIds.startsWith(","))
				profileIds = profileIds.substring(1, profileIds.length());
			List<ShortProfile> e = DumyDB.getInstance().getShortProfiles(profileIds);
			writeJSONResponse(resp, JSONHelper.toJSONArray(e));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkValidText(String ids) {
		if (ids != null && !ids.isEmpty()) {
			for (int i = 0; i < ids.length(); i++) {
				if (validText.indexOf(ids.charAt(i)) < 0) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	@Async
	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public void comment(String feedId, String replyCommentId, String content, String emoticon, HttpServletRequest req, HttpServletResponse resp, ModelMap map) {
		try {
			Person p = (Person) req.getSession(true).getAttribute("profile");
			Comment c = new Comment(0, Long.valueOf(replyCommentId), p.getId(), Long.valueOf(feedId), content, p.getFirstName() + " " + p.getSurname(), p.getImage());
			String url = HTMLLinkParser.getHttpLinkInText(c.getComment());
			if (url != null && !url.isEmpty()) {
				LinkInfo info = HTMLLinkParser.parseLink(url);
				if (info != null) {
					c.setType(Comment.TYPE_LINK);
					c.setTitle(info.getTitle());
					c.setDesc(info.getDesc());
					c.setImage(info.getImage());
					c.setUrl(info.getUrl());
				}
			}else if(emoticon != null && !emoticon.isEmpty() && isNumber(emoticon) && DumyDB.getInstance().getEmoticon(Integer.parseInt(emoticon)) != null){
				Emoticon e =  DumyDB.getInstance().getEmoticon(Integer.parseInt(emoticon));
				c.setType(Comment.TYPE_EMOTICON);
				c.setImage(e.getUrl());
				c.setComment("");
			}
			DumyDB.getInstance().postComment(c);
			writeJSONResponse(resp, JSONHelper.toJSONObject(c));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
