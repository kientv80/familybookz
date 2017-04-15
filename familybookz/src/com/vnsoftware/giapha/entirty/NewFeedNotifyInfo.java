package com.vnsoftware.giapha.entirty;

public class NewFeedNotifyInfo {
	public NewFeedNotifyInfo(long feedId, long ownerId, String content) {
		super();
		this.feedId = feedId;
		this.ownerId = ownerId;
		this.content = content;
	}
	/**
	 * @return the feedId
	 */
	public long getFeedId() {
		return feedId;
	}
	/**
	 * @param feedId the feedId to set
	 */
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
	/**
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	long feedId;
	long ownerId;
	String content;
}
