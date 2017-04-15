package com.vnsoftware.socialplugin;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.vnsoftware.facebook.FBLinkFeed;
import com.vnsoftware.facebook.FacebookService;
import com.vnsoftware.facebook.HTTPClient;
import com.vnsoftware.util.ConfigurationHelper;


import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.PostUpdate;
import facebook4j.PrivacyBuilder;
import facebook4j.PrivacyParameter;
import facebook4j.PrivacyType;
import facebook4j.auth.AccessToken;

public class FaceBookPluginServiceImpl implements FaceBookPluginService {
	private final BlockingQueue<FaceBookFeed> jobs = new LinkedBlockingQueue<FaceBookFeed>(6000);
	private Logger log = Logger.getLogger(FaceBookPluginServiceImpl.class);
	private Facebook facebook = null;
	private static FaceBookPluginService instance = null;

	private FaceBookPluginServiceImpl() {
	}

	public static FaceBookPluginService getInstance() {
		if (instance == null) {
			instance = new FaceBookPluginServiceImpl();
			new Thread(instance).start();
		}
		return instance;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (this.facebook != null) {
					log.info(">>>>>>>>>>>>>>>>>>>..FB is good");
					execute(jobs.take());
				} else {
					log.info(">>>>>>>>>>>>>>>>>>>..FB is  null -> try with old token");
					Facebook facebook = new FacebookFactory().getInstance();
					facebook.setOAuthAppId("382999278501512", "a70c6de5f0ffc6810dc475143c7d46dd");
					facebook.setOAuthPermissions("manage_pages");
					String callbackURL = ConfigurationHelper.getInstance().getValue("domain") + "admin/callback.html";
					facebook.getOAuthAuthorizationURL(callbackURL.toString());
					facebook.setOAuthAccessToken(new AccessToken(ConfigurationHelper.getDBValue("FB_pageAccessToken")));
					SocialServiceFactory.getFaceBookService().setFacebook(facebook);
					execute(jobs.take());
				}
			} catch (InterruptedException e) {
				log.error(">>>>>>>>>>>. execute InterruptedException", e);
			} catch (Exception e) {
				log.error(">>>>>>>>>>>. execute ", e);
			}
		}
	}

	private void execute(FaceBookFeed feed) {
		try {
			log.error("START >>>>>>>>>>>. execute ");
			/*PostUpdate u = new PostUpdate(feed.getLinkTitle());
			u.setCaption(feed.getLinkTitle());
			u.setDescription(feed.getLinkDesc().replaceAll("\\<.*?\\>", ""));
			u.setLink(new URL(feed.getLink()));
			u.setPicture(new URL(feed.getLinkThumb()));
			u.setPublished(true);
			ConfigurationHelper.getDBValue("FB_pageAccessToken")ConfigurationHelper.getDBValue("FB_pageAccessToken")ConfigurationHelper.getDBValue("FB_pageAccessToken")			log.info("Post FaceBook feed URL=" + feed.getLink());
			PrivacyParameter privacy = new PrivacyBuilder().setValue(PrivacyType.EVERYONE).build();
			u.setPrivacy(privacy);
			getFacebook().postFeed("545910802183206", u);*/
			
			String pageAccessToken = ConfigurationHelper.getDBValue("FB_pageAccessToken");
			FacebookService.getInstance().postFeedLink(new FBLinkFeed(feed.getLink(), feed.getLinkTitle(), feed.getLinkDesc(), feed.linkThumb), pageAccessToken, "545910802183206");
			log.info("Finished post FB feed" + feed.getLink());
			log.error("END >>>>>>>>>>>. execute ");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("execute postFeed>>>>>>>>>>>>>>>>>>>>>>>", e);
		}
	}

	public static void main(String[] args) {
		String url = "http://vnexpress.net/tin-tuc/thoi-su/giao-thong/chu-truong-cap-giay-phep-lai-xe-so-tu-dong-gay-tranh-cai-3218973.html";
		String endcodeUrl = "";
		try {
			url = URLEncoder.encode(url, "UTF-8");
			URLDecoder.decode(url, "UTF-8");
			System.out.println(url);
			url = URLDecoder.decode(url, "UTF-8");
			PostUpdate u = new PostUpdate("asdsad");
			u.setCaption("asdasasdsa sad");
			u.setDescription("asd asdas asdsa");
			u.setLink(new URL("http://360hay.com?target=http://vnexpress.net/tin-tuc/thoi-su/giao-thong/chu-truong-cap-giay-phep-lai-xe-so-tu-dong-gay-tranh-cai-3218973.html"));
			u.setPicture(new URL("http://vnexpress.net/"));
			u.setPublished(true);
			System.out.println(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void postFeed(FaceBookFeed feed) {
		try {
			if (this.facebook == null) {
				log.info("Not yet have facebook");
				return;
			}
			log.info(">>>>>>>>>>>> postFeed = " + feed.linkTitle);
			jobs.put(feed);
		} catch (InterruptedException e) {
			log.error("", e);
		}
	}

	public Facebook getFacebook() {
		return this.facebook;
	}

	@Override
	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

}
