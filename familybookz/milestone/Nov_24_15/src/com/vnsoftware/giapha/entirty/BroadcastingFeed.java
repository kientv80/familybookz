package com.vnsoftware.giapha.entirty;

public class BroadcastingFeed {
	private Person owner;
	private long feedId;
	public BroadcastingFeed(Person owner, long feedId) {
		this.owner = owner;
		this.feedId = feedId;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public long getFeedId() {
		return feedId;
	}
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
}
