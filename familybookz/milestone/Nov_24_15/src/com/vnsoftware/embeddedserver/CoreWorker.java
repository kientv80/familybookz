package com.vnsoftware.embeddedserver;

import java.util.Timer;
import java.util.TimerTask;

import com.vnsoftware.midle.BroadcastFeedService;

public class CoreWorker {
	public void start(){
		TimerTask broadcastFeed = new TimerTask() {
			@Override
			public void run() {
				BroadcastFeedService.getInstance().broadcastFeeds();
			}
		};
		Timer t4 = new Timer();
		t4.schedule(broadcastFeed, 0, 1 * 1 * 5 * 1000);
	}
}
