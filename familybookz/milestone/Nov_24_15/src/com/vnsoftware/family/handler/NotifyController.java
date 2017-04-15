package com.vnsoftware.family.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.vnsoftware.giapha.entirty.NewFeedNotifyInfo;

import facebook4j.internal.logging.Logger;

@Controller
public class NotifyController {
	Logger l = Logger.getLogger(NotifyController.class);
	@Autowired
	SimpMessagingTemplate template;
	
	@MessageMapping("/hello")
	@SendTo("/topic/newFeedNotify")
	public NewFeedNotifyInfo newFeedNotify(){
		NewFeedNotifyInfo r = new NewFeedNotifyInfo(1,3,"Hello");
		new Thread(){
			@SuppressWarnings("static-access")
			public void run() {
				while (true) {
					try {
						template.convertAndSend("/topic/newFeedNotify", "Just say hello!");
						this.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		return r;
	}
	
}
