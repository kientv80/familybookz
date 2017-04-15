package com.vnsoftware.socialplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.vng.zalosdk.service.ZaloMessageService;
import com.vng.zalosdk.service.ZaloServiceConfigure;
import com.vng.zalosdk.service.ZaloServiceFactory;
import com.vng.zalosdk.service.ZaloSocialService;

class ZaloServiceImpl implements ZaloService {
	private final BlockingQueue<Object> msgQueue = new LinkedBlockingQueue<Object>(6000);
	Logger log = Logger.getLogger(ZaloServiceImpl.class);
	ZaloServiceFactory a = new ZaloServiceConfigure(2484853059230521968l, "6F58BJL2eqd1PfNr6VHz").getZaloServiceFactory();
	ZaloMessageService service = a. getZaloMessageService();
	ZaloSocialService feedService = a.getZaloSocialService();
	
	private static ZaloService zservice;
	public static ZaloService getInstance(){
		if(zservice == null){
			zservice = new ZaloServiceImpl();
			new Thread(zservice).start();
		}
		return zservice;
	}
	@Override
	public void sendLinkMessageByPhonenumber(ZaloLinkMessage link) {
		try {
			msgQueue.put(link);
		} catch (InterruptedException e) {
			log.error("", e);
		}

	}

	@Override
	public void sendTextMessageByPhoneNum(ZaloMessage msg) {
		try {
			msgQueue.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				execute(msgQueue.take());
			} catch (InterruptedException e) {
				log.error("", e);
			} catch (Exception e) {
				log.error("", e);
			}
		}

	}

	private void execute(Object msg) {
		try {
			if (msg instanceof ZaloLinkMessage) {
				ZaloLinkMessage link = (ZaloLinkMessage) msg;
				service.sendLinkMessageByPhoneNum(link.getPhoneNumber(), link.getLink(), link.getLinkTitle(), link.getLinkTitle(), link.getLinkThumb(), null);
			}else if (msg instanceof ZaloMessage){
				ZaloMessage zmsg = (ZaloMessage)msg;
				service.sendTextMessageByPhoneNum(zmsg.getPhone(), zmsg.getMessage(), zmsg.getMessage());
			}else if (msg instanceof ZaloLinkFeed){
				ZaloLinkFeed feed = (ZaloLinkFeed)msg;
				feedService.pushLinkFeed(feed.getMessage().replaceAll("\\<.*?\\>", " "), feed.getLink(), feed.getLinkTitle(), feed.getLinkTitle(), feed.getLinkThumb());
			}else if (msg instanceof ImangeMassage){
				ImangeMassage feed = (ImangeMassage)msg;
				String imgId = a.getZaloUploadService().uploadImage(((ImangeMassage) msg).getImageUrl());
				List<String> imgIds = new ArrayList<String>();
				imgIds.add(imgId);
				feedService.pushMultiImagesFeed(feed.getMessage().replaceAll("\\<.*?\\>", " "), imgIds);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("",e);
		}

	}
	@Override
	public void pushLinkFeed(ZaloLinkFeed feed) {
		try {
			msgQueue.put(feed);
		} catch (InterruptedException e) {
			log.error("", e);
		}
		
	}
	@Override
	public void pushImageFeed(ImangeMassage feed) {
		try {
			msgQueue.put(feed);
		} catch (InterruptedException e) {
			log.error("", e);
		}
	}

}
