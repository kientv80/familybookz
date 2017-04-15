package com.vnsoftware.family.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebsocketNotificationHandler extends TextWebSocketHandler {
	Logger l = Logger.getLogger(WebsocketNotificationHandler.class);
	@Autowired
	SimpMessagingTemplate template;
	int count = 0;
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		l.debug("====================== Got a text " + message.toString());
		session.sendMessage(new TextMessage(session.getId() + "-Hello " + count));
		count ++;
		try {
			template.convertAndSend("/topic/newFeedNotify", "Just say hello!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
