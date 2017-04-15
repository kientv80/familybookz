package com.vnsoftware.giapha.entirty;

public class BroadcastingFeed {
	private long owner;
	private long feedId;
	public BroadcastingFeed(long owner, long feedId) {
		this.owner = owner;
		this.feedId = feedId;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public long getFeedId() {
		return feedId;
	}
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
}
